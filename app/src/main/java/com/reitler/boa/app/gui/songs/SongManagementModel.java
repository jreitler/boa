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
		return 1;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		if (columnIndex == 0) {
			return "title";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if (columnIndex == 0) {
			return String.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return columnIndex == 0;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (columnIndex == 0) {
			return this.songs.get(rowIndex).getTitle();
		}
		return null;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		this.songs.get(rowIndex).setTitle(String.valueOf(aValue));
	}

	public ISong getSong(final int r) {
		return this.songs.get(r);
	}

}
