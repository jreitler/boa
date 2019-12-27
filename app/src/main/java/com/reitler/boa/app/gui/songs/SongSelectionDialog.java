package com.reitler.boa.app.gui.songs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongManager;

public class SongSelectionDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -8148919411468100070L;
	private final List<ISong> selectedSongs = new ArrayList<>();
	private final List<ISong> allDisplayedSongs;
	private SongSelectionContainer selectionContainer;

	public SongSelectionDialog(final ISongManager manager, final ISongList list) {
		this.allDisplayedSongs = getSongsToDisplay(manager, list);
	}

	private List<ISong> getSongsToDisplay(final ISongManager manager, final ISongList list) {
		List<ISong> allSongs = manager.getAllSongs();
		List<ISong> displaySongs = new LinkedList<>();
		Set<ISong> alreadyContainedSongs = new HashSet<>();
		list.getByPage().forEach(a -> alreadyContainedSongs.add(a.getSong()));

		for (ISong s : allSongs) {
			if (!alreadyContainedSongs.contains(s)) {
				displaySongs.add(s);
			}
		}
		return displaySongs;
	}

	public List<ISong> selectSongs() {
		createContainer();

		setModal(true);
		setVisible(true);
		return this.selectedSongs;
	}

	private void createContainer() {
		this.selectionContainer = new SongSelectionContainer(this.allDisplayedSongs);

		JPanel buttonPane = new JPanel();
		JButton okButton = new JButton(UIConstants.getButtonOk());
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		getRootPane().setDefaultButton(okButton);
		buttonPane.add(okButton);

		JButton cancelButton = new JButton(UIConstants.getButtonCancel());
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);

		setLayout(new BorderLayout());
		add(this.selectionContainer, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.AFTER_LAST_LINE);
		pack();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(300, 300);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			this.selectedSongs.addAll(this.selectionContainer.getSelectedSongs());
		}
		setVisible(false);
	}
}
