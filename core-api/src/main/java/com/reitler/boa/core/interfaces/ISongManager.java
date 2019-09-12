package com.reitler.boa.core.interfaces;

import java.util.List;

public interface ISongManager extends ISongFactory {

	void deleteSong(final int id);

	void deleteSong(final ISong song);

	List<ISong> getAllSongs();

}
