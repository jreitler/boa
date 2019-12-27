package com.reitler.boa.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.HashMap;
import java.util.Map;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.factory.SongCreationParameter;

public class CSVImporter {

	private static final Logger LOG = System.getLogger(CSVImporter.class.getCanonicalName());

	private final ISongManager songManager;
	private final ISongListManager listManager;
	private final Map<String, ISong> songCache = new HashMap<>();
	private final Map<String, ISongList> listCache = new HashMap<>();

	public CSVImporter(final ISongManager songManager, final ISongListManager listManager) {
		this.songManager = songManager;
		this.listManager = listManager;
	}

	public void importData(final File file) {

		initCaches();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("#")) {
					readLine(line);
				}
			}
		} catch (IOException e) {
			String msg = String.format("Error while reading CSV file: %s", file.getAbsolutePath());
			CSVImporter.LOG.log(Level.ERROR, msg, e);
		}

	}

	private void initCaches() {
		this.songManager.getAllSongs().forEach(song -> this.songCache.put(song.getTitle(), song));
		this.listManager.getAllSongLists().forEach(list -> this.listCache.put(list.getName(), list));
	}

	private void readLine(final String line) {
		String[] split = line.split(";");

		String title = split[1];
		String artist = split[2];
		String publisher = split[3];
		String listNames = split[4];
		String page = split[5];

		SongCreationParameter parameter = new SongCreationParameter();
		parameter.title = title;
		parameter.artist = artist;
		parameter.publisher = publisher;

		ISong song = this.songCache.computeIfAbsent(title, t -> this.songManager.createSong(parameter));

		String[] lists = listNames.split("/");

		for (String listName : lists) {
			if (!listName.isBlank()) {
				ISongList list = this.listCache.computeIfAbsent(listName,
						name -> this.listManager.createSongList(name));
				this.listManager.assign(song, list, page);
			}
		}
	}

}
