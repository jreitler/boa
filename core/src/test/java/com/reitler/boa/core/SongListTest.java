package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListTest {

	@Test
	public void testSongList() {
		ISongListListener listener = Mockito.mock(ISongListListener.class);

		SongList list = new SongList(1, "testList");
		list.addListener(listener);

		Song song = new Song(1);
		song.setTitle("song1");
		SongAssignment assignment = new SongAssignment(1, song);
		assignment.setPage("12");
		list.add(assignment);

		Mockito.verify(listener, Mockito.times(1)).assignmentAdded(list, assignment);

		song = new Song(2);
		song.setTitle("aSong");
		assignment = new SongAssignment(2, song);
		assignment.setPage("12a");
		list.add(assignment);

		Mockito.verify(listener, Mockito.times(1)).assignmentAdded(list, assignment);

		List<ISongAssignment> byPage = list.getByPage();
		assertEquals(2, byPage.size());
		assertEquals("song1", byPage.get(0).getSong().getTitle());
		assertEquals("aSong", byPage.get(1).getSong().getTitle());

		List<ISongAssignment> byTitle = list.getByTitle();
		assertEquals(2, byTitle.size());
		assertEquals("aSong", byTitle.get(0).getSong().getTitle());
		assertEquals("song1", byTitle.get(1).getSong().getTitle());

		list.remove(assignment);

		Mockito.verify(listener, Mockito.times(1)).assignmentRemoved(list, assignment);

		byPage = list.getByPage();
		assertEquals(1, byPage.size());
		assertEquals("song1", byPage.get(0).getSong().getTitle());

		byTitle = list.getByTitle();
		assertEquals(1, byTitle.size());
		assertEquals("song1", byTitle.get(0).getSong().getTitle());
	}

}
