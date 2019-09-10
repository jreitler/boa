package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.reitler.boa.core.interfaces.ISongAssignment;

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

}
