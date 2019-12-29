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
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.reitler.boa.app.gui.FilteredTable;
import com.reitler.boa.app.gui.ITabbedParent;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListManagementContainer extends Container {

	private static final long serialVersionUID = 6302922642227504918L;
	private final ISongListListener listener = new SongListListener();
	private final ISongListManager manager;
	private final ISongManager songManager;
	private final SongListManagementTableModel model;
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

		JTable table = new JTable(this.model);
		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				if ((e.getClickCount() == 2) && (table.getSelectedRowCount() == 1)) {
					editSongList(table.convertRowIndexToModel(table.getSelectedRow()));
				} else if (SwingUtilities.isRightMouseButton(e)) {
					JTable source = (JTable) e.getSource();
					int row = source.rowAtPoint(e.getPoint());
					int column = source.columnAtPoint(e.getPoint());

					if (!source.isRowSelected(row)) {
						source.changeSelection(row, column, false, false);
					}
					JPopupMenu popup = new JPopupMenu();
					if (table.getSelectedRowCount() == 1) {
						popup.add(new DuplicateSongListAction(table, SongListManagementContainer.this.manager));
					}

					popup.add(new EditSongListAction(table));
					popup.add(new DeleteSongListAction(table));
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		Container filteredTable = new FilteredTable(table, this.model);
		add(filteredTable, BorderLayout.CENTER);

		Container container = new Container();
		container.setLayout(new BorderLayout());

		Container buttonContainer = new Container();

		buttonContainer.setLayout(new GridBagLayout());

		GridBagConstraints layoutContraints = new GridBagConstraints();

		layoutContraints.gridx = 1;
		layoutContraints.ipadx = 20;
		layoutContraints.insets = (new Insets(2, 5, 2, 5));
		layoutContraints.fill = GridBagConstraints.BOTH;

		buttonContainer.add(new JButton(new CreateSongListAction()), layoutContraints);
		JButton editButton = new JButton(new EditSongListAction(table));
		buttonContainer.add(editButton, layoutContraints);
		buttonContainer.add(new JButton(new DeleteSongListAction(table)), layoutContraints);
		container.add(buttonContainer, BorderLayout.BEFORE_FIRST_LINE);
		add(container, BorderLayout.LINE_END);

		editButton.setEnabled(false);
		table.getSelectionModel()
				.addListSelectionListener(e -> editButton.setEnabled(table.getSelectedRowCount() == 1));

		this.manager.addListener(this.listener);
	}

	private void editSongList(final int selectedRow) {
		ISongList songList = (this.model.getSongList(selectedRow));

		JTextField label = new JTextField(songList.getName());
		label.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(final DocumentEvent e) {
				songList.setName(label.getText());
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				songList.setName(label.getText());
			}

			@Override
			public void changedUpdate(final DocumentEvent e) {
				songList.setName(label.getText());
			}
		});

		this.parent.addTab(new SongListEditContainer(this.songManager, songList, this.manager), label);
	}

	private void update() {
		this.model.setSongLists(this.manager.getAllSongLists());
	}

	private void createSongList() {
		String result = JOptionPane.showInputDialog(null, UIConstants.getSongListCreationMessage(),
				UIConstants.getSongListCreationCaption(), JOptionPane.PLAIN_MESSAGE);
		if (result != null && !result.trim().isEmpty()) {
			this.manager.createSongList(result);
		}
	}

	private final class DeleteSongListAction extends AbstractAction {

		private static final long serialVersionUID = -4539807900565654016L;
		private final JTable table;

		private DeleteSongListAction(final JTable table) {
			super(UIConstants.getDeleteSongListButton());
			this.table = table;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			int[] rows = this.table.getSelectedRows();
			List<String> toDelete = new LinkedList<>();
			for (int r : rows) {
				toDelete.add(SongListManagementContainer.this.model.getSongList(this.table.convertRowIndexToModel(r))
						.getName());
			}
			toDelete.forEach(SongListManagementContainer.this.manager::deleteSongList);
		}

	}

	private final class CreateSongListAction extends AbstractAction {

		private static final long serialVersionUID = -8881025720477846347L;

		private CreateSongListAction() {
			super(UIConstants.getCreateSongListButton());
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			createSongList();
		}
	}

	private final class EditSongListAction extends AbstractAction {

		private static final long serialVersionUID = 2794801031066708290L;
		private final JTable table;

		private EditSongListAction(final JTable table) {
			super(UIConstants.getEditListButton());
			this.table = table;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			editSongList(this.table.convertRowIndexToModel(this.table.getSelectedRow()));
		}
	}

	private final class DuplicateSongListAction extends AbstractAction {

		private static final long serialVersionUID = 7633810221582883130L;
		private final ISongListManager listManager;
		private final JTable t;

		private DuplicateSongListAction(final JTable t, final ISongListManager listManager) {
			super(UIConstants.getDuplicateSongListCaption());
			this.t = t;
			this.listManager = listManager;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {

			ISongList songList = ((SongListManagementTableModel) this.t.getModel())
					.getSongList(this.t.convertRowIndexToModel(this.t.getSelectedRow()));

			String listName = songList.getName();

			ISongList newlist = this.listManager.createSongList(listName + "(2)");
			songList.getByPage().forEach(a -> this.listManager.assign(a.getSong(), newlist, a.getPage()));

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
