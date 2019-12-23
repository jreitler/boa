package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.List;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongManager extends ListenerSupport<ISongListener> implements ISongManager {

	private final SongStorage storage;

	public SongManager(final SongStorage storage) {
		this.storage = storage;
	}

	@Override
	public ISong createSong(final String title) {
		return createSong(IdManager.newSongId(), title);
	}

	public Song createSong(final int id, final String title) {
		IdManager.addSongId(id);
		Song song = new Song(id);
		song.setTitle(title);
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
		Song song = this.storage.getSong(id);
		this.storage.removeSong(song);
		IdManager.removeSongId(id);
		for (ISongListener l : getListeners()) {
			l.songRemoved(song);
			song.removeListener(l);
		}
	}

}
