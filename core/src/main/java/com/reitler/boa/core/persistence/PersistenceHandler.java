package com.reitler.boa.core.persistence;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.reitler.boa.core.Song;
import com.reitler.boa.core.SongAssignment;
import com.reitler.boa.core.SongList;
import com.reitler.boa.core.SongStorage;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;

public class PersistenceHandler implements IPersistenceHandler {

	private final SongStorage storage;
	private Connection connection;

	public PersistenceHandler(final SongStorage storage) {
		this.storage = storage;
	}

	@Override
	public boolean open(final File file) {
		DatabaseInitializer initializer = new DatabaseInitializer();
		boolean init = initializer.init(file);
		if (!init) {
			return false;
		}
		this.connection = initializer.getConnection();
		initializer.getSongs().forEach(this.storage::addSong);
		initializer.getSongLists().forEach(this.storage::addSongList);
		return true;
	}

	@Override
	public void songAdded(final ISong addedSong) {
		if (addedSong instanceof Song) {
			saveSong((Song) addedSong);
		}
	}

	@Override
	public void songRemoved(final ISong removedSong) {
		if (removedSong instanceof Song) {
			deleteSong((Song) removedSong);
		}
	}

	@Override
	public void assignmentAdded(final ISongList source, final ISongAssignment addedSong) {
		if (addedSong instanceof SongAssignment) {
			saveAssignment((SongAssignment) addedSong, source);
		}
	}

	@Override
	public void assignmentRemoved(final ISongList source, final ISongAssignment removedSong) {
		if (removedSong instanceof SongAssignment) {
			deleteAssignment((SongAssignment) removedSong);
		}
	}

	@Override
	public void songListAdded(final ISongList list) {
		if (list instanceof SongList) {
			saveSongList((SongList) list);
		}
	}

	@Override
	public void songListRemoved(final ISongList list) {
		if (list instanceof SongList) {
			deleteSongList(list);
		}
	}

	private void saveSong(final Song song) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareInsertSongQuery(song.getId(), song.getTitle()));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveSongList(final SongList list) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareInsertSonglistQuery(list.getId(), list.getName()));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveAssignment(final SongAssignment assign, final ISongList list) {
		SongAssignment assignment = assign;
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareInsertAssignmentQuery(assignment.getId(), assignment.getPage(),
					assignment.getSong().getId(), list.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteSong(final Song song) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.addBatch(Queries.prepareDeleteAssignmentBySongsQuery(song.getId()));
			statement.addBatch(Queries.prepareDeleteSongQuery(song.getId()));
			statement.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteAssignment(final SongAssignment removedSong) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareDeleteAssignmentByIdQuery(removedSong.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteSongList(final ISongList list) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.addBatch(Queries.prepareDeleteAssignmentsByListQuery(list.getId()));
			statement.addBatch(Queries.prepareDeleteSongListQuery(list.getId()));
			statement.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
