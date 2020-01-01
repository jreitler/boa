package com.reitler.boa.pdfgen.freemarker;

import java.io.StringWriter;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.reitler.boa.pdfgen.PdfGenerationParameter;

import freemarker.cache.TemplateLoader;
import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateResolver {

	public String resolve(final PdfGenerationParameter parameter) {
		ViewModel dataModel = new ViewModel(parameter);

		TemplateLoader loader = new URLTemplateLoader() {

			@Override
			protected URL getURL(final String name) {
				return TemplateResolver.class.getClassLoader().getResource(name);
			}
		};

		Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
		cfg.setTemplateLoader(loader);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");

		try {
			Template template = cfg.getTemplate("template.ftl");
			StringWriter out = new StringWriter();
			template.process(dataModel, out);
			return out.toString();
		} catch (Exception e) {
			Logger.getLogger(TemplateResolver.class.getCanonicalName()).log(Level.WARNING,
					"Error while resolving SongList template", e);
		}
		return "errors occured";
	}
}
