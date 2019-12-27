package com.reitler.boa.core.interfaces.factory;

import java.util.LinkedList;
import java.util.List;

public class SongCreationParameter {

	public String title;
	public String artist = "";
	public String publisher = "";
	public List<String> tags = new LinkedList<>();
}
