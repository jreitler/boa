package com.reitler.boa.app.gui.songs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.reitler.boa.app.gui.AbstractTableModel;
import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISong;

public class SongManagementModel extends AbstractTableModel {

	private final List<ISong> songs;

	public SongManagementModel(final List<ISong> songs) {
		this.songs = new LinkedList<>(songs);
	}

	@Override
	public int getRowCount() {
		return this.songs.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		switch (columnIndex) {
		case 0:
			return UIConstants.getSongTitleCaption();
		case 1:
			return UIConstants.getSongArtistCaption();
		case 2:
			return UIConstants.getSongPublisherCaption();
		case 3:
			return UIConstants.getSongTagsCaption();
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		if ((columnIndex >= 0) && (columnIndex <= 3)) {
			return String.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return (columnIndex >= 0) && (columnIndex <= 3);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		ISong song = getSong(rowIndex);
		switch (columnIndex) {
		case 0:
			return song.getTitle();
		case 1:
			return song.getArtist();
		case 2:
			return song.getPublisher();
		case 3:
			return String.join(" ", song.getTags());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		ISong song = getSong(rowIndex);
		String value = String.valueOf(aValue);
		switch (columnIndex) {
		case 0:
			song.setTitle(value);
			break;
		case 1:
			song.setArtist(value);
			break;
		case 2:
			song.setPublisher(value);
			break;
		case 3:
			updateTags(song, value);
			break;
		default:
		}
	}

	private void updateTags(final ISong song, final String value) {
		Set<String> existingTags = new HashSet<>(song.getTags());
		List<String> added = new ArrayList<>();
		List<String> removed = new ArrayList<>();

		LinkedHashSet<String> newTags = new LinkedHashSet<>(Arrays.asList(value.split(" ")));
		for (String t : newTags) {
			if (!existingTags.contains(t)) {
				added.add(t);
			}
		}

		for (String t : existingTags) {
			if (!newTags.contains(t)) {
				removed.add(t);
			}
		}

		removed.forEach(song::removeTag);
		added.forEach(song::addTag);

	}

	public ISong getSong(final int r) {
		return this.songs.get(r);
	}

}
