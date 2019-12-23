package com.reitler.boa.core.interfaces;

public interface IListenerSupport<E> {

	void addListener(E listener);

	void removeListener(E listener);

}