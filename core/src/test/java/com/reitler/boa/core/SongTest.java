package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongTest {

	@Test
	public void testTitleChangeNotification() {

		Song song = new Song(1);
		song.setTitle("testSong");
		song.setArtist("artist");
		song.setPublisher("publisher");

		ISongListener listener = Mockito.mock(ISongListener.class);
		song.addListener(listener);

		song.setTitle("newTitle");

		ArgumentCaptor<ISong> oldValueCaptor = ArgumentCaptor.forClass(ISong.class);
		ArgumentCaptor<ISong> newValueCaptor = ArgumentCaptor.forClass(ISong.class);

		Mockito.verify(listener, Mockito.times(1)).songChanged(oldValueCaptor.capture(), newValueCaptor.capture());

		assertEquals("testSong", oldValueCaptor.getValue().getTitle());
		assertEquals("artist", oldValueCaptor.getValue().getArtist());
		assertEquals("publisher", oldValueCaptor.getValue().getPublisher());
		assertEquals(1, oldValueCaptor.getValue().getId());

		assertEquals("newTitle", newValueCaptor.getValue().getTitle());
		assertEquals("artist", newValueCaptor.getValue().getArtist());
		assertEquals("publisher", newValueCaptor.getValue().getPublisher());
		assertEquals(1, newValueCaptor.getValue().getId());
	}

	@Test
	public void testArtistChangeNotification() {
		Song song = new Song(1);
		song.setTitle("testSong");
		song.setArtist("artist");
		song.setPublisher("publisher");

		ISongListener listener = Mockito.mock(ISongListener.class);
		song.addListener(listener);

		song.setArtist("newArtist");

		ArgumentCaptor<ISong> oldValueCaptor = ArgumentCaptor.forClass(ISong.class);
		ArgumentCaptor<ISong> newValueCaptor = ArgumentCaptor.forClass(ISong.class);

		Mockito.verify(listener, Mockito.times(1)).songChanged(oldValueCaptor.capture(), newValueCaptor.capture());

		assertEquals("testSong", oldValueCaptor.getValue().getTitle());
		assertEquals("artist", oldValueCaptor.getValue().getArtist());
		assertEquals("publisher", oldValueCaptor.getValue().getPublisher());
		assertEquals(1, oldValueCaptor.getValue().getId());

		assertEquals("testSong", newValueCaptor.getValue().getTitle());
		assertEquals("newArtist", newValueCaptor.getValue().getArtist());
		assertEquals("publisher", newValueCaptor.getValue().getPublisher());
		assertEquals(1, newValueCaptor.getValue().getId());
	}

	@Test
	public void testPublisherChangeNotification() {
		Song song = new Song(1);
		song.setTitle("testSong");
		song.setArtist("artist");
		song.setPublisher("publisher");

		ISongListener listener = Mockito.mock(ISongListener.class);
		song.addListener(listener);

		song.setPublisher("newPublisher");

		ArgumentCaptor<ISong> oldValueCaptor = ArgumentCaptor.forClass(ISong.class);
		ArgumentCaptor<ISong> newValueCaptor = ArgumentCaptor.forClass(ISong.class);

		Mockito.verify(listener, Mockito.times(1)).songChanged(oldValueCaptor.capture(), newValueCaptor.capture());

		assertEquals("testSong", oldValueCaptor.getValue().getTitle());
		assertEquals("artist", oldValueCaptor.getValue().getArtist());
		assertEquals("publisher", oldValueCaptor.getValue().getPublisher());
		assertEquals(1, oldValueCaptor.getValue().getId());

		assertEquals("testSong", newValueCaptor.getValue().getTitle());
		assertEquals("artist", newValueCaptor.getValue().getArtist());
		assertEquals("newPublisher", newValueCaptor.getValue().getPublisher());
		assertEquals(1, newValueCaptor.getValue().getId());
	}

	@Test
	public void testTagsChangeNotification() {
		List<String> originalTags = Arrays.asList("tag1", "tag2");
		Song song = new Song(1);
		song.setTitle("testSong");
		song.setArtist("artist");
		song.setPublisher("publisher");
		song.setTags(originalTags);

		ISongListener listener = Mockito.mock(ISongListener.class);
		song.addListener(listener);

		List<String> newTags = Arrays.asList("tag1,", "tag2", "tag3");
		song.setTags(newTags);

		ArgumentCaptor<ISong> oldValueCaptor = ArgumentCaptor.forClass(ISong.class);
		ArgumentCaptor<ISong> newValueCaptor = ArgumentCaptor.forClass(ISong.class);

		Mockito.verify(listener, Mockito.times(1)).songChanged(oldValueCaptor.capture(), newValueCaptor.capture());

		assertEquals("testSong", oldValueCaptor.getValue().getTitle());
		assertEquals("artist", oldValueCaptor.getValue().getArtist());
		assertEquals("publisher", oldValueCaptor.getValue().getPublisher());
		assertEquals(originalTags, oldValueCaptor.getValue().getTags());
		assertEquals(1, oldValueCaptor.getValue().getId());

		assertEquals("testSong", newValueCaptor.getValue().getTitle());
		assertEquals("artist", newValueCaptor.getValue().getArtist());
		assertEquals("publisher", newValueCaptor.getValue().getPublisher());
		assertEquals(newTags, newValueCaptor.getValue().getTags());
		assertEquals(1, newValueCaptor.getValue().getId());
	}

	@Test
	public void testNoNotificationOnNonChange() {
		List<String> originalTags = Arrays.asList("tag1", "tag2");
		Song song = new Song(1);
		song.setTitle("testSong");
		song.setArtist("artist");
		song.setPublisher("publisher");
		song.setTags(originalTags);

		ISongListener listener = Mockito.mock(ISongListener.class);
		song.addListener(listener);

		// we set the exact same values again after adding the listener...
		song.setTitle("testSong");
		song.setArtist("artist");
		song.setPublisher("publisher");
		song.setTags(originalTags);

		// ... and expect the listener not to be called, since no value changed
		Mockito.verify(listener, Mockito.never()).songChanged(ArgumentMatchers.any(), ArgumentMatchers.any());
	}
}
