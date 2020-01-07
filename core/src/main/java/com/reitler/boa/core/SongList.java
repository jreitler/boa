package com.reitler.boa.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.events.ISongListListener;

public class SongList extends ListenerSupport<ISongListListener> implements ISongList {

	private String name;
	private final int id;

	private final List<ISongAssignment> assignments = new ArrayList<>();

	public SongList(final int id, final String name) {
		this.name = name;
		this.id = id;
	}

	@Override
	public List<ISongAssignment> getByPage() {
		return getSortedByPage();
	}

	@Override
	public List<ISongAssignment> getByTitle() {
		return getSortedByTitle();
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
		return this.name != null ? this.name : "";
	}

	@Override
	public void remove(final ISongAssignment assignment) {
		if (this.assignments.remove(assignment)) {
			for (ISongListListener l : getListeners()) {
				l.assignmentRemoved(this, assignment);
			}
		}
	}

	private List<ISongAssignment> getSortedByTitle() {
		List<ISongAssignment> result = new ArrayList<>(this.assignments);
		Collections.sort(result, (a, b) -> a.getSong().getTitle().compareToIgnoreCase(b.getSong().getTitle()));
		return result;
	}

	private List<ISongAssignment> getSortedByPage() {
		List<ISongAssignment> result = new ArrayList<>(this.assignments);
		Collections.sort(result, (a, b) -> new PageComparator().compare(a.getPage(), b.getPage()));
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

	@Override
	public void setName(final String name) {
		String oldName = this.name;
		this.name = name;
		for (ISongListListener l : getListeners()) {
			l.songListNameChanged(this, oldName, name);
		}
	}

}
