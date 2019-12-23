package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.events.ISongListListener;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class SongListManager extends ListenerSupport<ISongListListener> implements ISongListManager, ISongListener {

	private final SongStorage storage;

	public SongListManager(final SongStorage storage) {
		this.storage = storage;
	}

	@Override
	public ISongList createSongList(final String name) {
		return createSongList(IdManager.newListId(), name);
	}

	public SongList createSongList(final int id, final String name) {
		IdManager.addSongListId(id);
		SongList list = new SongList(id, name);
		this.storage.addSongList(list);
		for (ISongListListener l : getListeners()) {
			l.songListCreated(list);
			list.addListener(l); 
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
				for (ISongListListener l : getListeners()) {
					l.songListDeleted(list);
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
	public void assign(final ISong song, final ISongList list, final String page) {
		assign(IdManager.newAssignmentId(), song, list, page);
	}

	public void assign(final int id, final ISong song, final ISongList list, final String page) {
		IdManager.addAssignmentId(id);
		SongAssignment assignment = new SongAssignment(id, song);
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

	@Override
	public void songChanged(final ISong oldValue, final ISong newValue) {
		// nothing to do
	}

}
