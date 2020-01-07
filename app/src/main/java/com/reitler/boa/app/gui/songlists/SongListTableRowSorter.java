package com.reitler.boa.app.gui.songlists;

import java.util.Comparator;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.reitler.boa.core.PageComparator;

/**
 * TableRowSorter to sort the Song Assignments while editing a song list
 */
public class SongListTableRowSorter extends TableRowSorter<TableModel> {

	public SongListTableRowSorter(final TableModel model) {
		super(model);
	}

	@Override
	public Comparator<?> getComparator(final int column) {
		if (column == 0) {
			return new PageComparator();
		}
		return super.getComparator(column);
	}

}
