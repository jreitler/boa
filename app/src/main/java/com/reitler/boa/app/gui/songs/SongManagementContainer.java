package com.reitler.boa.app.gui.songs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongManagementContainer extends Container {

	private static final long serialVersionUID = 1248811594328553729L;
	private final ISongManager songManager;
	private SongManagementModel model;
	private JTable table;
	private final ISongListener listener = new SongListener();

	public SongManagementContainer(final ISongManager songManager) {
		this.songManager = songManager;
		this.model = new SongManagementModel(songManager.getAllSongs());
		createContainer();
	}

	private void createContainer() {

		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);

		this.table = new JTable(this.model);
		this.table.getColumnModel().getColumn(0).setMinWidth(50);

		JScrollPane scrollPane = new JScrollPane(this.table);
		add(scrollPane, BorderLayout.CENTER);

		Container container = new Container();
		container.setLayout(new BorderLayout());

		Container buttonContainer = new Container();

		buttonContainer.setLayout(new GridLayout(2, 1));
		JButton addButton = new JButton();
		addButton.setAction(new CreateSongAction());
		buttonContainer.add(addButton);
		buttonContainer.add(new JButton(new DeleteSongAction(this.table)));
		container.add(buttonContainer, BorderLayout.BEFORE_FIRST_LINE);
		add(container, BorderLayout.LINE_END);
		this.songManager.addListener(this.listener);
	}

	@Override
	public void removeNotify() {
		this.songManager.removeListener(this.listener);
		super.removeNotify();
	}

	private void update() {
		this.model = new SongManagementModel(this.songManager.getAllSongs());
		this.table.setModel(this.model);
	}

	private void createSong() {
		String result = JOptionPane.showInputDialog(null, "Please enter name of the song", "Create song",
				JOptionPane.PLAIN_MESSAGE);
		if (!"".equals(result)) {
			this.songManager.createSong(result);
		}
	}

	private final class DeleteSongAction extends AbstractAction {

		private static final long serialVersionUID = -4539807900565654016L;
		private static final String TITLE = "Delete";
		private final JTable table;

		DeleteSongAction(final JTable table) {
			super(DeleteSongAction.TITLE);
			this.table = table;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			int[] rows = this.table.getSelectedRows();
			List<ISong> toDelete = new LinkedList<>();
			for (int r : rows) {
				toDelete.add(SongManagementContainer.this.model.getSong(r));
			}
			toDelete.forEach(SongManagementContainer.this.songManager::deleteSong);
		}

	}

	private final class CreateSongAction extends AbstractAction {

		private static final long serialVersionUID = -8881025720477846347L;
		private static final String TITLE = "Create";

		CreateSongAction() {
			super(CreateSongAction.TITLE);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			createSong();
		}

	}

	private final class SongListener implements ISongListener {

		@Override
		public void songAdded(final ISong addedSong) {
			update();
		}

		@Override
		public void songRemoved(final ISong removedSong) {
			update();
		}

		@Override
		public void songChanged(final ISong oldValue, final ISong newValue) {
			update();
		}
	}

}
