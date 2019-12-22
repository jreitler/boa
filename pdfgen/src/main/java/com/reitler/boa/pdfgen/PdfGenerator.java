package com.reitler.boa.pdfgen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ServiceLoader;

import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;
import com.reitler.boa.pdfgen.fop.FopExecutor;
import com.reitler.boa.pdfgen.freemarker.TemplateResolver;

public class PdfGenerator {

	public static void main(final String args[]) {
		ServiceLoader<ISongManager> load = ServiceLoader.load(ISongManager.class);
		ISongManager iSongManager = load.findFirst().get();

		ServiceLoader<ISongListManager> service = ServiceLoader.load(ISongListManager.class);
		ISongListManager songListManager = service.findFirst().get();

		ISongList list = songListManager.createSongList("My FancyList");

		for (int i = 1; i <= 25; i++) {
			ISong s = iSongManager.createSong("Song No. " + i);
			songListManager.assign(s, list, String.valueOf(i));
		}

		File target = new File("/home/jan/temp/test2.pdf");

		new PdfGenerator().generate(list, target);
	}

	public void generate(final ISongList list, final File target) {
		String content = new TemplateResolver().resolve(list);
		new FopExecutor().generate(new ByteArrayInputStream(content.getBytes()), target);
	}
}
