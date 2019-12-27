package com.reitler.boa.app.gui.songlists;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.reitler.boa.app.gui.songs.SongSelectionDialog;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.pdfgen.PdfGenerator;

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

		buttonContainer.setLayout(new GridBagLayout());
		
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		
		layoutConstraints.gridx = 1;
		layoutConstraints.ipadx = 35;
		layoutConstraints.insets = new Insets(2 ,5, 2, 5);
		
		buttonContainer.add(new JButton(new AddSongAction()), layoutConstraints);
		buttonContainer.add(new JButton(new RemoveSongAction(table)), layoutConstraints);
		buttonContainer.add(new JButton(new PrintSongListAction(this.songList)), layoutConstraints);

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

	private final class PrintSongListAction extends AbstractAction {

		private static final String PDF_FILE_EXTENSION = ".pdf";
		private static final long serialVersionUID = 7664446593744493627L;
		private static final String TITLE = "Generate Pdf..";
		private final ISongList list;

		private PrintSongListAction(final ISongList list) {
			super(PrintSongListAction.TITLE);
			this.list = list;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			JFileChooser chooser = new JFileChooser(new File("."));
			chooser.setDialogTitle("Generate Pdf");
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);
			chooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
			chooser.setSelectedFile(new File("." + File.separator + this.list.getName() + PDF_FILE_EXTENSION));

			int result = chooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				if (!file.getAbsolutePath().endsWith(PDF_FILE_EXTENSION)) {
					file = new File(file.getAbsolutePath() + PDF_FILE_EXTENSION);
				}
				PdfGenerator generator = new PdfGenerator();
				generator.generate(this.list, file);
			}

		}

	}
}
