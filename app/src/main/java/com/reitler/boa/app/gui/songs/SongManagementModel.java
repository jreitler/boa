package com.reitler.boa.app.gui.songs;

import java.util.LinkedList;
import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISong;

public class SongManagementModel extends AbstractTableModel {

	private List<ISong> songs;

	public SongManagementModel(final List<ISong> songs) {
		this.songs = new LinkedList<>(songs);
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
		return 4;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
		case 0:
			return UIConstants.getSongTitleCaption();
		case 1:
			return UIConstants.getSongArtistCaption();
		case 2:
			return UIConstants.getSongPublisherCaption();
		case 3:
			return UIConstants.getSongTagsCaption();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if ((columnIndex >= 0) && (columnIndex <= 3)) {
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
			return String.join(" ", song.getTags());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		// nothing to do
	}

	public ISong getSong(final int r) {
		return this.songs.get(r);
	}

}
