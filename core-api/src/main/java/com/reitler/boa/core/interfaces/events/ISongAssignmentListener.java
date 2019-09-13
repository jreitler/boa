package com.reitler.boa.core.interfaces.events;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;

public interface ISongAssignmentListener {

	void assignmentAdded(ISongList source, ISongAssignment addedSong);

	void assignmentRemoved(ISongList source, ISongAssignment removedSong);

}
