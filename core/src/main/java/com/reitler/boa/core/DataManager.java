package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISongManager;

public class DataManager {

	private static DataManager INSTANCE = new DataManager();

	private final ISongManager songManager = new SongManager();

	public static DataManager getInstance() {
		return DataManager.INSTANCE;
	}

	public ISongManager getSongManager() {
		return this.songManager;
	}

	static synchronized void reset() {
		DataManager.INSTANCE = new DataManager();
	}

}
