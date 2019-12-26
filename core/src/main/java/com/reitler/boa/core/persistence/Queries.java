package com.reitler.boa.core.persistence;

import com.reitler.boa.core.interfaces.ISong;

public class Queries {

	public static final String SONG_ID_ATTRIBUTE = "song_id";
	public static final String SONG_TITLE_ATTIRBUTE = "title";
	public static final String SONG_ARTIST_ATTRIBUTE = "artist";
	public static final String SONG_PUBLISHER_ATTRIBUTE = "publisher";

	public static final String SONGLIST_ID_ATTRIBUTE = "songlist_id";
	public static final String SONGLIST_NAME_ATTRIBUTE = "songlist_name";

	public static final String ASSIGNMENT_ID_ATTRIBUTE = "assignment_id";
	public static final String ASSIGNMENT_PAGE_ATTRIBUTE = "page";
	public static final String ASSIGNMENT_SONG_ATTRIBUTE = "song";
	public static final String ASSIGNMENT_SONGLIST_ATTRIBUTE = "songlist";

	private static final String TABLE_NAME_SONGS = "songs";
	private static final String TABLE_NAME_ASSIGNMENTS = "assignments";
	private static final String TABLE_NAME_SONGLISTS = "songlists";

	private static final String INSERT_SONG_QUERY = String.format(
			"INSERT INTO %s (%s,%s,%s,%s) VALUES ('%%d','%%s','%%s','%%s');", Queries.TABLE_NAME_SONGS,
			Queries.SONG_ID_ATTRIBUTE, Queries.SONG_TITLE_ATTIRBUTE, Queries.SONG_ARTIST_ATTRIBUTE,
			Queries.SONG_PUBLISHER_ATTRIBUTE);
	private static final String INSERT_SONGLIST_QUERY = String.format("INSERT INTO %s (%s,%s) VALUES ('%%d','%%s');",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_ID_ATTRIBUTE, Queries.SONGLIST_NAME_ATTRIBUTE);
	private static final String INSERT_ASSIGNMENT_QUERY = String.format(
			"INSERT INTO %s (%s,%s, %s, %s) VALUES ('%%d','%%s','%%d','%%d');", Queries.TABLE_NAME_ASSIGNMENTS,
			Queries.ASSIGNMENT_ID_ATTRIBUTE, Queries.ASSIGNMENT_PAGE_ATTRIBUTE, Queries.ASSIGNMENT_SONG_ATTRIBUTE,
			Queries.ASSIGNMENT_SONGLIST_ATTRIBUTE);

	private static final String SELECT_ALL = "SELECT * FROM %s;";

	public static final String SELECT_SONGS_QUERY = String.format(Queries.SELECT_ALL, Queries.TABLE_NAME_SONGS);
	public static final String SELECT_ASSIGNMENTS_QUERY = String.format(Queries.SELECT_ALL,
			Queries.TABLE_NAME_ASSIGNMENTS);
	public static final String SELECT_SONGLISTS_QUERY = String.format(Queries.SELECT_ALL, Queries.TABLE_NAME_SONGLISTS);

	private static final String SELECT_SONG_BY_TITLE_QUERY = String.format("SELECT * from %s where %s=='%%s';",
			Queries.TABLE_NAME_SONGS, Queries.SONG_TITLE_ATTIRBUTE);
	private static final String SELECT_SONGLIST_BY_NAME_QUERY = String.format("SELECT * from %s where %s=='%%s';",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_NAME_ATTRIBUTE);
	private static final String SELECT_ASSIGNMENT_BY_SONG_AND_SONGLIST_QUERY = String.format(
			"SELECT * from %s WHERE %s=='%%d' AND %s=='%%d';", Queries.TABLE_NAME_ASSIGNMENTS,
			Queries.ASSIGNMENT_SONG_ATTRIBUTE, Queries.ASSIGNMENT_SONGLIST_ATTRIBUTE);

	private static final String DELETE_SONG_BY_ID_QUERY = String.format("DELETE from %s where %s=='%%d';",
			Queries.TABLE_NAME_SONGS, Queries.SONG_ID_ATTRIBUTE);
	private static final String DELETE_ASSIGNMENTS_BY_SONG_ID_QUERY = String.format("DELETE from %s where %s=='%%d';",
			Queries.TABLE_NAME_ASSIGNMENTS, Queries.ASSIGNMENT_SONG_ATTRIBUTE);

