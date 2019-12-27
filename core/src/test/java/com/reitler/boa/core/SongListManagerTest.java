package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListManagerTest {

	@Test
	public void testSongListCreationAndDeletion() {

		ISongListListener listener = Mockito.mock(ISongListListener.class);

		SongStorage storage = new SongStorage();
		SongListManager manager = new SongListManager(storage);
		manager.addListener(listener);

		// create two lists
		ISongList list1 = manager.createSongList("list1");
		ISongList list2 = manager.createSongList("list2");

		// assert that the listener is called on list creation
		Mockito.verify(listener, Mockito.times(2)).songListCreated(ArgumentMatchers.any(ISongList.class));

		// assert that the manager actually return all created lists
		List<ISongList> allLists = manager.getAllSongLists();
		assertEquals(2, allLists.size());

		assertSame(list1, allLists.get(0));
		assertSame(list2, allLists.get(1));

		// assert that the created lists are stored in the SongStorage
		List<SongList> storedLists = storage.getSongLists();
		assertEquals(2, storedLists.size());

		assertSame(list1, storedLists.get(0));
		assertSame(list2, storedLists.get(1));

		// delete one list
		manager.deleteSongList(list1);

		// assert that the listener is called for list removal as well
		Mockito.verify(listener, Mockito.times(1)).songListDeleted(list1);

		// assert that the list is properly removed from the manager...
		allLists = manager.getAllSongLists();
		assertEquals(1, allLists.size());
		assertSame(list2, allLists.get(0));

		// ... and from the SongStorage
		storedLists = storage.getSongLists();
		assertEquals(1, storedLists.size());

		assertSame(list2, storedLists.get(0));
	}

	@Test
	public void testSongAssignments() {
		SongStorage storage = new SongStorage();
		SongListManager manager = new SongListManager(storage);

		ISongListListener listener = Mockito.mock(ISongListListener.class);

		manager.addListener(listener);

		ISong song = Mockito.mock(ISong.class);
		Mockito.when(song.getId()).thenReturn(42);

		// create a list and assign a song
		ISongList list = manager.createSongList("testlist");
		manager.assign(song, list, "page1");

		// assert that the song is properly assigned to the list
		List<ISongAssignment> assignments = list.getByPage();
		assertEquals(1, assignments.size());
		assertEquals(song, assignments.get(0).getSong());
		assertEquals("page1", assignments.get(0).getPage());

		// assert that the listener called for the added assignment
		Mockito.verify(listener, Mockito.times(1)).assignmentAdded(ArgumentMatchers.eq(list),
				ArgumentMatchers.eq(assignments.get(0)));

		manager.unassign(song, list);

		Mockito.verify(listener, Mockito.times(1)).assignmentRemoved(ArgumentMatchers.eq(list),
				ArgumentMatchers.eq(assignments.get(0)));

		assignments = list.getByPage();
		assertEquals(0, assignments.size());
	}

	/**
	 * If a song is removed, all assignments of this song shall be removed from the
	 * songlists
	 */
	@Test
	public void testUpdateOnSongRemoval() {
		ISongListListener listener = Mockito.mock(ISongListListener.class);

		SongStorage storage = new SongStorage();
		SongListManager manager = new SongListManager(storage);
		manager.addListener(listener);

		ISongList list = manager.createSongList("testList");

		Song song1 = new Song(1);
		song1.setTitle("song1");
		song1.setArtist("artist");
		song1.setPublisher("publisher");

		Song song2 = new Song(2);
		song2.setTitle("song2");
		song2.setArtist("artist");
		song2.setPublisher("publisher");

		manager.assign(song1, list, "page1");
		manager.assign(song2, list, "page2");

		manager.songRemoved(song1);

		Mockito.verify(listener, Mockito.times(1)).assignmentRemoved(ArgumentMatchers.eq(list),
				ArgumentMatchers.argThat(argument -> argument.getSong().getTitle().equals("song1")));

		List<ISongAssignment> byPage = list.getByPage();
		assertEquals(1, byPage.size());
		assertEquals(song2, byPage.get(0).getSong());
	}

}
