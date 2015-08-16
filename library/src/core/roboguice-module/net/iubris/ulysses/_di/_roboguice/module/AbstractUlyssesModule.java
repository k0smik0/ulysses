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
import net.iubris.ulysses.engine.searcher.aware.cache.UlyssesThreeStateCacheAware;
import net.iubris.ulysses.engine.searcher.aware.full.DefaultUlyssesSearcher;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware;
import net.iubris.ulysses.engine.searcher.location.aware.cache.UlyssesLocalizedSearcherCacheAware;
import net.iubris.ulysses.engine.searcher.location.aware.full.DefaultUlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.DefaultUlyssesLocalizedSearcherNetworkAware;
import net.iubris.ulysses.engine.searcher.location.aware.network.UlyssesLocalizedSearcherNetworkAware;
import net.iubris.ulysses.model.Place;

import com.google.inject.TypeLiteral;

public abstract class AbstractUlyssesModule extends AbstractDianeModule {
	
//	private final SocratesToUlyssesModule socratesToUlyssesModule;
//
//	public AbstractUlyssesModule(SocratesToUlyssesModule socratesToUlyssesModule) {
//		this.socratesToUlyssesModule = socratesToUlyssesModule;
//	}
	
	@Override
	protected void configure() {
		super.configure();
		install ( getSocratesToUlyssesModule() );
		
		bindUlyssesSearcher();
		bindUlyssesLocalizedSearcher();
		bindUlyssesLocalizedSearcherCacheAware();
		bindUlyssesLocalizedSearcherNetworkAware();
		
		bindPersister();
	}
	
	protected void bindUlyssesSearcher() {
		bind(UlyssesSearcher.class).to(DefaultUlyssesSearcher.class);
	}
	protected void bindUlyssesLocalizedSearcher() {
		bind(UlyssesLocalizedSearcher.class).to(DefaultUlyssesLocalizedSearcher.class);
	}
	protected void bindUlyssesLocalizedSearcherCacheAware() {
		bind(UlyssesLocalizedSearcherCacheAware.class).to(DefaultUlyssesLocalizedSearcherCacheAware.class);
	}
	
	protected void bindUlyssesLocalizedSearcherNetworkAware() {
		bind(UlyssesLocalizedSearcherNetworkAware.class).to(DefaultUlyssesLocalizedSearcherNetworkAware.class);
	}

	// for Diane
	@Override
	protected void bindLocalizedSearcherCacheAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherCacheAwareStrictChecking<List<Place>>>(){}).to(DefaultUlyssesLocalizedSearcherCacheAware.class);	}
	@Override
	protected void bindLocalizedSearcherNetworkAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherNetworkAwareStrictChecking<List<Place>>>(){}).to(DefaultUlyssesLocalizedSearcherNetworkAware.class);
	}
	@Override
	protected void bindLocalizedSearcherCacheNetworkAwareStrictChecking() {
		bind(new TypeLiteral<LocalizedSearcherCacheNetworkAwareStrictChecking<List<Place>>>(){}).to(DefaultUlyssesLocalizedSearcher.class);
	}


	

	@Override
	protected void bindDistanceMaximumThreshold() {
		bind(Integer.class).annotatedWith(DistanceMaximumThreshold.class).toInstance(Integer.valueOf(100));
	}
	@Override
	protected void bindThreeStateCacheAware() {
		bind(ThreeStateCacheAware.class).to(UlyssesThreeStateCacheAware.class);
	}
	
	protected abstract SocratesToUlyssesModule getSocratesToUlyssesModule();
	
	protected abstract void bindPersister();
	/*protected void bindPersister() {
		bind(Persister.class).to(OrmlitePersister.class);
	}*/
}
