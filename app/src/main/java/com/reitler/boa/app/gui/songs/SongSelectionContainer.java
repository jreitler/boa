package com.reitler.boa.app.gui.songs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.reitler.boa.core.interfaces.ISong;

public class SongSelectionContainer extends Container {

	private static final long serialVersionUID = -8788104253935838066L;

	private final List<ISong> allSongs;

	private final SongSelectionTableModel model;

	private JTable table;

	public SongSelectionContainer(final List<ISong> allSongs) {
		this.allSongs = allSongs;
		this.model = new SongSelectionTableModel(allSongs);
		createContainer();
	}

	private void createContainer() {

		setLayout(new BorderLayout());

		this.table = new JTable(this.model);
		add(new JScrollPane(this.table), BorderLayout.CENTER);
		this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public List<ISong> getSelectedSongs() {
		List<ISong> selected = new ArrayList<>();
		int[] rows = this.table.getSelectedRows();
		for (int i : rows) {
			selected.add(this.allSongs.get(i));
		}
		return selected;
	}

}
