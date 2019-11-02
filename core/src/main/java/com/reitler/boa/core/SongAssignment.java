package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;

public class SongAssignment implements ISongAssignment {

	private final ISong song;
	private String page;

	public SongAssignment(final ISong song) {
		this.song = song;
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

}
