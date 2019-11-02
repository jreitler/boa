package com.reitler.boa.core;

import java.io.File;
import java.io.IOException;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.persistence.PersistenceHandler;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(final String[] args) {
		System.out.println("Hello World!");

		SongStorage store = DataManager.getInstance().getStorage();
		try (PersistenceHandler h = new PersistenceHandler()) {
			h.open(new File("/home/jan/temp/test.db3"), store);
			SongListManager listManager = DataManager.getInstance().getSongListManager();
			listManager.setSongAssignmentListener(h);
			listManager.addSongListListener(h);
			SongManager songManager = DataManager.getInstance().getSongManager();
			songManager.addSongListener(h);

			ISong song4 = songManager.createSong("song no 4");
			ISongList list3 = listManager.createSongList("thirdList");
			listManager.assign(song4, list3, "page1");

			for (ISong s : store.getSongs()) {
				System.out.println(String.format("Song Id: %d title: %s", s.getId(), s.getTitle()));
			}

			for (ISongList l : store.getSongLists()) {
				System.out.println(String.format("SongList: %s", l.getName()));
				for (ISongAssignment a : l.getByPage()) {
					System.out.println(String.format("Page: %s title: %s", a.getPage(), a.getSong().getTitle()));
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
