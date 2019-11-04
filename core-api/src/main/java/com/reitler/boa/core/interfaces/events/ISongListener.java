package com.reitler.boa.core.interfaces.events;

import com.reitler.boa.core.interfaces.ISong;

public interface ISongListener {

	public void songAdded(final ISong addedSong);

	public void songRemoved(final ISong removedSong);
}
