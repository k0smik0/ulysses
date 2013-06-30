package net.iubris.ulysses_sample.config;

import javax.inject.Singleton;

import net.iubris.ulysses.searcher.location.aware.network.delegate.config.AbstractConfigMandatory;

@Singleton
public final class UlyssesSampleConfigMandatory extends AbstractConfigMandatory {
	@Override
	public String getKey() {				
		return "AIzaSyAfOlNmRr5G-4BPDd1faYsn9kvkV5ebBRk";
	}

	@Override
	public String getApplicationName() {				
		return "Ulysses";
	}
}