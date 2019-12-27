package com.reitler.boa.app.gui.songs;

import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISong;

public class SongSelectionTableModel extends AbstractTableModel {

	private final List<ISong> songs;

	public SongSelectionTableModel(final List<ISong> songs) {
		this.songs = songs;
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
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		return this.songs.get(rowIndex).getTitle();
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return UIConstants.getSongTitleCaption();
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return ISong.class;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		// TODO Auto-generated method stub

	}

}
