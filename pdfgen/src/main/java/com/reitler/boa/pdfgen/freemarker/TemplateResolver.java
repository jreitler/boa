package com.reitler.boa.pdfgen.freemarker;

import java.io.StringWriter;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.Locale;

import com.reitler.boa.core.interfaces.ISongList;

import freemarker.cache.TemplateLoader;
import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateResolver {

	public String resolve(final ISongList list) {
		ViewModel dataModel = new ViewModel(list);

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
			System.getLogger(TemplateResolver.class.getCanonicalName()).log(Level.ERROR,
					"Error while resolving SongList template", e);
		}
		return "errors occured";
	}
}
