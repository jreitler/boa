package com.reitler.boa.core.api;

import java.util.Locale;
import java.util.ResourceBundle;

public class Constants {

	private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("Constants", Locale.getDefault());

	public static final String getAllSongsTitle() {
		return Constants.RESOURCES.getString("CAPTION_ALL_SONGS");
	}

	public static final String getAllSongListsTitle() {
		return Constants.RESOURCES.getString("CAPTION_ALL_SONGLISTS");
	}

	public static final String getSongTitleCaption() {
		return Constants.RESOURCES.getString("SONG_TITLE");
	}

	public static final String getSongArtistCaption() {
		return Constants.RESOURCES.getString("SONG_ARTIST");
	}

	public static final String getSongPublisherCaption() {
		return Constants.RESOURCES.getString("SONG_PUBLISHER");
	}

	public static final String getChangeSongMessage() {
		return Constants.RESOURCES.getString("CHANGE_SONG_MESSAGE");
	}

	public static final String getCreateSongButton() {
		return Constants.RESOURCES.getString("CREATE_SONG_BUTTON");
	}

	public static final String getSongCreationCaption() {
		return Constants.RESOURCES.getString("CREATE_SONG_CAPTION");
	}

	public static final String getSongCreationMessage() {
		return Constants.RESOURCES.getString("CREATE_SONG_MESSAGE");
	}

	public static final String getDeleteSongButton() {
		return Constants.RESOURCES.getString("DELETE_SONG_BUTTON");
	}

	public static String getGeneratePdfButton() {
		return Constants.RESOURCES.getString("GENERATE_PDF_BUTTON");
	}

	public static String getGeneratePdfCaption() {
		return Constants.RESOURCES.getString("CAPTION_GENERATE_PDF");
	}

	public static String getPdfFileType() {
		return Constants.RESOURCES.getString("PDF_FILES");
	}

	public static String getAddSongButton() {
		return Constants.RESOURCES.getString("ADD_SONG_BUTTON");
	}

	public static String getRemoveSongButton() {
		return Constants.RESOURCES.getString("REMOVE_SONG_BUTTON");
	}

	public static String getAssignmentPageCaption() {
		return Constants.RESOURCES.getString("CAPTION_ASSIGNMENT_PAGE");
	}

	public static String getCreateSongListButton() {
		return Constants.RESOURCES.getString("CREATE_SONGLIST_BUTTON");
	}

	public static String getDeleteSongListButton() {
		return Constants.RESOURCES.getString("DELETE_SONGLIST_BUTTON");
	}

	public static String getSongListNameCaption() {
		return Constants.RESOURCES.getString("CAPTION_SONGLIST_NAME");
	}

	public static String getEditSongButton() {
		return Constants.RESOURCES.getString("EDIT_SONG_BUTTON");
	}

	public static String getSongListCreationCaption() {
		return Constants.RESOURCES.getString("CREATE_SONGLIST_CAPTION");
	}

	public static String getSongListCreationMessage() {
		return Constants.RESOURCES.getString("CREATE_SONGLIST_MESSAGE");
	}

	public static String getButtonOk() {
		return Constants.RESOURCES.getString("BUTTON_OK");
	}

	public static String getButtonCancel() {
		return Constants.RESOURCES.getString("BUTTON_CANCEL");
	}

	public static String getSongTagsCaption() {
		return Constants.RESOURCES.getString("SONG_TAGS");
	}

	public static String getTypeFilterHint() {
		return Constants.RESOURCES.getString("HINT_TYPE_FILTER");
	}

	public static String getEditListButton() {
		return Constants.RESOURCES.getString("EDIT_SONGLIST_BUTTON");
	}

	public static String getDuplicateSongListCaption() {
		return Constants.RESOURCES.getString("DUPLICATE_SONGLIST");
	}

	public static String getFileMenu() {
		return Constants.RESOURCES.getString("FILE_MENU");
	}

	public static String getImportCsvFile() {
		return Constants.RESOURCES.getString("IMPORT_CSV");
	}

	public static String getSongSongListsCaption() {
		return Constants.RESOURCES.getString("SONG_CONTAINING_LISTS");
	}

	public static String getSortByPage() {
		return Constants.RESOURCES.getString("SORT_BY_PAGE");
	}

	public static String getSortByTitle() {
		return Constants.RESOURCES.getString("SORT_BY_TITLE");
	}

	public static String getIncludePages() {
		return Constants.RESOURCES.getString("INCLUDE_PAGES");
	}

	public static String getOptions() {
		return Constants.RESOURCES.getString("OPTIONS");
	}

	public static String getOutputFile() {
		return Constants.RESOURCES.getString("OUTPUT_FILE");
	}

	public static String getSplitTable() {
		return Constants.RESOURCES.getString("SPLIT_TABLE");
	}
}
