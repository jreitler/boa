package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;

public class SongAssignment implements ISongAssignment {

	private final ISong song;
	private String page;
	private final int id;

	public SongAssignment(final int id, final ISong song) {
		this.song = song;
		this.id = id;
	}

	@Override
	public ISong getSong() {
		return this.song;
	}

	@Override
	public String getPage() {
		return this.page;
	}

	@Override
	public void setPage(final String page) {
		this.page = page;
	}

	public int getId() {
		return this.id;
	}

}
