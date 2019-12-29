package com.reitler.boa.core.persistence;

public class Queries {

	public static final String SONG_ID_ATTRIBUTE = "song_id";
	public static final String SONG_TITLE_ATTIRBUTE = "title";
	public static final String SONG_ARTIST_ATTRIBUTE = "artist";
	public static final String SONG_PUBLISHER_ATTRIBUTE = "publisher";
	public static final String SONG_TAGS_ATTRIBUTE = "tags";

	public static final String SONGLIST_ID_ATTRIBUTE = "songlist_id";
	public static final String SONGLIST_NAME_ATTRIBUTE = "songlist_name";

	public static final String ASSIGNMENT_ID_ATTRIBUTE = "assignment_id";
	public static final String ASSIGNMENT_PAGE_ATTRIBUTE = "page";
	public static final String ASSIGNMENT_SONG_ATTRIBUTE = "song";
	public static final String ASSIGNMENT_SONGLIST_ATTRIBUTE = "songlist";

	private static final String TABLE_NAME_SONGS = "songs";
	private static final String TABLE_NAME_ASSIGNMENTS = "assignments";
	private static final String TABLE_NAME_SONGLISTS = "songlists";

	public static final String INSERT_SONG_QUERY = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?);",
			Queries.TABLE_NAME_SONGS, Queries.SONG_ID_ATTRIBUTE, Queries.SONG_TITLE_ATTIRBUTE,
			Queries.SONG_ARTIST_ATTRIBUTE, Queries.SONG_PUBLISHER_ATTRIBUTE, Queries.SONG_TAGS_ATTRIBUTE);
	public static final String INSERT_SONGLIST_QUERY = String.format("INSERT INTO %s (%s,%s) VALUES (?,?);",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_ID_ATTRIBUTE, Queries.SONGLIST_NAME_ATTRIBUTE);
	public static final String INSERT_ASSIGNMENT_QUERY = String.format(
			"INSERT INTO %s (%s,%s, %s, %s) VALUES (?,?,?,?);", Queries.TABLE_NAME_ASSIGNMENTS,
			Queries.ASSIGNMENT_ID_ATTRIBUTE, Queries.ASSIGNMENT_PAGE_ATTRIBUTE, Queries.ASSIGNMENT_SONG_ATTRIBUTE,
			Queries.ASSIGNMENT_SONGLIST_ATTRIBUTE);

	private static final String SELECT_ALL = "SELECT * FROM %s;";

	public static final String SELECT_SONGS_QUERY = String.format(Queries.SELECT_ALL, Queries.TABLE_NAME_SONGS);
	public static final String SELECT_ASSIGNMENTS_QUERY = String.format(Queries.SELECT_ALL,
			Queries.TABLE_NAME_ASSIGNMENTS);
	public static final String SELECT_SONGLISTS_QUERY = String.format(Queries.SELECT_ALL, Queries.TABLE_NAME_SONGLISTS);

	public static final String DELETE_SONG_BY_ID_QUERY = String.format("DELETE from %s where %s==?;",
			Queries.TABLE_NAME_SONGS, Queries.SONG_ID_ATTRIBUTE);
	public static final String DELETE_ASSIGNMENTS_BY_SONG_ID_QUERY = String.format("DELETE from %s where %s==?;",
			Queries.TABLE_NAME_ASSIGNMENTS, Queries.ASSIGNMENT_SONG_ATTRIBUTE);

	public static final String DELETE_LIST_BY_ID_QUERY = String.format("DELETE from %s where %s==?;",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_ID_ATTRIBUTE);
	public static final String DELETE_ASSIGNMENTS_BY_LIST_ID_QUERY = String.format("DELETE from %s where %s==?;",
			Queries.TABLE_NAME_ASSIGNMENTS, Queries.ASSIGNMENT_SONGLIST_ATTRIBUTE);
	public static final String DELETE_ASSIGNMENT_BY_ID_QUERY = String.format("DELETE from %s where %s==?;",
			Queries.TABLE_NAME_ASSIGNMENTS, Queries.ASSIGNMENT_ID_ATTRIBUTE);

	public static final String UPDATE_SONG_QUERY = String.format("UPDATE %s set %s=?,%s=?,%s=?,%s=? where %s=?;",
			Queries.TABLE_NAME_SONGS, Queries.SONG_TITLE_ATTIRBUTE, Queries.SONG_ARTIST_ATTRIBUTE,
			Queries.SONG_PUBLISHER_ATTRIBUTE, Queries.SONG_TAGS_ATTRIBUTE, Queries.SONG_ID_ATTRIBUTE);

	public static final String UPDATE_SONGLIST_QUERY = String.format("UPDATE %s set %s=? where %s=?;",
			Queries.TABLE_NAME_SONGLISTS, Queries.SONGLIST_NAME_ATTRIBUTE, Queries.SONGLIST_ID_ATTRIBUTE);

}
