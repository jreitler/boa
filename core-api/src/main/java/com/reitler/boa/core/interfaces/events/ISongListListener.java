package com.reitler.boa.core.interfaces.events;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;

public interface ISongListListener {

	void songListCreated(ISongList list);

	void songListDeleted(ISongList list);

	void assignmentAdded(ISongList list, ISongAssignment assignment);

	void assignmentRemoved(ISongList list, ISongAssignment assignment);

}
