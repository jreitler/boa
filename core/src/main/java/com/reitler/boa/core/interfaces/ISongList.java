package com.reitler.boa.core.interfaces;

import java.util.List;

public interface ISongList {

	List<ISongAssignment> getByPage();

	List<ISongAssignment> getByTitle();

	void add(ISongAssignment assignment);

	String getName();
}
