package net.iubris.ulysses.searcher.location.aware.network.delegate.config;

import net.iubris.socrates.config.ConfigMandatory;
import net.iubris.socrates.model.http.request.url.output.HttpParserOutputType;

/*
@Provides
Class<PlacesList> providePlaceListClass() {
	return PlacesList.class;
}*/
public abstract class AbstractConfigMandatory implements ConfigMandatory{
	@Override
	public boolean isUseSensor() {
		return true;
	}
	
	@Override
	public HttpParserOutputType getOutput() {				
		return HttpParserOutputType.json;
	}
	/*
	@Override
	public String getKey() {				
		return "";
	}
	@Override
	public String getApplicationName() {				
		return "SocratesTest";
	}*/
}