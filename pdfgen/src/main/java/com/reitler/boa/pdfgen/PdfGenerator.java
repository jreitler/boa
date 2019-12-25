package com.reitler.boa.pdfgen;

import java.io.ByteArrayInputStream;
import java.io.File;

import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.pdfgen.fop.FopExecutor;
import com.reitler.boa.pdfgen.freemarker.TemplateResolver;

public class PdfGenerator {

	public void generate(final ISongList list, final File target) {
		String content = new TemplateResolver().resolve(list);
		new FopExecutor().generate(new ByteArrayInputStream(content.getBytes()), target);
	}
}
