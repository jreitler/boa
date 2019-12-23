package com.reitler.boa.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ServiceLoader;

import com.reitler.boa.app.gui.MainFrame;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;

public class Application {

	public static void main(final String[] args) {
		ServiceLoader<ISongManager> load = ServiceLoader.load(ISongManager.class);
		ISongManager iSongManager = load.findFirst().get();

		ServiceLoader<ISongListManager> service = ServiceLoader.load(ISongListManager.class);
		ISongListManager songListManager = service.findFirst().get();

		ServiceLoader<IPersistenceHandler> persistenceService = ServiceLoader.load(IPersistenceHandler.class);
		@SuppressWarnings("resource")
		IPersistenceHandler handler = persistenceService.findFirst().get();
		handler.open(new File("/home/jan/temp/test.db3"));

		MainFrame mainFrame = new MainFrame(iSongManager, songListManager);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				try {
					handler.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}
}
