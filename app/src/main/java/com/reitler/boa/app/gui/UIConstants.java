package com.reitler.boa.app.gui;

import java.util.Locale;
import java.util.ResourceBundle;

public class UIConstants {

	private static final ResourceBundle RESOURCES = ResourceBundle.getBundle("UIElements", Locale.getDefault());

	public static final String getAllSongsTitle() {
		return UIConstants.RESOURCES.getString("CAPTION_ALL_SONGS");
	}

	public static final String getAllSongListsTitle() {
		return UIConstants.RESOURCES.getString("CAPTION_ALL_SONGLISTS");
	}

	public static final String getSongTitleCaption() {
		return UIConstants.RESOURCES.getString("SONG_TITLE");
	}

	public static final String getSongArtistCaption() {
		return UIConstants.RESOURCES.getString("SONG_ARTIST");
	}

	public static final String getSongPublisherCaption() {
		return UIConstants.RESOURCES.getString("SONG_PUBLISHER");
	}

	public static final String getCreateSongButton() {
		return UIConstants.RESOURCES.getString("CREATE_SONG_BUTTON");
	}

	public static final String getSongCreationCaption() {
		return UIConstants.RESOURCES.getString("CREATE_SONG_CAPTION");
	}

	public static final String getSongCreationMessage() {
		return UIConstants.RESOURCES.getString("CREATE_SONG_MESSAGE");
	}

	public static final String getDeleteSongButton() {
		return UIConstants.RESOURCES.getString("DELETE_SONG_BUTTON");
	}

	public static String getGeneratePdfButton() {
		return UIConstants.RESOURCES.getString("GENERATE_PDF_BUTTON");
	}

	public static String getGeneratePdfCaption() {
		return UIConstants.RESOURCES.getString("CAPTION_GENERATE_PDF");
	}

	public static String getPdfFileType() {
		return UIConstants.RESOURCES.getString("PDF_FILES");
	}

	public static String getAddSongButton() {
		return UIConstants.RESOURCES.getString("ADD_SONG_BUTTON");
	}

	public static String getRemoveSongButton() {
		return UIConstants.RESOURCES.getString("REMOVE_SONG_BUTTON");
	}

	public static String getAssignmentPageCaption() {
		return UIConstants.RESOURCES.getString("CAPTION_ASSIGNMENT_PAGE");
	}

	public static String getCreateSongListButton() {
		return UIConstants.RESOURCES.getString("CREATE_SONGLIST_BUTTON");
	}

	public static String getDeleteSongListButton() {
		return UIConstants.RESOURCES.getString("DELETE_SONGLIST_BUTTON");
	}

	public static String getSongListNameCaption() {
		return UIConstants.RESOURCES.getString("CAPTION_SONGLIST_NAME");
	}

	public static String getSongListCreationCaption() {
		return UIConstants.RESOURCES.getString("CREATE_SONGLIST_CAPTION");
	}

	public static String getSongListCreationMessage() {
		return UIConstants.RESOURCES.getString("CREATE_SONGLIST_MESSAGE");
	}

	public static String getButtonOk() {
		return UIConstants.RESOURCES.getString("BUTTON_OK");
	}

	public static String getButtonCancel() {
		return UIConstants.RESOURCES.getString("BUTTON_CANCEL");
	}

	public static String getSongTagsCaption() {
		return UIConstants.RESOURCES.getString("SONG_TAGS");
	}

	public static String getTypeFilterHint() {
		return UIConstants.RESOURCES.getString("HINT_TYPE_FILTER");
	}
}
