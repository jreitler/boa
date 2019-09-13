package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongList implements ISongList {

	private final Set<ISongListListener> listeners = new HashSet<>();
	private final String name;

	private final List<ISongAssignment> assignments = new ArrayList<>();

	public SongList(final String name) {
		this.name = name;
	}

	@Override
	public List<ISongAssignment> getByPage() {
		return getSorted(a -> a.getPage());
	}

	@Override
	public List<ISongAssignment> getByTitle() {
		return getSorted(a -> a.getSong().getTitle());
	}

	@Override
	public void add(final ISongAssignment assignment) {
		if (this.assignments.add(assignment)) {
			for (ISongListListener l : this.listeners) {
				l.assignmentAdded(this, assignment);
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void remove(final ISongAssignment assignment) {
		if (this.assignments.remove(assignment)) {
			for (ISongListListener l : this.listeners) {
				l.assignmentRemoved(this, assignment);
			}
		}
	}

	private List<ISongAssignment> getSorted(final Function<ISongAssignment, String> f) {
		List<ISongAssignment> result = new ArrayList<>(this.assignments);

		Collections.sort(result, (a, b) -> f.apply(a).compareTo(f.apply(b)));

		return result;
	}

	@Override
	public void addSongListListener(final ISongListListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}
	}

	@Override
	public void removeSongListListener(final ISongListListener listener) {
		if (listener != null) {
			this.listeners.remove(listener);
		}

	}

}
