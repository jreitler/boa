
module com.reitler.boa.app {
	requires com.reitler.boa.core.api;
	requires com.reitler.boa.pdfgen;
	requires java.desktop;

	uses com.reitler.boa.core.interfaces.ISongManager;
	uses com.reitler.boa.core.interfaces.ISongListManager;
	uses com.reitler.boa.core.interfaces.persistence.IPersistenceHandler;
}