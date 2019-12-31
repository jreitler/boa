package com.reitler.boa.app.gui.songlists;

import java.util.ArrayList;
import java.util.List;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.core.api.Constants;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListEditModel extends AbstractTableModel {

	private final ISongListListener listener = new SongListListener();
	private final ISongListManager manager;
	private List<ISongAssignment> assignments;
	private ISongList songList;

	public SongListEditModel(final ISongList list, final ISongListManager manager) {
		this.manager = manager;
		setSongList(list);
	}

	public void setSongList(final ISongList list) {
		if (this.songList != null) {
			this.songList.removeListener(this.listener);
		}
		if (list != null) {
			this.assignments = new ArrayList<>(list.getByPage());
			this.songList = list;
			list.addListener(this.listener);
		}
	}

	@Override
	public int getRowCount() {
		return this.assignments.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return columnIndex == 0 ? Constants.getAssignmentPageCaption() : Constants.getSongTitleCaption();
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return columnIndex == 0;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		if (this.assignments.size() >= (rowIndex + 1)) {
			ISongAssignment assignment = this.assignments.get(rowIndex);
			return columnIndex == 0 ? assignment.getPage() : assignment.getSong().getTitle();
		}
		return null;
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		if ((this.assignments.size() >= (rowIndex + 1)) && (columnIndex == 0)) {
			ISong song = this.assignments.get(rowIndex).getSong();
			this.manager.unassign(song, this.songList);
			this.manager.assign(song, this.songList, String.valueOf(aValue));
			update();
		}

	}

	@Override
	protected void update() {
		SongListEditModel.this.assignments.clear();
		SongListEditModel.this.assignments.addAll(this.songList.getByPage());
		super.update();
	}

	private class SongListListener implements ISongListListener {

		@Override
		public void assignmentAdded(final ISongList source, final ISongAssignment addedSong) {
			update();
		}

		@Override
		public void assignmentRemoved(final ISongList source, final ISongAssignment removedSong) {
			update();
		}

		@Override
		public void songListCreated(final ISongList list) {
			update();
		}

		@Override
		public void songListDeleted(final ISongList list) {
			update();
		}

	}

}
