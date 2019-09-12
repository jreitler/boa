module com.reitler.boa.core {

	requires com.reitler.boa.core.api;

	provides com.reitler.boa.core.interfaces.ISongManager with com.reitler.boa.core.service.SongManagerServiceProvider;
	provides com.reitler.boa.core.interfaces.ISongFactory with com.reitler.boa.core.service.SongManagerServiceProvider;
}