package net.iubris.ulysses.tasks.search.aware;

import java.util.List;

import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.locationstate.LocationExceptionState;

public interface SearchAwareTaskLocationStateable {
	UlyssesSearcher getSearcher();
	UlyssesLocalizedSearcher getLocalizedSearcher();
	void doOnSuccess(List<Place> result);
	void setLocationExceptionState(LocationExceptionState currentLocationExceptionState, 
			LocationExceptionState futureLocationTooNearExceptionState);
	void setResultFoundByNetwork();
	void setResultFoundByCache();	
}
