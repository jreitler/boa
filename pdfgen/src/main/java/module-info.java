module com.reitler.boa.pdfgen {

	requires transitive com.reitler.boa.core.api;
	requires fop;
	requires xmlgraphics.commons;
	requires java.xml;
	requires freemarker;

	uses com.reitler.boa.core.interfaces.ISongManager;
	uses com.reitler.boa.core.interfaces.ISongListManager;

	requires com.reitler.boa.core;

	exports com.reitler.boa.pdfgen;
	exports com.reitler.boa.pdfgen.freemarker;
}