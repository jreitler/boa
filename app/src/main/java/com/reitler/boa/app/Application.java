package com.reitler.boa.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.reitler.boa.app.gui.MainFrame;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;
import com.reitler.boa.core.service.PersistenceHandlerServiceProvider;
import com.reitler.boa.core.service.SongListManagerServiceProvider;
import com.reitler.boa.core.service.SongManagerServiceProvider;

public class Application {

	public static void main(final String[] args) {
		ISongManager iSongManager = SongManagerServiceProvider.provider();

		ISongListManager songListManager = SongListManagerServiceProvider.provider();

		@SuppressWarnings("resource")
		IPersistenceHandler handler = PersistenceHandlerServiceProvider.provider();
		File file = new File("boa.db3");
		handler.open(file);

		MainFrame mainFrame = new MainFrame(iSongManager, songListManager);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				try {
					handler.close();
				} catch (IOException e1) {
					Logger.getLogger(Application.class.getCanonicalName()).log(Level.SEVERE,
							"Error on closing DB handler", e);
				}
			}

		});
	}
}
