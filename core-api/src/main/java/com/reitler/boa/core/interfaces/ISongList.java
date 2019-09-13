package com.reitler.boa.core.interfaces;

import java.util.List;

import com.reitler.boa.core.interfaces.events.ISongListListener;

public interface ISongList {

	List<ISongAssignment> getByPage();

	List<ISongAssignment> getByTitle();

	void add(ISongAssignment assignment);

	String getName();

	void addSongListListener(ISongListListener listener);

	void removeSongListListener(ISongListListener listener);
}
