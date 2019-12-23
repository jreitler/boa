package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongList extends ListenerSupport<ISongListListener> implements ISongList {

	private final String name;
	private final int id;

	private final List<ISongAssignment> assignments = new ArrayList<>();

	public SongList(final int id, final String name) {
		this.name = name;
		this.id = id;
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
			for (ISongListListener l : getListeners()) {
				l.assignmentAdded(this, assignment);
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void remove(final ISongAssignment assignment) {
		if (this.assignments.remove(assignment)) {
			for (ISongListListener l : getListeners()) {
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
	public void removeIf(final Predicate<ISongAssignment> predicate) {
		List<ISongAssignment> list = this.assignments.stream().filter(predicate).collect(Collectors.toList());
		for (ISongAssignment as : list) {
			remove(as);
		}
	}

	@Override
	public int getId() {
		return this.id;
	}

}
