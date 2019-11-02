package com.reitler.boa.core;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;

public class SongStorage {

	private final SortedMap<Integer, Song> songs = new TreeMap<>();
	private final SortedMap<Integer, SongList> songLists = new TreeMap<>();

	public List<Song> getSongs() {
		return new LinkedList<>(this.songs.values());
	}

	public void addSong(final Song song) {
		this.songs.put(song.getId(), song);
	}

	public void removeSong(final ISong song) {
		this.songs.remove(song.getId());
	}

	public List<SongList> getSongLists() {
		return new LinkedList<>(this.songLists.values());
	}

	public void addSongList(final SongList list) {
		this.songLists.put(list.getId(), list);
	}

	public void removeSongList(final ISongList list) {
		this.songLists.remove(list.getId());
	}

	public SongList getList(final int id) {
		return this.songLists.get(id);
	}

	public Song getSong(final int id) {
		return this.songs.get(id);
	}
}
