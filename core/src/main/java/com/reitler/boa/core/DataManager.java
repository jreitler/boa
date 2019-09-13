package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;

public class DataManager {

	private static DataManager INSTANCE = new DataManager();

	private final ISongManager songManager = new SongManager();
	private final ISongListManager songListManager = new SongListManager();

	public static DataManager getInstance() {
		return DataManager.INSTANCE;
	}

	public ISongManager getSongManager() {
		return this.songManager;
	}

	public ISongListManager getSongListManager() {
		return this.songListManager;
	}

	static synchronized void reset() {
		DataManager.INSTANCE = new DataManager();
	}

}
