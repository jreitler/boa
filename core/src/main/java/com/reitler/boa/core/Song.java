package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class Song extends ListenerSupport<ISongListener> implements ISong {

	private final int id;
	private String title;
	private String artist;
	private String publisher;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.id;
		result = (prime * result) + ((this.title == null) ? 0 : this.title.hashCode());
		result = (prime * result) + ((this.artist == null) ? 0 : this.artist.hashCode());
		result = (prime * result) + ((this.publisher == null) ? 0 : this.publisher.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Song other = (Song) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!this.title.equals(other.title)) {
			return false;
		}
		if (this.artist == null) {
			if (other.artist != null) {
				return false;
			}
		} else if (!this.artist.equals(other.artist)) {
			return false;
		}
		if (this.publisher == null) {
			if (other.publisher != null) {
				return false;
			}
		} else if (!this.publisher.equals(other.publisher)) {
			return false;
		}
		return true;
	}

	private ISong copy() {
		Song copy = new Song(this.id);
		copy.title = this.title;
		copy.artist = this.artist;
		copy.publisher = this.publisher;
		return copy;
	}

	private void notifyChanged(final ISong oldValue) {
		for (ISongListener l : getListeners()) {
			l.songChanged(oldValue, this);
		}
	}

}
