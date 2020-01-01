package com.reitler.boa.pdfgen;

import java.io.File;

import com.reitler.boa.core.interfaces.ISongList;

public class PdfGenerationParameter {

	private File targetFile;
	private ISongList list;
	private boolean includePages = true;
	private int sortOrder = 0; // 0 = bypage, 1 = bytitle
	private boolean splitTable;

	public File getTargetFile() {
		return this.targetFile;
	}

	public void setTargetFile(final File targetFile) {
		this.targetFile = targetFile;
	}

	public ISongList getList() {
		return this.list;
	}

	public void setIncludePages(final boolean includePages) {
		this.includePages = includePages;
	}

	public boolean includePages() {
		return this.includePages;
	}

	public void setList(final ISongList list) {
		this.list = list;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getSortOrder() {
		return this.sortOrder;
	}

	public void setSplitTable(final boolean split) {
		this.splitTable = split;
	}

	public boolean isSplitTable() {
		return this.splitTable;
	}
}
