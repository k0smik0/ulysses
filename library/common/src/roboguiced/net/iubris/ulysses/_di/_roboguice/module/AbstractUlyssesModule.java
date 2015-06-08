/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AbstractUlyssesModule.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses._di._roboguice.module;


import java.util.List;

import net.iubris.diane._roboguice.module.AbstractDianeModule;
import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;
import net.iubris.diane.aware.location.state.three.base.annotation.DistanceMaximumThreshold;
import net.iubris.diane.searcher.location.aware.cache.LocalizedSearcherCacheAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.full.LocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.network.LocalizedSearcherNetworkAwareStrictChecking;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.aware.cache.UlyssesThreeStateCacheAware;
import net.iubris.ulysses.engine.searcher.location.aware.cache.UlyssesLocalizedSearcherCacheAwareDummy;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.UlyssesLocalizedSearcherNetworkAware;

import com.google.inject.TypeLiteral;

public abstract class AbstractUlyssesModule extends AbstractDianeModule {
	
	@Override
	protected void configure() {
		super.configure();
		install ( getSocratesToUlyssesModule() );
//		install ( getKusorModule() );	
	}
	
//	@Override
//	protected void bindFullAwareSearcher() {
//		bind(FullAwareSearcher.class).to(UlyssesSearcher.class);
//	}
////	@Override
//	protected void bindLocalizedSearcher() {
//		bind(new TypeLiteral<LocalizedSearcherCacheAwareStrictChecking<List<PlaceHere>>>(){}).to(UlyssesLocalizedSearcherCacheAwareDummy.class);
//	}
	
	@Override
	protected void bindLocalizedSearcherCacheAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherCacheAwareStrictChecking<List<PlaceEnhanced>>>(){}).to(UlyssesLocalizedSearcherCacheAwareDummy.class);
	}
	@Override
	protected void bindLocalizedSearcherNetworkAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherNetworkAwareStrictChecking<List<PlaceEnhanced>>>(){}).to(UlyssesLocalizedSearcherNetworkAware.class);
	}
	@Override
	protected void bindLocalizedSearcherCacheNetworkAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherCacheNetworkAwareStrictChecking<List<PlaceEnhanced>>>(){}).to(UlyssesLocalizedSearcher.class);
	}

	/*@Override
	protected LocationProvider providesLocationProvider(LocationProvider locationProviderConcrete) {
		bind(LocationProvider.class).to(KLocator.class);
	}*/
	

	@Override
	protected void bindDistanceMaximumThreshold() {
		bind(Integer.class).annotatedWith(DistanceMaximumThreshold.class).toInstance(Integer.valueOf(100));
	}

	@Override
	protected void bindThreeStateCacheAware() {
		bind(ThreeStateCacheAware.class).to(UlyssesThreeStateCacheAware.class);
	}
	
	protected abstract SocratesToUlyssesModule getSocratesToUlyssesModule();
	
//	protected abstract KusorModule getKusorModule();
}
