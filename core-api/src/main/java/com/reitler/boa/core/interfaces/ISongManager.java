package com.reitler.boa.core.interfaces;

import java.util.List;

import com.reitler.boa.core.interfaces.events.ISongListener;
import com.reitler.boa.core.interfaces.factory.ISongFactory;

public interface ISongManager extends ISongFactory {

	void deleteSong(final int id);

	void deleteSong(final ISong song);

	List<ISong> getAllSongs();

	void removeSongListener(final ISongListener listener);

	void addSongListener(final ISongListener listener);

}
