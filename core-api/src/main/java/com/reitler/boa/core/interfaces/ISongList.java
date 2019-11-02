package com.reitler.boa.core.interfaces;

import java.util.List;
import java.util.function.Predicate;

import com.reitler.boa.core.interfaces.events.ISongAssignmentListener;

public interface ISongList {

	List<ISongAssignment> getByPage();

	List<ISongAssignment> getByTitle();

	void add(ISongAssignment assignment);

	void remove(ISongAssignment assignment);

	void removeIf(Predicate<ISongAssignment> predicate);

	String getName();

	void addSongAssignmentListener(ISongAssignmentListener listener);

	void removeSongAssignmentListener(ISongAssignmentListener listener);
}
