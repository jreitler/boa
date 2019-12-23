package com.reitler.boa.core.interfaces.persistence;

import java.io.Closeable;
import java.io.File;

import com.reitler.boa.core.interfaces.events.ISongAssignmentListener;
import com.reitler.boa.core.interfaces.events.ISongListListener;
import com.reitler.boa.core.interfaces.events.ISongListener;

public interface IPersistenceHandler extends Closeable, ISongListListener, ISongAssignmentListener, ISongListener {

	boolean open(File file);

}