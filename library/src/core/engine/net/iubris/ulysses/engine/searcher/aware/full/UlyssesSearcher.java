package net.iubris.ulysses.engine.searcher.aware.full;

import java.util.List;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.full.FullAwareSearcher;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;

public interface UlyssesSearcher extends FullAwareSearcher<List<Place>>{
	@Override
	public Void search(Void... arg0) throws StillSearchException,
	LocationTooNearException,
	LocationNotSoUsefulException, NoNetworkException,
	CacheEmptyException, 
	CacheAwareSearchException,
	PlacesRetrievingException, 
	PlacesUnbelievableZeroResultStatusException, 
	PlacesTyrannusStatusException;
}
