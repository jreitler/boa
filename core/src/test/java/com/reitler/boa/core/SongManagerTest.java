package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.events.ISongListener;
import com.reitler.boa.core.interfaces.factory.SongCreationParameter;

public class SongManagerTest {

	@Test
	public void testSongCreationAndDeletion() {
		ISongListener listener = Mockito.mock(ISongListener.class);

		SongStorage storage = new SongStorage();
		SongManager manager = new SongManager(storage);
		manager.addListener(listener);

		SongCreationParameter param1 = new SongCreationParameter();
		param1.title = "title1";
		param1.artist = "artist";
		param1.publisher = "publisher";

		ISong song1 = manager.createSong(param1);
		assertNotNull(song1);
		assertNotEquals(0, song1.getId());
		assertEquals("title1", song1.getTitle());
		Mockito.verify(listener, Mockito.times(1)).songAdded(song1);

		SongCreationParameter param2 = new SongCreationParameter();
		param2.title = "title2";
		param2.artist = "artist";
		param2.publisher = "publisher";

		ISong song2 = manager.createSong(param2);
		assertNotNull(song2);
		assertNotEquals(0, song2.getId());
		assertEquals("title2", song2.getTitle());
		Mockito.verify(listener, Mockito.times(1)).songAdded(song1);

		assertNotEquals(song1.getId(), song2.getId());

		List<ISong> allSongs = manager.getAllSongs();
		assertEquals(2, allSongs.size());
		assertSame(song1, allSongs.get(0));
		assertSame(song2, allSongs.get(1));

		List<Song> storedSongs = storage.getSongs();
		assertEquals(2, storedSongs.size());
		assertEquals(song1, storedSongs.get(0));
		assertEquals(song2, storedSongs.get(1));

		manager.deleteSong(song1);
		Mockito.verify(listener, Mockito.times(1)).songRemoved(song1);

		allSongs = manager.getAllSongs();
		assertEquals(1, allSongs.size());
		assertEquals(song2, allSongs.get(0));

		storedSongs = storage.getSongs();
		assertEquals(1, storedSongs.size());
		assertEquals(song2, allSongs.get(0));
	}
}
