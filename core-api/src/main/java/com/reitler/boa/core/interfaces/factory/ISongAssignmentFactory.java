package com.reitler.boa.core.interfaces.factory;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;

public interface ISongAssignmentFactory {

	ISongAssignment createAssignment(ISong song, String page);

}
