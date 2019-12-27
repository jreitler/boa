package com.reitler.boa.app.gui.songlists;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.reitler.boa.app.gui.ITabbedParent;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListManagementContainer extends Container {

	private final ISongListListener listener = new SongListListener();
	private final ISongListManager manager;
	private final ISongManager songManager;
	private SongListManagementTableModel model;
	private JTable table;
	private final ITabbedParent parent;

	public SongListManagementContainer(final ISongListManager manager, final ISongManager songManager,
			final ITabbedParent parent) {
		this.manager = manager;
		this.songManager = songManager;
		this.model = new SongListManagementTableModel(manager.getAllSongLists());
		this.parent = parent;
		createContainer();
	}

	@Override
	public void removeNotify() {
		this.manager.removeListener(this.listener);
		super.removeNotify();
	}

	private void createContainer() {

		BorderLayout borderLayout = new BorderLayout();
		setLayout(borderLayout);

		this.table = new JTable(this.model);
		this.table.getColumnModel().getColumn(0).setMinWidth(50);
		this.table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				// TODO Auto-generated method stub
				JTable table = (JTable) e.getSource();
				if ((e.getClickCount() == 2) && (table.getSelectedRow() != -1)) {
					editSongList(table.getSelectedRow());
				}
			}

		});

		JScrollPane scrollPane = new JScrollPane(this.table);
		add(scrollPane, BorderLayout.CENTER);

		Container container = new Container();
		container.setLayout(new BorderLayout());

		Container buttonContainer = new Container();

		buttonContainer.setLayout(new GridBagLayout());

		GridBagConstraints layoutContraints = new GridBagConstraints();

		layoutContraints.gridx = 1;
		layoutContraints.ipadx = 20;
		layoutContraints.insets = new Insets(2, 5, 2, 5);

//		JButton addButton = new JButton();
//		addButton.setAction(new CreateSongListAction());
//		buttonContainer.add(addButton);
		buttonContainer.add(new JButton(new CreateSongListAction()), layoutContraints);
		buttonContainer.add(new JButton(new DeleteSongListAction(this.table)), layoutContraints);
		container.add(buttonContainer, BorderLayout.BEFORE_FIRST_LINE);
		add(container, BorderLayout.LINE_END);
		this.manager.addListener(this.listener);
	}

	private void editSongList(final int selectedRow) {
		ISongList songList = this.model.getSongList(selectedRow);

//		contentPane.add(new Label(""), BorderLayout.PAGE_START);
//		contentPane.add(new Label(""), BorderLayout.PAGE_END);
//		contentPane.add(new Label(""), BorderLayout.LINE_START);
//		contentPane.add(new Label(""), BorderLayout.LINE_END);

		this.parent.addTab(songList.getName(), new SongListEditContainer(this.songManager, songList, this.manager));
	}

	private void update() {
		this.model = new SongListManagementTableModel(this.manager.getAllSongLists());
		this.table.setModel(this.model);
	}

	private void createSongList() {
		String result = JOptionPane.showInputDialog(null, UIConstants.getSongListCreationMessage(),
				UIConstants.getSongListCreationCaption(), JOptionPane.PLAIN_MESSAGE);
		if (!"".equals(result)) {
			this.manager.createSongList(result);
		}
	}

	private final class DeleteSongListAction extends AbstractAction {

		private static final long serialVersionUID = -4539807900565654016L;
		private final JTable table;

		DeleteSongListAction(final JTable table) {
			super(UIConstants.getDeleteSongListButton());
			this.table = table;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			int[] rows = this.table.getSelectedRows();
			List<String> toDelete = new LinkedList<>();
			for (int r : rows) {
				toDelete.add(SongListManagementContainer.this.model.getSongList(r).getName());
			}
			toDelete.forEach(SongListManagementContainer.this.manager::deleteSongList);
		}

	}

	private final class CreateSongListAction extends AbstractAction {

		private static final long serialVersionUID = -8881025720477846347L;

		CreateSongListAction() {
			super(UIConstants.getCreateSongListButton());
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			createSongList();
		}

	}

	private final class SongListListener implements ISongListListener {

		@Override
		public void songListCreated(final ISongList list) {
			update();
		}

		@Override
		public void songListDeleted(final ISongList list) {
			update();
		}

		@Override
		public void assignmentAdded(final ISongList list, final ISongAssignment assignment) {
			update();
		}

		@Override
		public void assignmentRemoved(final ISongList list, final ISongAssignment assignment) {
			update();
		}

	}
}
