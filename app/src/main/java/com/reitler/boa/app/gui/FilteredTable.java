package com.reitler.boa.app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FilteredTable extends Container {

	private static final long serialVersionUID = -6186356493538447128L;
	private final TableRowSorter<TableModel> sorter;

	public FilteredTable(final JTable table, final TableModel model) {
		this.sorter = new TableRowSorter<>(model);
		table.setRowSorter(this.sorter);

		setLayout(new BorderLayout());
		JTextField searchBar = new HintTextField(UIConstants.getTypeFilterHint());
		searchBar.getDocument().addDocumentListener(new FilterDocumentListener(searchBar));
		add(searchBar, BorderLayout.BEFORE_FIRST_LINE);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	private void updateFilter(final String text) {
		if ((text != null) && !text.trim().isEmpty()) {
			this.sorter.setRowFilter(RowFilter.regexFilter("(?i).*" + text + ".*", 0, 1, 2, 3));
		} else {
			this.sorter.setRowFilter(null);
		}
	}

	private final class FilterDocumentListener implements DocumentListener {
		private final JTextField searchBar;

		private FilterDocumentListener(final JTextField searchBar) {
			this.searchBar = searchBar;
		}

		@Override
		public void removeUpdate(final DocumentEvent e) {
			updateFilter(this.searchBar.getText());
		}

		@Override
		public void insertUpdate(final DocumentEvent e) {
			updateFilter(this.searchBar.getText());
		}

		@Override
		public void changedUpdate(final DocumentEvent e) {
			updateFilter(this.searchBar.getText());
		}
	}

	private class HintTextField extends JTextField {
		private static final long serialVersionUID = 2806037961588084765L;

		private final String hint;

		public HintTextField(final String hint) {
			this.hint = hint;
		}

		@Override
		public void paint(final Graphics g) {
			super.paint(g);
			if (getText().length() == 0) {
				int h = getHeight();
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				Insets ins = getInsets();
				FontMetrics fm = g.getFontMetrics();
				int c0 = getBackground().getRGB();
				int c1 = getForeground().getRGB();
				int m = 0xfefefefe;
				int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
				g.setColor(new Color(c2, true));
				g.drawString(this.hint, ins.left, ((h / 2) + (fm.getAscent() / 2)) - 2);
			}
		}

	}

}
