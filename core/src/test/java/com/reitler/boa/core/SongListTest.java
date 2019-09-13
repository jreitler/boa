package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListTest {

	@Test
	public void testSongList() {
		SongList list = new SongList("testList");

		Song song = new Song(1);
		song.setTitle("song1");
		SongAssignment assignment = new SongAssignment(song);
		assignment.setPage("12");
		list.add(assignment);

		song = new Song(2);
		song.setTitle("aSong");
		assignment = new SongAssignment(song);
		assignment.setPage("12a");
		list.add(assignment);

		List<ISongAssignment> byPage = list.getByPage();
		assertEquals(2, byPage.size());
		assertEquals("song1", byPage.get(0).getSong().getTitle());
		assertEquals("aSong", byPage.get(1).getSong().getTitle());

		List<ISongAssignment> byTitle = list.getByTitle();
		assertEquals(2, byTitle.size());
		assertEquals("aSong", byTitle.get(0).getSong().getTitle());
		assertEquals("song1", byTitle.get(1).getSong().getTitle());
	}

	@Test
	public void testNotification() {
		Listener l = new Listener();
		SongList list = new SongList("testList");
		list.addSongListListener(l);

		try {
			Song song = new Song(1);
			song.setTitle("song1");
			SongAssignment assignment1 = new SongAssignment(song);
			assignment1.setPage("12");
			list.add(assignment1);

			song = new Song(2);
			song.setTitle("aSong");
			SongAssignment assignment2 = new SongAssignment(song);
			assignment2.setPage("12a");
			list.add(assignment2);

			assertEquals(2, l.assignments.size());
			assertTrue(l.assignments.contains(assignment1));
			assertTrue(l.assignments.contains(assignment2));

			list.remove(assignment1);
		} finally {
			list.removeSongListListener(l);
		}
	}

	private class Listener implements ISongListListener {

		private final List<ISongAssignment> assignments = new ArrayList<>();

		@Override
		public void assignmentAdded(final ISongList source, final ISongAssignment addedSong) {
			this.assignments.add(addedSong);
		}

		@Override
		public void assignmentRemoved(final ISongList source, final ISongAssignment removedSong) {
			this.assignments.remove(removedSong);
		}

	}

}
