package com.reitler.boa.pdfgen.freemarker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.fop.util.XMLUtil;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;

public class ViewModel {

	private final String name;

	private final List<PrintTable> tables = new ArrayList<>();

	public ViewModel(final ISongList list) {
		createRowsByPage(list);
		createRowsByTitle(list);
		this.name = list.getName();
	}

	private void createRowsByPage(final ISongList list) {

		List<ISongAssignment> songs = list.getByPage();
		createRows(songs);
	}

	private void createRowsByTitle(final ISongList list) {
		List<ISongAssignment> songs = list.getByTitle();
		createRows(songs);
	}

	private void createRows(final List<ISongAssignment> songs) {
		int mid = songs.size() / 2;
		if ((songs.size() % 2) == 1) {
			mid++;
		}
		PrintTable table = new PrintTable(mid, 4);
		table.setColumnWeights(new int[] { 10, 40, 10, 40 });
		this.tables.add(table);

		List<ISongAssignment> left = new LinkedList<>(songs.subList(0, mid));
		List<ISongAssignment> right = new LinkedList<>(songs.subList(mid, songs.size()));

		for (int i = 0; i < mid; i++) {

			table.setValue(i, 0, escape(left.get(i).getPage()));
			table.setValue(i, 1, escape(left.get(i).getSong().getTitle()));

			if (right.size() > i) {
				table.setValue(i, 2, escape(right.get(i).getPage()));
				table.setValue(i, 3, escape(right.get(i).getSong().getTitle()));
			}
		}
	}

	public String getName() {
		return this.name;
	}

	public List<PrintTable> getTables() {
		return this.tables;
	}

	private String escape(final String original) {
		return XMLUtil.escape(original);
	}
}
