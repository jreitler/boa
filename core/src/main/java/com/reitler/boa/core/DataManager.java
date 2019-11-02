package com.reitler.boa.core;

public class DataManager {

	private static DataManager INSTANCE = new DataManager();

	private final SongStorage storage;
	private final SongManager songManager;
	private final SongListManager songListManager;

	public static DataManager getInstance() {
		return DataManager.INSTANCE;
	}

	private DataManager() {
		this.storage = new SongStorage();
		this.songListManager = new SongListManager(this.storage);
		this.songManager = new SongManager(this.storage);
		this.songManager.addSongListener(this.songListManager);
	}

	public SongManager getSongManager() {
		return this.songManager;
	}

	public SongListManager getSongListManager() {
		return this.songListManager;
	}

	public SongStorage getStorage() {
		return this.storage;
	}

	static synchronized void reset() {
		DataManager.INSTANCE = new DataManager();
	}

}
