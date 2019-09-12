package com.reitler.boa.core.service;

import com.reitler.boa.core.DataManager;
import com.reitler.boa.core.interfaces.ISongManager;

public class SongManagerServiceProvider {

	public static ISongManager provider() {
		return DataManager.getInstance().getSongManager();
	}

}
