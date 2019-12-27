package com.reitler.boa.core.persistence;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.reitler.boa.core.SongAssignment;
import com.reitler.boa.core.SongListManager;
import com.reitler.boa.core.SongManager;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;

public class PersistenceHandler implements IPersistenceHandler {

	private final static Logger LOG = System.getLogger(PersistenceHandler.class.getCanonicalName());

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
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareUpdateSongListQuery(list.getId(), list.getName()));

		} catch (SQLException e) {
			String msg = String.format("Error while updating songList: ID='%d' Name='%s' ", list.getId(),
					list.getName());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private void saveSong(final ISong song) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareInsertSongQuery(song));

		} catch (SQLException e) {
			String msg = String.format("Error while saving song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private void updateSong(final ISong song) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareUpdateSongQuery(song));

		} catch (SQLException e) {
			String msg = String.format("Error while updating song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private void saveSongList(final ISongList list) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareInsertSonglistQuery(list.getId(), list.getName()));

		} catch (SQLException e) {
			String msg = String.format("Error while saving songlist: ID='%d' Title='%s'", list.getId(), list.getName());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private void saveAssignment(final SongAssignment assign, final ISongList list) {
		SongAssignment assignment = assign;
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareInsertAssignmentQuery(assignment.getId(), assignment.getPage(),
					assignment.getSong().getId(), list.getId()));
		} catch (SQLException e) {
			String msg = String.format("Error while saving assignments: ID='%d' SongID='%d' ListId='%d'",
					assign.getId(), assign.getSong().getId(), list.getId());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	@Override
	public void close() throws IOException {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				PersistenceHandler.LOG.log(Level.ERROR, "Error while closing database file", e);
			}
		}
	}

	private void deleteSong(final ISong song) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.addBatch(Queries.prepareDeleteAssignmentBySongsQuery(song.getId()));
			statement.addBatch(Queries.prepareDeleteSongQuery(song.getId()));
			statement.executeBatch();

		} catch (SQLException e) {
			String msg = String.format("Error while deleting song: ID='%d' Title='%s' Artist='%s' Publisher='%s'",
					song.getId(), song.getTitle(), song.getArtist(), song.getPublisher());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private void deleteAssignment(final SongAssignment removedSong) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.executeUpdate(Queries.prepareDeleteAssignmentByIdQuery(removedSong.getId()));
		} catch (SQLException e) {
			String msg = String.format("Error while deleting assignments: ID='%d' SongID='%d' ", removedSong.getId(),
					removedSong.getSong().getId());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private void deleteSongList(final ISongList list) {
		try (Statement statement = this.connection.createStatement()) {
			statement.setQueryTimeout(30);
			statement.addBatch(Queries.prepareDeleteAssignmentsByListQuery(list.getId()));
			statement.addBatch(Queries.prepareDeleteSongListQuery(list.getId()));
			statement.executeBatch();

		} catch (SQLException e) {
			String msg = String.format("Error while deleting songlist: ID='%d' Title='%s'", list.getId(),
					list.getName());
			PersistenceHandler.LOG.log(Level.ERROR, msg, e);
		}
	}

	private boolean isInitialized() {
		return this.connection != null;
	}
}
