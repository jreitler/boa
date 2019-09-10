package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISongManager;

public class DataManager {

	private final ISongManager songManager = new SongManager();

	public ISongManager getSongManager() {
		return this.songManager;
	}

}
