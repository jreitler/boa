package com.reitler.boa.app.gui.songs;

import java.util.LinkedList;
import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.core.interfaces.ISong;

public class SongManagementModel extends AbstractTableModel {

	private List<ISong> songs;

	public SongManagementModel(List<ISong> songs) {
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
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0) {
			return "title";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return String.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return this.songs.get(rowIndex).getTitle();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		//
	}

	public ISong getSong(int r) {
		return this.songs.get(r);
	}

}
