package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.events.ISongAssignmentListener;
import com.reitler.boa.core.interfaces.events.ISongListListener;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongListManager implements ISongListManager, ISongListener {

	private final Set<ISongListListener> listeners = new HashSet<>();
	private final SongStorage storage;
	private ISongAssignmentListener listener;

	public void setSongAssignmentListener(final ISongAssignmentListener handler) {
		this.listener = handler;
	}

	public SongListManager(final SongStorage storage) {
		this.storage = storage;
	}

	@Override
	public ISongList createSongList(final String name) {
		SongList list = new SongList(IdManager.newListId(), name);
		list.addSongAssignmentListener(this.listener);
		this.storage.addSongList(list);
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
		List<SongList> songLists = this.storage.getSongLists();
		Iterator<SongList> i = songLists.iterator();

		while (i.hasNext()) {
			ISongList list = i.next();
			if (list.getName().equals(name)) {
				for (ISongListListener l : this.listeners) {
					l.songListRemoved(list);
				}
				this.storage.removeSongList(list);
			}
		}
	}

	@Override
	public List<ISongList> getAllSongLists() {
		return new ArrayList<>(this.storage.getSongLists());
	}

	@Override
	public void addSongListListener(final ISongListListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeSongListListener(final ISongListListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void assign(final ISong song, final ISongList list, final String page) {
		SongAssignment assignment = new SongAssignment(IdManager.newAssignmentId(), song);
		assignment.setPage(page);
		list.add(assignment);
	}

	@Override
	public void unassign(final ISong song, final ISongList list) {
		if (list instanceof SongList) {
			SongList songList = (SongList) list;
			songRemoved(songList, song);
		}
	}

	@Override
	public void songAdded(final ISong addedSong) {
		// nothing to do

	}

	@Override
	public void songRemoved(final ISong removedSong) {
		this.storage.getSongLists().forEach(l -> songRemoved(l, removedSong));
	}

	private void songRemoved(final SongList l, final ISong removedSong) {
		l.removeIf(a -> a.getSong().equals(removedSong));
	}

}
