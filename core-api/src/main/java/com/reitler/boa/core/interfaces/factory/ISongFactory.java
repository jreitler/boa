package com.reitler.boa.core.interfaces.factory;

import com.reitler.boa.core.interfaces.ISong;

public interface ISongFactory {

	ISong createSong(String title, final String artist, final String publisher);

}
