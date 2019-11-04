package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongManager implements ISongManager {

	private final Set<ISongListener> listeners = new HashSet<>();
	private final SongStorage storage;

	public SongManager(final SongStorage storage) {
		this.storage = storage;
	}

	@Override
	public ISong createSong(final String title) {
		Song song = new Song(IdManager.newSongId());
		song.setTitle(title);
		this.storage.addSong(song);
		for (ISongListener l : this.listeners) {
			l.songAdded(song);
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
		Song song = this.storage.getSong(id);
		this.storage.removeSong(song);
		for (ISongListener l : this.listeners) {
			l.songRemoved(song);
		}
	}

	public void addSongListener(final ISongListener listener) {
		this.listeners.add(listener);
	}

	public void removeSongListener(final ISongListener listener) {
		this.listeners.remove(listener);
	}
}
