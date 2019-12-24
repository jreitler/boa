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
	public ISong createSong(final String title, final String artist, final String publisher) {
		return createSong(IdManager.newSongId(), title, artist, publisher);
	}

	public Song createSong(final int id, final String title, final String artist, final String publisher) {
		IdManager.addSongId(id);
		Song song = new Song(id);
		song.setTitle(title);
		song.setArtist(artist);
		song.setPublisher(publisher);
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
		IdManager.removeSongId(id);
		for (ISongListener l : getListeners()) {
			l.songRemoved(song);
			song.removeListener(l);
		}
	}

}
