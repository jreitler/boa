package com.reitler.boa.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongAssignment;

public class SongAssignmentFactoryTest {

	@Test
	public void testCreateAssignment() {
		ISong song = Mockito.mock(ISong.class);
		SongAssignmentFactory factory = new SongAssignmentFactory();

		ISongAssignment assignment = factory.createAssignment(song, "page1");
		assertNotNull(assignment);
		assertEquals("page1", assignment.getPage());
		assertSame(song, assignment.getSong());
	}

}
