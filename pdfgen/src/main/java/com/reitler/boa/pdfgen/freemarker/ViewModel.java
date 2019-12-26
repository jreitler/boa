package com.reitler.boa.pdfgen.freemarker;

import java.util.LinkedList;
import java.util.List;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;

public class ViewModel {

	private final List<Row> rowsByPage;
	private final List<Row> rowsByTitle;
	private final String name;

	public ViewModel(final ISongList list) {
		this.rowsByPage = createRowsByPage(list);
		this.rowsByTitle = createRowsByTitle(list);
		this.name = list.getName();
	}

	private List<ViewModel.Row> createRowsByPage(final ISongList list) {

		List<ISongAssignment> songs = list.getByPage();
		return createRows(songs);
	}

	private List<ViewModel.Row> createRowsByTitle(final ISongList list) {
		List<ISongAssignment> songs = list.getByTitle();
		return createRows(songs);
	}

	private List<ViewModel.Row> createRows(final List<ISongAssignment> songs) {
		List<ViewModel.Row> result = new LinkedList<>();
		int mid = songs.size() / 2;
		if ((songs.size() % 2) == 1) {
			mid++;
		}

		List<ISongAssignment> left = new LinkedList<>(songs.subList(0, mid));
		List<ISongAssignment> right = new LinkedList<>(songs.subList(mid, songs.size()));

		for (int i = 0; i < mid; i++) {
			ViewModel.Row row = new ViewModel.Row();
			ViewModel.Entry leftEntry = new ViewModel.Entry();
			leftEntry.setPage(left.get(i).getPage());
			leftEntry.setTitle(left.get(i).getSong().getTitle());
			row.setLeft(leftEntry);

			ViewModel.Entry rightEntry = new ViewModel.Entry();
			if (right.size() > i) {
				rightEntry.setPage(right.get(i).getPage());
				rightEntry.setTitle(right.get(i).getSong().getTitle());
			}
			row.setRight(rightEntry);

			result.add(row);
		}
		return result;
	}

	public List<Row> getRowsByPage() {
		return this.rowsByPage;
	}

	public List<Row> getRowsByTitle() {
		return this.rowsByTitle;
	}

	public String getName() {
		return this.name;
	}

	public class Entry {
		private String page = "";
		private String title = "";

		public String getPage() {
			return this.page;
		}

		public void setPage(final String page) {
			this.page = page;
		}

		public String getTitle() {
			return this.title;
		}

		public void setTitle(final String title) {
			this.title = title;
		}
	}

	public class Row {
		private Entry left;
		private Entry right;

		public Entry getLeft() {
			return this.left;
		}

		public void setLeft(final Entry left) {
			this.left = left;
		}

		public Entry getRight() {
			return this.right;
		}

		public void setRight(final Entry right) {
			this.right = right;
		}
	}
}
