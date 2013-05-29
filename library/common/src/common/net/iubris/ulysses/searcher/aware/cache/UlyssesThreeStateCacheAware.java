package net.iubris.ulysses.searcher.aware.cache;

import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;

public class UlyssesThreeStateCacheAware implements ThreeStateCacheAware {
	@Override
	public Boolean isCacheAvailable() throws CacheTooOldException {
		return false;
	}
}
