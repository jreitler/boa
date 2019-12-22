module com.reitler.boa.core {

	requires com.reitler.boa.core.api;
	requires java.sql;
	requires sqlite.jdbc;

	provides com.reitler.boa.core.interfaces.ISongManager with com.reitler.boa.core.service.SongManagerServiceProvider;
	provides com.reitler.boa.core.interfaces.ISongListManager
			with com.reitler.boa.core.service.SongListManagerServiceProvider;
	provides com.reitler.boa.core.interfaces.persistence.IPersistenceHandler
			with com.reitler.boa.core.service.PersistenceHandlerServiceProvider;
}