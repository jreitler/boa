package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongListManager implements ISongListManager {

	private final SortedMap<String, ISongList> lists = new TreeMap<>();
	private final Set<ISongListListener> listeners = new HashSet<>();

	@Override
	public ISongList createSongList(final String name) {
		SongList list = new SongList(name);
		this.lists.put(name, list);
		for (ISongListListener l : this.listeners) {
			l.songListAdded(list);
		}
		return list;
	}

	@Override
	public void deleteSongList(final ISongList list) {
		deleteSongList(list.getName());
	}

	@Override
	public void deleteSongList(final String name) {
		ISongList list = this.lists.remove(name);
		if (list != null) {
			for (ISongListListener l : this.listeners) {
				l.songListRemoved(list);
			}
		}
	}

	@Override
	public List<ISongList> getAllSongLists() {
		return new ArrayList<>(this.lists.values());
	}

	@Override
	public void addSongListListener(final ISongListListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeSongListListener(final ISongListListener listener) {
		this.listeners.remove(listener);
	}

}
