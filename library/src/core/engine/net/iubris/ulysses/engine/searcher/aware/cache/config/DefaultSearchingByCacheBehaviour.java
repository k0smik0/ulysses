package net.iubris.ulysses.engine.searcher.aware.cache.config;

import net.iubris.diane.aware.cache.states.three.SearchingByCacheBehaviour;

public class DefaultSearchingByCacheBehaviour implements SearchingByCacheBehaviour {
	@Override
	public boolean useFirstlyCache() {
		return false;
	}
}
