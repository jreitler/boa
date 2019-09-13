package com.reitler.boa.core.interfaces.events;

import com.reitler.boa.core.interfaces.ISongList;

public interface ISongListListener {

	void songListAdded(ISongList list);

	void songListRemoved(ISongList list);

}