	private static final String DELETE_LIST_BY_ID_QUERY = String.format("DELETE from %s where %s=='%%d';",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_ID_ATTRIBUTE);
	private static final String DELETE_ASSIGNMENTS_BY_LIST_ID_QUERY = String.format("DELETE from %s where %s=='%%d';",
			Queries.TABLE_NAME_ASSIGNMENTS, Queries.ASSIGNMENT_SONGLIST_ATTRIBUTE);
	private static final String DELETE_ASSIGNMENT_BY_ID_QUERY = String.format("DELETE from %s where %s=='%%d';",
			Queries.TABLE_NAME_ASSIGNMENTS, Queries.ASSIGNMENT_ID_ATTRIBUTE);

	private static final String UPDATE_SONG_QUERY = String.format(
			"UPDATE %s set %s='%%s',%s='%%s',%s='%%s' where %s='%%d';", Queries.TABLE_NAME_SONGS,
			Queries.SONG_TITLE_ATTIRBUTE, Queries.SONG_ARTIST_ATTRIBUTE, Queries.SONG_PUBLISHER_ATTRIBUTE,
			Queries.SONG_ID_ATTRIBUTE);

	private static final String UPDATE_SONGLIST_QUERY = String.format("UPDATE %s set %s='%%s' where %s='%%d';",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_NAME_ATTRIBUTE, Queries.SONGLIST_ID_ATTRIBUTE);

	public static String prepareUpdateSongQuery(final ISong song) {
		return String.format(Queries.UPDATE_SONG_QUERY, song.getTitle(), song.getArtist(), song.getPublisher(),
				song.getId());
	}

	public static String prepareInsertSongQuery(final int songId, final String title, final String artist,
			final String publisher) {
		return String.format(Queries.INSERT_SONG_QUERY, songId, nullSafe(title), nullSafe(artist), nullSafe(publisher));
	}

	public static String prepareDeleteSongQuery(final int songId) {
		return String.format(Queries.DELETE_SONG_BY_ID_QUERY, songId);
	}

	public static String prepareDeleteAssignmentBySongsQuery(final int songId) {
		return String.format(Queries.DELETE_ASSIGNMENTS_BY_SONG_ID_QUERY, songId);
	}

	public static String prepareDeleteAssignmentsByListQuery(final int listId) {
		return String.format(Queries.DELETE_ASSIGNMENTS_BY_LIST_ID_QUERY, listId);
	}

	public static String prepareDeleteAssignmentByIdQuery(final int assignmentId) {
		return String.format(Queries.DELETE_ASSIGNMENT_BY_ID_QUERY, assignmentId);
	}

	public static String prepareDeleteSongListQuery(final int listId) {
		return String.format(Queries.DELETE_LIST_BY_ID_QUERY, listId);
	}

	public static String prepareUpdateSongListQuery(final int listId, final String listName) {
		return String.format(Queries.UPDATE_SONGLIST_QUERY, listName, listId);
	}

	public static String prepareInsertSonglistQuery(final int listId, final String listName) {
		return String.format(Queries.INSERT_SONGLIST_QUERY, listId, listName);
	}

	public static String prepareInsertAssignmentQuery(final int assignmentId, final String page, final int songId,
			final int songlistId) {
		return String.format(Queries.INSERT_ASSIGNMENT_QUERY, assignmentId, page, songId, songlistId);
	}

	public static String prepareFetchSongQuery(final String title) {
		return String.format(Queries.SELECT_SONG_BY_TITLE_QUERY, title);
	}

	public static String prepareFetchSongListQuery(final String name) {
		return String.format(Queries.SELECT_SONGLIST_BY_NAME_QUERY, name);
	}

	public static String prepareFetchAssignmentQuery(final int songId, final int listId) {
		return String.format(Queries.SELECT_ASSIGNMENT_BY_SONG_AND_SONGLIST_QUERY, songId, listId);
	}

	private static String nullSafe(final String value) {
		return value == null ? "" : value;
	}

}
