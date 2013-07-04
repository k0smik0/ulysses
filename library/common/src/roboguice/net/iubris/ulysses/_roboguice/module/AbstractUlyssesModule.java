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
