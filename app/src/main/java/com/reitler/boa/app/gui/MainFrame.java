package com.reitler.boa.app.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.reitler.boa.app.CSVImporter;
import com.reitler.boa.app.gui.songlists.SongListManagementContainer;
import com.reitler.boa.app.gui.songs.SongManagementContainer;
import com.reitler.boa.core.api.Constants;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -2599442231433868789L;

	private final ISongManager songManager;
	private final ISongListManager listManager;

	public MainFrame(final ISongManager songManager, final ISongListManager songListManager) {
		this.songManager = songManager;
		this.listManager = songListManager;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
		super.setLayout(new GridBagLayout());
		GridBagConstraints layoutContraints = new GridBagConstraints();

		JTabbedPaneWithCloseButton cards = new JTabbedPaneWithCloseButton();

		cards.addTabNoClose(Constants.getAllSongsTitle(), new SongManagementContainer(songManager, this.listManager));
		cards.addTabNoClose(Constants.getAllSongListsTitle(),
				new SongListManagementContainer(songListManager, songManager, cards));

		layoutContraints.insets = new Insets(10, 10, 10, 10);
		layoutContraints.gridwidth = GridBagConstraints.RELATIVE;
		layoutContraints.gridheight = GridBagConstraints.RELATIVE;
		layoutContraints.fill = GridBagConstraints.BOTH;

		layoutContraints.weightx = 1.0;
		layoutContraints.weighty = 1.0;

		setTitle(readTitle());

		super.add(cards, layoutContraints);
		super.pack();
		setBounds(300, 300, getSize().width, getSize().height);
		setVisible(true);
		setEnabled(true);

	}

	private String readTitle() {
		String name = "boa - ";
		String version = getClass().getPackage().getImplementationVersion();
		if (version == null) {
			return name.concat("development version");
		}
		return name.concat(version);
	}

	private JMenuBar createMenuBar() {
		JMenuBar result = new JMenuBar();

		JMenu menu = new JMenu(Constants.getFileMenu());
		JMenuItem item = new JMenuItem(Constants.getImportCsvFile());
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				importFile();
			}
		});
		menu.add(item);
		result.add(menu);

		return result;
	}

	private void importFile() {

		JFileChooser chooser = new JFileChooser(new File("."));
		chooser.setDialogTitle(Constants.getImportCsvFile());
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv", "txt"));

		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file.exists()) {
				new CSVImporter(this.songManager, this.listManager).importData(file);
			}
		}
	}

}
