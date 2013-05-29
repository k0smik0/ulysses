package net.iubris.ulysses._roboguice.module;

import java.util.List;

import net.iubris.diane._roboguice.module.AbstractDianeModule;
import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;
import net.iubris.diane.searcher.location.aware.cache.LocalizedSearcherCacheAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.full.LocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.network.LocalizedSearcherNetworkAwareStrictChecking;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.cache.UlyssesThreeStateCacheAware;
import net.iubris.ulysses.searcher.location.aware.cache.UlyssesLocalizedSearcherCacheAwareDummy;
import net.iubris.ulysses.searcher.location.aware.full.UlyssesLocalizedSearcherCacheNetworkAware;
import net.iubris.ulysses.searcher.location.aware.network.UlyssesLocalizedSearcherNetworkAware;

import com.google.inject.TypeLiteral;

public abstract class AbstractUlyssesModule extends AbstractDianeModule {
	
	@Override
	protected void configure() {
		super.configure();
		install (	getSocrates4UlyssesModule() );
//		install ( getKusorModule() );	
	}
	
	@Override
	protected void bindLocalizedSearcherCacheAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherCacheAwareStrictChecking<List<PlaceHere>>>(){}).to(UlyssesLocalizedSearcherCacheAwareDummy.class);
	}
	@Override
	protected void bindLocalizedSearcherNetworkAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherNetworkAwareStrictChecking<List<PlaceHere>>>(){}).to(UlyssesLocalizedSearcherNetworkAware.class);
	}
	@Override
	protected void bindLocalizedSearcherCacheNetworkAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherCacheNetworkAwareStrictChecking<List<PlaceHere>>>(){}).to(UlyssesLocalizedSearcherCacheNetworkAware.class);
	}

	/*@Override
	protected LocationProvider providesLocationProvider(LocationProvider locationProviderConcrete) {
		bind(LocationProvider.class).to(KLocator.class);
	}*/
	
	

	@Override
	protected void bindThreeStateCacheAware() {
		bind(ThreeStateCacheAware.class).to(UlyssesThreeStateCacheAware.class);
	}
	
	protected abstract Socrates4UlyssesModule getSocrates4UlyssesModule();
	
//	protected abstract KusorModule getKusorModule();
}
