package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;

public class SongList implements ISongList {

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
		this.assignments.add(assignment);
	}

	@Override
	public String getName() {
		return this.name;
	}

	private List<ISongAssignment> getSorted(final Function<ISongAssignment, String> f) {
		List<ISongAssignment> result = new ArrayList<>(this.assignments);

		Collections.sort(result, (a, b) -> f.apply(a).compareTo(f.apply(b)));

		return result;
	}

}
