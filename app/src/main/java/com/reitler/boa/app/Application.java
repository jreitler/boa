package com.reitler.boa.app;

import java.util.Optional;
import java.util.ServiceLoader;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;

public class Application {

	public static void main(final String[] args) {
		runDummy();
	}

	public static boolean runDummy() {
		ServiceLoader<ISongManager> loader = ServiceLoader.load(ISongManager.class);

		if (loader == null) {
			System.err.println("No Song Manager could be instantiated");
			return false;
		}

		Optional<ISongManager> first = loader.findFirst();
		if (!first.isPresent()) {
			System.err.println("No Song Manager could be instantiated");
			return false;
		}

		ISongManager manager = first.get();
		ISong song1 = manager.createSong("Title1");

		System.out.println(song1.getId());
		return true;
	}

}
