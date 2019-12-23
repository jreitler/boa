package com.reitler.boa.core.service;

import com.reitler.boa.core.DataManager;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;
import com.reitler.boa.core.persistence.PersistenceHandler;

public class PersistenceHandlerServiceProvider {

	public static IPersistenceHandler provider() {
		return new PersistenceHandler(DataManager.getInstance().getStorage());
	}

}
