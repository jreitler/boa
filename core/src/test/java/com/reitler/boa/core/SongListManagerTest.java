package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListManagerTest {

	@Test
	public void testSongListCreation() {

		Listener listener = new Listener();
		SongListManager manager = new SongListManager();
		manager.addSongListListener(listener);
		try {
			ISongList list2 = manager.createSongList("list2");
			ISongList list1 = manager.createSongList("list1");

			List<ISongList> allLists = manager.getAllSongLists();
			assertEquals(2, allLists.size());

			assertSame(list1, allLists.get(0));
			assertSame(list2, allLists.get(1));

			assertEquals(2, listener.lists.size());
			assertSame(list2, listener.lists.get(0));
			assertSame(list1, listener.lists.get(1));

		} finally {
			manager.removeSongListListener(listener);
		}
	}

	private class Listener implements ISongListListener {

		private final List<ISongList> lists = new ArrayList<>();

		@Override
		public void songListAdded(final ISongList list) {
			this.lists.add(list);
		}

		@Override
		public void songListRemoved(final ISongList list) {
			this.lists.remove(this.lists);
		}

	}
}
