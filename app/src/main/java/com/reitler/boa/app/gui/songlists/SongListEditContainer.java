package com.reitler.boa.app.gui.songlists;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.reitler.boa.app.gui.songs.SongSelectionDialog;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;

public class SongListEditContainer extends Container {

	private static final long serialVersionUID = -9070444017178255752L;

	private final ISongManager songManager;
	private final ISongList songList;
	private final ISongListManager songListManager;
	private final SongListEditModel model;

	public SongListEditContainer(final ISongManager songManager, final ISongList songList,
			final ISongListManager songListManager) {
		this.songManager = songManager;
		this.songList = songList;
		this.songListManager = songListManager;
		this.model = new SongListEditModel(songList, songListManager);
		createContainer();
	}

	private void createContainer() {

		setLayout(new BorderLayout());

		JTable table = new JTable(this.model);
		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(50);
		add(new JScrollPane(table), BorderLayout.CENTER);

		Container container = new Container();
		container.setLayout(new BorderLayout());

		Container buttonContainer = new Container();

		buttonContainer.setLayout(new GridLayout(2, 1));
		JButton addButton = new JButton();
		addButton.setAction(new AddSongAction());
		buttonContainer.add(addButton);
		buttonContainer.add(new JButton(new RemoveSongAction(table)));
		container.add(buttonContainer, BorderLayout.BEFORE_FIRST_LINE);
		add(container, BorderLayout.LINE_END);
	}

	private final class AddSongAction extends AbstractAction {
		private static final String TITLE = "Add Song";
		private static final long serialVersionUID = -5324451503239813652L;

		private AddSongAction() {
			super(AddSongAction.TITLE);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			selectSongsToAdd().forEach(
					s -> SongListEditContainer.this.songListManager.assign(s, SongListEditContainer.this.songList, ""));
		}
	}

	private List<ISong> selectSongsToAdd() {
		return new SongSelectionDialog(this.songManager, this.songList).selectSongs();
	}

	private final class RemoveSongAction extends AbstractAction {

		private static final String TITLE = "Remove";
		private static final long serialVersionUID = -3420828603160944101L;

		private final JTable table;

		private RemoveSongAction(final JTable table) {
			super(RemoveSongAction.TITLE);
			this.table = table;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			// TODO: error handling (e.g. no selection)
			int[] rows = this.table.getSelectedRows();
			List<ISong> toRemove = new LinkedList<>();
			for (int r : rows) {
				toRemove.add(SongListEditContainer.this.songList.getByPage().get(r).getSong());
			}
			toRemove.forEach(
					s -> SongListEditContainer.this.songListManager.unassign(s, SongListEditContainer.this.songList));
		}
	}
}
