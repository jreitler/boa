package com.reitler.boa.core.interfaces;

import java.util.List;
import java.util.function.Predicate;

import com.reitler.boa.core.interfaces.events.ISongListListener;

public interface ISongList extends IListenerSupport<ISongListListener> {

	List<ISongAssignment> getByPage();

	List<ISongAssignment> getByTitle();

	void add(ISongAssignment assignment);

	void remove(ISongAssignment assignment);

	void removeIf(Predicate<ISongAssignment> predicate);

	String getName();

	int getId();

	void setName(final String name);
}
