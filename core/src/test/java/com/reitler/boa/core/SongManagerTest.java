package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.reitler.boa.core.interfaces.ISong;

public class SongManagerTest {

	@Test
	public void testCreateSong() {

		SongManager manager = new SongManager();

		ISong song1 = manager.createSong("title1");
		assertNotNull(song1);
		assertNotEquals(0, song1.getId());
		assertEquals("title1", song1.getTitle());

		ISong song2 = manager.createSong("title2");
		assertNotNull(song2);
		assertNotEquals(0, song2.getId());
		assertEquals("title2", song2.getTitle());

		assertNotEquals(song1.getId(), song2.getId());

		List<ISong> allSongs = manager.getAllSongs();
		assertEquals(2, allSongs.size());

		assertSame(song1, allSongs.get(0));
		assertSame(song2, allSongs.get(1));
	}

	@Test
	public void testDeleteSong() {
		SongManager manager = new SongManager();

		ISong song1 = manager.createSong("title1");
		assertNotNull(song1);
		assertNotEquals(0, song1.getId());
		assertEquals("title1", song1.getTitle());

		ISong song2 = manager.createSong("title2");
		assertNotNull(song2);
		assertNotEquals(0, song2.getId());
		assertEquals("title2", song2.getTitle());

		ISong song3 = manager.createSong("title3");
		assertNotNull(song3);
		assertNotEquals(0, song3.getId());
		assertEquals("title3", song3.getTitle());

		List<ISong> allSongs = manager.getAllSongs();
		assertEquals(3, allSongs.size());

		manager.deleteSong(song2.getId());
		allSongs = manager.getAllSongs();
		assertEquals(2, allSongs.size());
		assertTrue(allSongs.contains(song1));
		assertTrue(allSongs.contains(song1));

		manager.deleteSong(song3);
		allSongs = manager.getAllSongs();
		assertEquals(1, allSongs.size());
		assertTrue(allSongs.contains(song1));
	}

}
