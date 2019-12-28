package com.reitler.boa.core.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sqlite.SQLiteConfig;

import com.reitler.boa.core.Song;
import com.reitler.boa.core.SongList;
import com.reitler.boa.core.SongListManager;
import com.reitler.boa.core.SongManager;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;

public class DatabaseInitializer {

	private static final java.util.logging.Logger LOG = Logger.getLogger(DatabaseInitializer.class.getCanonicalName());
	private final Map<Integer, Song> songs = new TreeMap<>();
	private final Map<Integer, SongList> songLists = new TreeMap<>();

	private final SongManager songManager;
	private final SongListManager listManager;
	private Connection connection;

	public DatabaseInitializer(final SongManager songManager, final SongListManager listManager) {
		this.songManager = songManager;
		this.listManager = listManager;
	}

	public boolean init(final File file) {
		boolean init = false;
		if (!file.exists()) {
			init = true;
		}

		SQLiteConfig config = new SQLiteConfig();
		config.enforceForeignKeys(true);

		try {
			String url = String.format("jdbc:sqlite:%s", file.getAbsolutePath());
			this.connection = DriverManager.getConnection(url, config.toProperties());
		} catch (SQLException e) {
			String msg = String.format("Error while opening database file: %s", file.getAbsolutePath());
			DatabaseInitializer.LOG.log(Level.SEVERE, msg, e);
			return false;
		}

		if (init) {
			return initDbSchema() && load();
		}

		return load();
	}

	public Connection getConnection() {
		return this.connection;
	}

	public List<Song> getSongs() {
		return new ArrayList<>(this.songs.values());
	}

	public List<SongList> getSongLists() {
		return new ArrayList<>(this.songLists.values());
	}

	private boolean initDbSchema() {
		try (InputStream stream = getClass().getClassLoader().getResourceAsStream("schema.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				Statement statement = this.connection.createStatement()) {

			statement.setQueryTimeout(30);
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("#") && !line.trim().isEmpty()) {
					statement.addBatch(line.trim());
				}
			}
			statement.executeBatch();
			return true;
		} catch (IOException | SQLException e) {
			DatabaseInitializer.LOG.log(Level.SEVERE, "Error while initializing the database", e);
			return false;
		}
	}

	private boolean load() {
		try (ResultSet songResults = executeQuery(Queries.SELECT_SONGS_QUERY)) {
			while (songResults.next()) {
				createSong(songResults);
			}
		} catch (SQLException e) {
			DatabaseInitializer.LOG.log(Level.SEVERE, "Error while reading songs from database file", e);
			return false;
		}

		try (ResultSet songListResults = executeQuery(Queries.SELECT_SONGLISTS_QUERY)) {
			while (songListResults.next()) {
				createSongList(songListResults);
			}
		} catch (SQLException e) {
			DatabaseInitializer.LOG.log(Level.SEVERE, "Error while reading song lists from database", e);
			return false;
		}

		try (ResultSet assignmentResults = executeQuery(Queries.SELECT_ASSIGNMENTS_QUERY)) {
			while (assignmentResults.next()) {
				createAssignment(assignmentResults);
			}
		} catch (SQLException e) {
			DatabaseInitializer.LOG.log(Level.SEVERE, "Error while reading song assignments from database", e);
			return false;
		}
		return true;
	}

	private void createAssignment(final ResultSet rs) throws SQLException {
		int assignmentId = rs.getInt(Queries.ASSIGNMENT_ID_ATTRIBUTE);
		int songId = rs.getInt(Queries.ASSIGNMENT_SONG_ATTRIBUTE);
		String page = rs.getString(Queries.ASSIGNMENT_PAGE_ATTRIBUTE);
		int listId = rs.getInt(Queries.ASSIGNMENT_SONGLIST_ATTRIBUTE);

		ISong song = this.songs.get(songId);
		ISongList list = this.songLists.get(listId);

		this.listManager.assign(assignmentId, song, list, page);
	}

	private void createSongList(final ResultSet songListResults) throws SQLException {
		int id = songListResults.getInt(Queries.SONGLIST_ID_ATTRIBUTE);
		String name = songListResults.getString(Queries.SONGLIST_NAME_ATTRIBUTE);

		SongList list = this.listManager.createSongList(id, name);
		this.songLists.put(id, list);
	}

	private void createSong(final ResultSet songResults) throws SQLException {
		int songId = songResults.getInt(Queries.SONG_ID_ATTRIBUTE);
		String title = songResults.getString(Queries.SONG_TITLE_ATTIRBUTE);
		String artist = songResults.getString(Queries.SONG_ARTIST_ATTRIBUTE);
		String publisher = songResults.getString(Queries.SONG_PUBLISHER_ATTRIBUTE);
		String tags = songResults.getString(Queries.SONG_TAGS_ATTRIBUTE);

		Song song = this.songManager.createSong(songId, title, artist, publisher, Arrays.asList(tags.split(",")));
		this.songs.put(songId, song);
	}

	@SuppressWarnings("resource")
	private ResultSet executeQuery(final String query) throws SQLException {
		Statement statement = this.connection.createStatement();
		statement.closeOnCompletion();
		// create a database connection
		statement.setQueryTimeout(30); // set timeout to 30 sec.
		return statement.executeQuery(query);
	}
}
