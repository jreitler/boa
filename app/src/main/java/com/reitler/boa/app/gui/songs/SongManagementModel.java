package com.reitler.boa.app.gui.songs;

import java.util.LinkedList;
import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.core.interfaces.ISong;

public class SongManagementModel extends AbstractTableModel {

	private final List<ISong> songs;

	public SongManagementModel(final List<ISong> songs) {
		this.songs = new LinkedList<>(songs);
	}

	@Override
	public int getRowCount() {
		return this.songs.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "title";
		case 1:
			return "artist";
		case 2:
			return "publisher";
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if ((columnIndex >= 0) && (columnIndex <= 2)) {
			return String.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return (columnIndex >= 0) && (columnIndex <= 2);
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
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		ISong song = getSong(rowIndex);
		String value = String.valueOf(aValue);
		switch (columnIndex) {
		case 0:
			song.setTitle(value);
			break;
		case 1:
			song.setArtist(value);
			break;
		case 2:
			song.setPublisher(value);
			break;
		default:
		}
	}

	public ISong getSong(final int r) {
		return this.songs.get(r);
	}

}
