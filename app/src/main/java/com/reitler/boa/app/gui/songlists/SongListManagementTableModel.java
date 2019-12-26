package com.reitler.boa.app.gui.songlists;

import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISongList;

public class SongListManagementTableModel extends AbstractTableModel {

	private final List<ISongList> songLists;

	public SongListManagementTableModel(final List<ISongList> songLists) {
		this.songLists = songLists;
	}

	@Override
	public int getRowCount() {
		return this.songLists.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		if (columnIndex == 0) {
			return UIConstants.getSongListNameCaption();
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
		return false;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (columnIndex == 0) {
			return this.songLists.get(rowIndex).getName();
		}
		return null;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		// nothing to do
	}

	public ISongList getSongList(final int index) {
		return this.songLists.get(index);
	}

}
