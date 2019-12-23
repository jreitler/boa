package com.reitler.boa.core.interfaces;

import java.util.List;

import com.reitler.boa.core.interfaces.events.ISongListListener;
import com.reitler.boa.core.interfaces.factory.ISongListFactory;

public interface ISongListManager extends ISongListFactory, IListenerSupport<ISongListListener> {

	void deleteSongList(ISongList list);

	void deleteSongList(String name);

	List<ISongList> getAllSongLists();

	void assign(final ISong song, final ISongList list, final String page);

	void unassign(final ISong song, final ISongList list);
}
