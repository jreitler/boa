package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongManager;

public class SongManager implements ISongManager {

	private final SortedSet<Song> songs = new TreeSet<>((a, b) -> a.getId() - b.getId());
	private int currentId = 1;

	@Override
	public ISong createSong(final String title) {
		Song song = new Song(newId());
		song.setTitle(title);
		this.songs.add(song);
		return song;
	}

	@Override
	public List<ISong> getAllSongs() {
		return new ArrayList<>(this.songs);
	}

	@Override
	public void deleteSong(final ISong song) {
		if (song != null) {
			deleteSong(song.getId());
		}
	}

	@Override
	public void deleteSong(final int id) {
		this.songs.removeIf(s -> s.getId() == id);
	}

	private int newId() {
		int id = this.currentId;
		this.currentId++;
		return id;
	}

}
