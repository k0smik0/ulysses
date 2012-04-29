package net.iubris.ulysses.controller.delegates.locationaware;

import java.util.Collections;
import java.util.List;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.diane.searcher.locationaware.base.AbstractLocationAwareObserverSearcher;
import net.iubris.diane.searcher.locationaware.exceptions.search.LocationAwareSearchException;
import net.iubris.kusor.locator.KLocator;
import net.iubris.ulysses.controller.delegates.locationaware.annotations.UlyssesDistanceMinimumThreshold;
import net.iubris.ulysses.controller.delegates.locationaware.annotations.UlyssesTimeMinimumThreshold;
import net.iubris.ulysses.model.PlaceHere;

import com.google.inject.Inject;

public class UlyssesLocationSearcherDelegate extends AbstractLocationAwareObserverSearcher<Void, List<PlaceHere>> implements IUlyssesLocationSearcherDelegate {

	private List<PlaceHere> result = Collections.emptyList();

	@Inject
	public UlyssesLocationSearcherDelegate(KLocator kLocator,
			@UlyssesDistanceMinimumThreshold Integer distanceMinimumThreshold, 
			@UlyssesTimeMinimumThreshold long timeMinimumThreshold) {
		super(kLocator, distanceMinimumThreshold, timeMinimumThreshold);
	}

	@Override
	public Void search() throws LocationAwareSearchException, SearchException {
		return null;
	}

	/**
	 * @return empty list
	 */
	@Override
	public List<PlaceHere> getSearchResult() {
		return result;
	}

}
