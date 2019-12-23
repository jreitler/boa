package com.reitler.boa.core.service;

import com.reitler.boa.core.DataManager;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;
import com.reitler.boa.core.persistence.PersistenceHandler;

public class PersistenceHandlerServiceProvider {

	public static IPersistenceHandler provider() {
		DataManager dataManager = DataManager.getInstance();
		return new PersistenceHandler(dataManager.getSongManager(), dataManager.getSongListManager());
	}

}
