package com.reitler.boa.core;

import org.junit.jupiter.api.Test;

public class SongListTest {

	@Test
	public void testSongList() {
//		SongList list = new SongList("testList");
//
//		Song song = new Song(1);
//		song.setTitle("song1");
//		SongAssignment assignment = new SongAssignment(song);
//		assignment.setPage("12");
//		list.add(assignment);
//
//		song = new Song(2);
//		song.setTitle("aSong");
//		assignment = new SongAssignment(song);
//		assignment.setPage("12a");
//		list.add(assignment);
//
//		List<ISongAssignment> byPage = list.getByPage();
//		assertEquals(2, byPage.size());
//		assertEquals("song1", byPage.get(0).getSong().getTitle());
//		assertEquals("aSong", byPage.get(1).getSong().getTitle());
//
//		List<ISongAssignment> byTitle = list.getByTitle();
//		assertEquals(2, byTitle.size());
//		assertEquals("aSong", byTitle.get(0).getSong().getTitle());
//		assertEquals("song1", byTitle.get(1).getSong().getTitle());
	}

	@Test
	public void testNotification() {
//		Listener l = new Listener();
//		SongList list = new SongList("testList");
//		list.addSongAssignmentListener(l);
//
//		try {
//			Song song = new Song(1);
//			song.setTitle("song1");
//			SongAssignment assignment1 = new SongAssignment(song);
//			assignment1.setPage("12");
//			list.add(assignment1);
//
//			song = new Song(2);
//			song.setTitle("aSong");
//			SongAssignment assignment2 = new SongAssignment(song);
//			assignment2.setPage("12a");
//			list.add(assignment2);
//
//			assertEquals(2, l.assignments.size());
//			assertTrue(l.assignments.contains(assignment1));
//			assertTrue(l.assignments.contains(assignment2));
//
//			list.remove(assignment1);
//		} finally {
//			list.removeSongAssignmentListener(l);
//		}
	}

}
