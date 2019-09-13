package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.factory.ISongListFactory;

public class SongListFactory implements ISongListFactory {

	@Override
	public ISongList createSongList(final String name) {
		return new SongList(name);
	}

}
