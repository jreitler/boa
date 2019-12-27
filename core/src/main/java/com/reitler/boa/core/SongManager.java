package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongManager extends ListenerSupport<ISongListener> implements ISongManager {

	private final Set<Integer> USED_SONG_IDS = new HashSet<>();
	private final SongStorage storage;

	public SongManager(final SongStorage storage) {
		this.storage = storage;
	}

	@Override
	public ISong createSong(final String title, final String artist, final String publisher) {
		return createSong(newSongId(), title, artist, publisher, Collections.emptyList());
	}

	public Song createSong(final int id, final String title, final String artist, final String publisher,
			final List<String> tags) {
		this.USED_SONG_IDS.add(id);
		Song song = new Song(id);
		song.setTitle(title);
		song.setArtist(artist);
		song.setPublisher(publisher);
		tags.forEach(song::addTag);
		this.storage.addSong(song);
		for (ISongListener l : getListeners()) {
			l.songAdded(song);
			song.addListener(l);
		}
		return song;
	}

	@Override
	public List<ISong> getAllSongs() {
		return new ArrayList<>(this.storage.getSongs());
	}

	@Override
	public void deleteSong(final ISong song) {
		if (song != null) {
			deleteSong(song.getId());
		}
	}

	@Override
	public void deleteSong(final int id) {
		ISong song = this.storage.getSong(id);
		this.storage.removeSong(song);
		this.USED_SONG_IDS.remove(id);
		for (ISongListener l : getListeners()) {
			l.songRemoved(song);
			song.removeListener(l);
		}
	}

	private int newSongId() {
		int id = 1;
		while (this.USED_SONG_IDS.contains(id)) {
			id++;
		}
		this.USED_SONG_IDS.add(id);
		return id;
	}

}
