package com.reitler.boa.pdfgen.freemarker;

import java.util.Arrays;

public class PrintTable {

	private final int rows;
	private final int cols;
	private final String[][] values;
	private String[] captions;
	private int[] columnWeights;

	public PrintTable(final int rows, final int cols) {
		this.rows = rows;
		this.cols = cols;
		this.values = new String[this.rows][this.cols];
	}

	public int getCols() {
		return this.cols;
	}

	public int getRows() {
		return this.rows;
	}

	public void setColumnWeights(final int[] weights) {
		this.columnWeights = Arrays.copyOf(weights, Math.min(weights.length, this.cols));
	}

	public void setCaptions(final String[] captions) {
		this.captions = Arrays.copyOf(captions, Math.min(captions.length, this.cols));
		if (this.captions.length < captions.length) {
			for (int i = captions.length; i < captions.length; i++) {
				captions[i] = "";
			}
		}
	}

	public void setValue(final int row, final int col, final String value) {
		if ((row < this.rows) && (col < this.cols)) {
			this.values[row][col] = value;
		}
	}

	public String getCaption(final int col) {
		if (col >= this.cols) {
			return "";
		}
		return this.captions[col];
	}

	public int getColumnWeight(final int col) {
		if (col >= this.cols) {
			return 0;
		}
		return this.columnWeights[col];
	}

	public String getValue(final int row, final int col) {
		if ((row >= this.rows) || (col >= this.cols)) {
			return " ";
		}
		return this.values[row][col] == null ? "" : this.values[row][col];
	}

	public boolean hasCaptions() {
		if (this.captions == null) {
			return false;
		}
		return this.captions.length == this.cols;
	}
}
