package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.factory.ISongAssignmentFactory;

public class SongAssignmentFactory implements ISongAssignmentFactory {

	@Override
	public ISongAssignment createAssignment(final ISong song, final String page) {
		SongAssignment sa = new SongAssignment(song);
		sa.setPage(page);
		return sa;
	}

}
