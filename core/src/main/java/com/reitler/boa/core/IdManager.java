package com.reitler.boa.core;

import java.util.HashSet;
import java.util.Set;

public class IdManager {

	private IdManager() {
	}

	private static Set<Integer> USED_SONG_IDS = new HashSet<>();
	private static Set<Integer> USED_LIST_IDS = new HashSet<>();
	private static Set<Integer> USED_ASSIGNMENT_IDS = new HashSet<>();

	public static int newSongId() {
		return newId(IdManager.USED_SONG_IDS);
	}

	public static int newListId() {
		return newId(IdManager.USED_LIST_IDS);
	}

	public static int newAssignmentId() {
		return newId(IdManager.USED_ASSIGNMENT_IDS);
	}

	public static boolean addSongId(final int id) {
		return IdManager.USED_SONG_IDS.add(id);
	}

	public static boolean addSongListId(final int id) {
		return IdManager.USED_LIST_IDS.add(id);
	}

	public static boolean addAssignmentId(final int id) {
		return IdManager.USED_ASSIGNMENT_IDS.add(id);
	}

	public static void removeSongId(final int id) {
		IdManager.USED_SONG_IDS.remove(id);
	}

	public static void removeSongListId(final int id) {
		IdManager.USED_LIST_IDS.remove(id);
	}

	public static void removeAssignmentId(final int id) {
		IdManager.USED_ASSIGNMENT_IDS.remove(id);
	}

	private static int newId(final Set<Integer> usedIds) {
		int id = 1;
		while (usedIds.contains(id)) {
			id++;
		}
		usedIds.add(id);
		return id;
	}

}
