package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class Song extends ListenerSupport<ISongListener> implements ISong {

	private final int id;
	private String title;
	private String artist;
	private String publisher;
	private final List<String> tags = new ArrayList<>();

	public Song(final int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(final String title) {
		ISong oldValue = copy();
		this.title = title;
		notifyChanged(oldValue);
	}

	@Override
	public void setArtist(final String artist) {
		ISong oldValue = copy();
		this.artist = artist;
		notifyChanged(oldValue);
	}

	@Override
	public String getArtist() {
		return this.artist;
	}

	@Override
	public void setPublisher(final String publisher) {
		ISong oldValue = copy();
		this.publisher = publisher;
		notifyChanged(oldValue);
	}

	@Override
	public String getPublisher() {
		return this.publisher;
	}

	@Override
	public void addTag(final String tag) {
		ISong oldValue = copy();
		this.tags.add(tag);
		notifyChanged(oldValue);
	}

	@Override
	public void removeTag(final String tag) {
		ISong oldValue = copy();
		this.tags.remove(tag);
		notifyChanged(oldValue);
	}

	@Override
	public List<String> getTags() {
		return Collections.unmodifiableList(this.tags);
	}

	private ISong copy() {
		Song copy = new Song(this.id);
		copy.title = this.title;
		copy.artist = this.artist;
		copy.publisher = this.publisher;
		copy.tags.addAll(this.tags);
		return copy;
	}

	private void notifyChanged(final ISong oldValue) {
		for (ISongListener l : getListeners()) {
			l.songChanged(oldValue, this);
		}
	}

}
