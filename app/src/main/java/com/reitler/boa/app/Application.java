package com.reitler.boa.app;

import java.lang.System.Logger.Level;
import java.util.Optional;
import java.util.ServiceLoader;

import com.reitler.boa.app.gui.MainFrame;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;

public class Application {

	public static void main(final String[] args) {
		runDummy();
		ServiceLoader<ISongManager> load = ServiceLoader.load(ISongManager.class);
		ISongManager iSongManager = load.findFirst().get();

		ServiceLoader<ISongListManager> service = ServiceLoader.load(ISongListManager.class);
		ISongListManager songListManager = service.findFirst().get();
		new MainFrame(iSongManager, songListManager);

	}

	public static boolean runDummy() {
		ServiceLoader<ISongManager> loader = ServiceLoader.load(ISongManager.class);

		if (loader == null) {
			System.getLogger(Application.class.getCanonicalName()).log(Level.ERROR,
					"No Song Manager could be instantiated");
			return false;
		}

		Optional<ISongManager> first = loader.findFirst();
		if (!first.isPresent()) {
			System.getLogger(Application.class.getCanonicalName()).log(Level.ERROR,
					"No Song Manager could be instantiated");
			return false;
		}

		ISongManager manager = first.get();
		manager.createSong("Title1");

		return true;
	}

}
