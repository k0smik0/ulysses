package net.iubris.ulysses.asynctask;

import java.util.concurrent.Callable;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.network.PlacesNoNetworkException;

public interface SearcherCallable<V> extends Callable<V> {
	@Override
	public V call() throws PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, PlacesNoNetworkException, SearchException, Exception;	
}
