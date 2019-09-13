package com.reitler.boa.core.service;

import com.reitler.boa.core.DataManager;
import com.reitler.boa.core.interfaces.ISongListManager;

public class SongListManagerServiceProvider {

	public static ISongListManager provider() {
		return DataManager.getInstance().getSongListManager();
	}
}
