package com.reitler.boa.app.gui.songs;

import java.util.LinkedList;
import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.core.api.Constants;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongManagementModel extends AbstractTableModel {

	private List<ISong> songs;
	private final ISongListManager listManager;
	private final ISongListListener listener = new SongListListener();

	public SongManagementModel(final List<ISong> songs, final ISongListManager listManager) {
		this.songs = new LinkedList<>(songs);
		this.listManager = listManager;
		registerListener();
	}

	private void registerListener() {
		this.listManager.addListener(this.listener);

		for (ISongList list : this.listManager.getAllSongLists()) {
			list.addListener(this.listener);
		}
	}

	public void setSongs(final List<ISong> songsToSet) {
		this.songs = new LinkedList<>(songsToSet);
		update();
	}

	@Override
	public int getRowCount() {
		return this.songs.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Constants.getSongTitleCaption();
		case 1:
			return Constants.getSongArtistCaption();
		case 2:
			return Constants.getSongPublisherCaption();
		case 3:
			return Constants.getSongSongListsCaption();
		case 4:
			return Constants.getSongTagsCaption();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if ((columnIndex >= 0) && (columnIndex <= 4)) {
			return String.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		ISong song = getSong(rowIndex);
		switch (columnIndex) {
		case 0:
			return song.getTitle();
		case 1:
			return song.getArtist();
		case 2:
			return song.getPublisher();
		case 3:
			return getContainingLists(song);
		case 4:
			return String.join(" ", song.getTags());
		default:
			return null;
		}
	}

	private String getContainingLists(final ISong song) {
		StringBuilder builder = new StringBuilder();

		for (ISongList list : this.listManager.getAllSongLists()) {
			for (ISongAssignment a : list.getByPage()) {
				if (a.getSong().equals(song)) {
					builder.append(String.format("  %s-%s", list.getName(), a.getPage()));
				}
			}
		}

		return builder.toString().trim();
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		// nothing to do
	}

	public ISong getSong(final int r) {
		return this.songs.get(r);
	}

	private class SongListListener implements ISongListListener {

		@Override
		public void songListCreated(final ISongList list) {
			update();
		}

		@Override
		public void songListDeleted(final ISongList list) {
			update();
		}

		@Override
		public void assignmentAdded(final ISongList list, final ISongAssignment assignment) {
			update();
		}

		@Override
		public void assignmentRemoved(final ISongList list, final ISongAssignment assignment) {
			update();
		}

	}

}
