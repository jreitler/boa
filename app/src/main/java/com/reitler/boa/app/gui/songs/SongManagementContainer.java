package com.reitler.boa.app.gui.songs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.reitler.boa.app.gui.FilteredTable;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListener;
import com.reitler.boa.core.interfaces.factory.SongCreationParameter;

public class SongManagementContainer extends Container {

	private static final long serialVersionUID = 1248811594328553729L;
	private final ISongManager songManager;
	private final SongManagementModel model;
	private final ISongListener listener = new SongListener();

	public SongManagementContainer(final ISongManager songManager) {
		this.songManager = songManager;
		this.model = new SongManagementModel(songManager.getAllSongs());
		createContainer();
	}

	private void createContainer() {

		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);

		JTable table = new JTable(this.model);
		table.getColumnModel().getColumn(0).setMinWidth(50);

		Container tableContainer = new FilteredTable(table, this.model);
		add(tableContainer, BorderLayout.CENTER);

		Container container = new Container();
		container.setLayout(new BorderLayout());

		Container buttonContainer = new Container();

		buttonContainer.setLayout(new GridBagLayout());
		GridBagConstraints layoutContraints = new GridBagConstraints();

		layoutContraints.gridx = 1;
		layoutContraints.ipadx = 20;
		layoutContraints.insets = new Insets(2, 5, 2, 5);
		layoutContraints.fill = GridBagConstraints.BOTH;

		buttonContainer.add(new JButton(new CreateSongAction()), layoutContraints);
		JButton editButton = new JButton(new ChangeSongAction(table));
		buttonContainer.add(editButton, layoutContraints);
		buttonContainer.add(new JButton(new DeleteSongAction(table)), layoutContraints);
		container.add(buttonContainer, BorderLayout.BEFORE_FIRST_LINE);
		add(container, BorderLayout.LINE_END);

		editButton.setEnabled(false);
		table.getSelectionModel()
				.addListSelectionListener(e -> editButton.setEnabled(table.getSelectedRowCount() == 1));

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				System.out.println(e.getClickCount());
				System.out.println(table.getSelectedColumnCount());
				if ((e.getClickCount() == 2) && (table.getSelectedRowCount() == 1)) {
					changeSong(table);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					JTable source = (JTable) e.getSource();
					int row = source.rowAtPoint(e.getPoint());
					int column = source.columnAtPoint(e.getPoint());

					if (!source.isRowSelected(row)) {
						source.changeSelection(row, column, false, false);
					}
					JPopupMenu popup = new JPopupMenu();
					popup.add(new ChangeSongAction(table));
					popup.add(new DeleteSongAction(table));
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		this.songManager.addListener(this.listener);
	}

	@Override
	public void removeNotify() {
		this.songManager.removeListener(this.listener);
		super.removeNotify();
	}

	private void update() {
		this.model.setSongs(this.songManager.getAllSongs());
	}

	private void createSong() {
		SongChangeDialog dialog = new SongChangeDialog();
		SongCreationParameter songParameter = dialog.showDialog();

		if (songParameter != null) {
			this.songManager.createSong(songParameter);
		}

	}

	private void changeSong(final JTable table) {
		ISong song = this.model.getSong(table.getSelectedRow());

		SongChangeDialog dialog = new SongChangeDialog(song);
		SongCreationParameter songparameter = dialog.showDialog();

		dialog.setModal(true);
		dialog.setVisible(true);

		if (songparameter != null) {
			song.setTitle(songparameter.title);
			song.setArtist(songparameter.artist);
			song.setPublisher(songparameter.publisher);
			song.setTags(songparameter.tags);
		}

		update();
	}

	private final class DeleteSongAction extends AbstractAction {

		private static final long serialVersionUID = -4539807900565654016L;
		private final JTable table;

		private DeleteSongAction(final JTable table) {
			super(UIConstants.getDeleteSongButton());
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

		CreateSongAction() {
			super(UIConstants.getCreateSongButton());
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			createSong();
		}

	}

	private final class ChangeSongAction extends AbstractAction {

		private static final long serialVersionUID = 2638458205955773214L;
		private final JTable table;

		private ChangeSongAction(final JTable table) {
			super(UIConstants.getEditSongButton());
			this.table = table;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			changeSong(this.table);
		}

	}

	final class SongListener implements ISongListener {

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
