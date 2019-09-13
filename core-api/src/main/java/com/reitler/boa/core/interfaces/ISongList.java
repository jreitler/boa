package com.reitler.boa.core.interfaces;

import java.util.List;

import com.reitler.boa.core.interfaces.events.ISongAssignmentListener;

public interface ISongList {

	List<ISongAssignment> getByPage();

	List<ISongAssignment> getByTitle();

	void add(ISongAssignment assignment);

	String getName();

	void addSongAssignmentListener(ISongAssignmentListener listener);

	void removeSongAssignmentListener(ISongAssignmentListener listener);
}
