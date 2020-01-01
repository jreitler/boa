package com.reitler.boa.pdfgen;

import java.io.ByteArrayInputStream;

import com.reitler.boa.pdfgen.fop.FopExecutor;
import com.reitler.boa.pdfgen.freemarker.TemplateResolver;

public class PdfGenerator {

	public void generate(final PdfGenerationParameter parameter) {
		String content = new TemplateResolver().resolve(parameter);
		new FopExecutor().generate(new ByteArrayInputStream(content.getBytes()), parameter.getTargetFile());
	}
}
