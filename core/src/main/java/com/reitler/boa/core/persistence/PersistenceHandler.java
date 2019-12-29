package com.reitler.boa.core.persistence;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.reitler.boa.core.SongAssignment;
import com.reitler.boa.core.SongListManager;
import com.reitler.boa.core.SongManager;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;

public class PersistenceHandler implements IPersistenceHandler {

	private final static java.util.logging.Logger LOG = Logger.getLogger(PersistenceHandler.class.getCanonicalName());

	private final SongManager songManager;
	private final SongListManager listManager;
	private Connection connection;

	public PersistenceHandler(final SongManager songManager, final SongListManager listManager) {
		this.songManager = songManager;
		this.listManager = listManager;
	}

	@Override
	public boolean open(final File file) {
		this.songManager.addListener(this);
		this.listManager.addListener(this);
		DatabaseInitializer initializer = new DatabaseInitializer(this.songManager, this.listManager);
		boolean init = initializer.init(file);
		if (!init) {
			return false;
		}
		this.connection = initializer.getConnection();
		return true;
	}

	@Override
	public void songAdded(final ISong addedSong) {
		if (isInitialized()) {
			saveSong(addedSong);
		}
	}

	@Override
	public void songRemoved(final ISong removedSong) {
		if (isInitialized()) {
			deleteSong(removedSong);
		}
	}

	@Override
	public void songChanged(final ISong oldValue, final ISong newValue) {
		if (isInitialized()) {
			updateSong(newValue);
		}
	}

	@Override
	public void assignmentAdded(final ISongList source, final ISongAssignment addedSong) {
		if (isInitialized() && (addedSong instanceof SongAssignment)) {
			saveAssignment((SongAssignment) addedSong, source);
		}
	}

	@Override
	public void assignmentRemoved(final ISongList source, final ISongAssignment removedSong) {
		if (isInitialized() && (removedSong instanceof SongAssignment)) {
			deleteAssignment((SongAssignment) removedSong);
		}
	}

	@Override
	public void songListCreated(final ISongList list) {
		if (isInitialized()) {
			saveSongList(list);
		}
	}

	@Override
	public void songListDeleted(final ISongList list) {
		if (isInitialized()) {
			deleteSongList(list);
		}
	}

	@Override
	public void songListNameChanged(final ISongList list, final String oldName, final String newName) {
		if (isInitialized()) {
			updateSongList(list);
		}
	}

	private void updateSongList(final ISongList list) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.UPDATE_SONGLIST_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, list.getId());
			statement.setString(2, list.getName());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while updating songList: ID='%d' Name='%s' ", list.getId(),
					list.getName());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private void saveSong(final ISong song) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.INSERT_SONG_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, song.getId());
			statement.setString(2, nullSafe(song.getTitle()));
			statement.setString(3, nullSafe(song.getArtist()));
			statement.setString(4, nullSafe(song.getPublisher()));
			statement.setString(5, join(song.getTags()));
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while saving song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private void updateSong(final ISong song) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.UPDATE_SONG_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setString(1, nullSafe(song.getTitle()));
			statement.setString(2, nullSafe(song.getArtist()));
			statement.setString(3, nullSafe(song.getPublisher()));
			statement.setString(4, join(song.getTags()));
			statement.setInt(5, song.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while updating song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private void saveSongList(final ISongList list) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.INSERT_SONGLIST_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, list.getId());
			statement.setString(2, list.getName());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while saving songlist: ID='%d' Title='%s'", list.getId(), list.getName());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private void saveAssignment(final SongAssignment assignment, final ISongList list) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.INSERT_ASSIGNMENT_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, assignment.getId());
			statement.setString(2, assignment.getPage());
			statement.setInt(3, assignment.getSong().getId());
			statement.setInt(4, list.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			String msg = String.format("Error while saving assignments: ID='%d' SongID='%d' ListId='%d'",
					assignment.getId(), assignment.getSong().getId(), list.getId());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	@Override
	public void close() throws IOException {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				PersistenceHandler.LOG.log(Level.SEVERE, "Error while closing database file", e);
			}
		}
	}

	private void deleteSong(final ISong song) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.DELETE_SONG_BY_ID_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, song.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while deleting song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
		try (PreparedStatement statement = this.connection
				.prepareStatement(Queries.DELETE_ASSIGNMENTS_BY_SONG_ID_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, song.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while deleting song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private void deleteAssignment(final SongAssignment removedSong) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.DELETE_ASSIGNMENT_BY_ID_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, removedSong.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			String msg = String.format("Error while deleting assignments: ID='%d' SongID='%d' ", removedSong.getId(),
					removedSong.getSong().getId());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private void deleteSongList(final ISongList list) {
		try (PreparedStatement statement = this.connection.prepareStatement(Queries.DELETE_LIST_BY_ID_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, list.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while deleting songlist: ID='%d' Title='%s'", list.getId(),
					list.getName());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}

		try (PreparedStatement statement = this.connection
				.prepareStatement(Queries.DELETE_ASSIGNMENTS_BY_LIST_ID_QUERY)) {
			statement.setQueryTimeout(30);
			statement.setInt(1, list.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			String msg = String.format("Error while deleting songlist: ID='%d' Title='%s'", list.getId(),
					list.getName());
			PersistenceHandler.LOG.log(Level.SEVERE, msg, e);
		}
	}

	private boolean isInitialized() {
		return this.connection != null;
	}

	private static String nullSafe(final String value) {
		return value == null ? "" : value;
	}

	private static String join(final Collection<String> tags) {
		if (tags == null) {
			return "";
		}
		return String.join(",", tags);
	}
}
