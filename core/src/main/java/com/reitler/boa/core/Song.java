package com.reitler.boa.core;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.events.ISongListener;

public class Song extends ListenerSupport<ISongListener> implements ISong {

	private final int id;
	private String title;

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
		Song oldValue = copy();
		this.title = title;
		for (ISongListener l : getListeners()) {
			l.songChanged(oldValue, this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.id;
		result = (prime * result) + ((this.title == null) ? 0 : this.title.hashCode());
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
		return true;
	}

	private Song copy() {
		Song copy = new Song(this.id);
		copy.title = this.title;
		return copy;
	}

}
