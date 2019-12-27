package com.reitler.boa.core.interfaces;

import java.util.List;

import com.reitler.boa.core.interfaces.events.ISongListener;

public interface ISong extends IListenerSupport<ISongListener> {

	public int getId();

	public String getTitle();

	public void setTitle(String title);

	String getPublisher();

	void setPublisher(final String publisher);

	String getArtist();

	void setArtist(final String artist);

	void setTags(List<String> tags);

	List<String> getTags();

}
