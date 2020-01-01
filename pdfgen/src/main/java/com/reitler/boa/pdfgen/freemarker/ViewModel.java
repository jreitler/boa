package com.reitler.boa.pdfgen.freemarker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.fop.util.XMLUtil;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.pdfgen.PdfGenerationParameter;

public class ViewModel {

	private final String name;

	private final List<PrintTable> tables = new ArrayList<>();

	public ViewModel(final PdfGenerationParameter parameter) {
		this.name = parameter.getList().getName();
		createTables(parameter);
	}

	private void createTables(final PdfGenerationParameter parameter) {
		List<ISongAssignment> songs = parameter.getSortOrder() == 0 ? parameter.getList().getByPage()
				: parameter.getList().getByTitle();

		int sections = parameter.isSplitTable() ? 2 : 1;

		int mid = songs.size() / sections;
		if ((songs.size() % sections) == 1) {
			mid++;
		}

		List<ISongAssignment> left = new LinkedList<>(songs.subList(0, mid));
		List<ISongAssignment> right = new LinkedList<>(songs.subList(mid, songs.size()));

		if (parameter.includePages()) {
			PrintTable table = new PrintTable(mid, 2 * sections);
			int[] weights = new int[2 * sections];
			for (int i = 0; i < (2 * sections); i++) {
				if ((i % 2) == 0) {
					weights[i] = (100 / sections) / 5;
				} else {
					weights[i] = ((100 / sections) / 5) * 4;
				}
			}
			table.setColumnWeights(weights);
			this.tables.add(table);

			for (int i = 0; i < mid; i++) {

				table.setValue(i, 0, escape(left.get(i).getPage()));
				table.setValue(i, 1, escape(left.get(i).getSong().getTitle()));

				if (right.size() > i) {
					table.setValue(i, 2, escape(right.get(i).getPage()));
					table.setValue(i, 3, escape(right.get(i).getSong().getTitle()));
				}
			}
		} else {
			PrintTable table = new PrintTable(mid, sections);
			int[] weights = new int[sections];
			for (int i = 0; i < (sections); i++) {
				weights[i] = 100 % sections;
			}
			table.setColumnWeights(new int[] { 50, 50 });
			this.tables.add(table);
			for (int i = 0; i < mid; i++) {

				table.setValue(i, 0, escape(left.get(i).getSong().getTitle()));

				if (right.size() > i) {
					table.setValue(i, 1, escape(right.get(i).getSong().getTitle()));
				}
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
