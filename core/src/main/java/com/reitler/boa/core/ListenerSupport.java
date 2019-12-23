package com.reitler.boa.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.reitler.boa.core.interfaces.IListenerSupport;

public class ListenerSupport<E> implements IListenerSupport<E> {

	private final Set<E> listeners = new HashSet<>();

	@Override
	public void addListener(final E listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(final E listener) {
		this.listeners.remove(listener);
	}

	protected Set<E> getListeners() {
		return Collections.unmodifiableSet(this.listeners);
	}
}
