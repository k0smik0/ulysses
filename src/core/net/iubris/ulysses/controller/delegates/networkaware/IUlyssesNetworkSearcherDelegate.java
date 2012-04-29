package net.iubris.ulysses.controller.delegates.networkaware;

import java.util.List;

import android.location.Location;

import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNullException;
import net.iubris.diane.searcher.networkaware.NetworkAwareByLocationSearcher;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.PlaceHere;

public interface IUlyssesNetworkSearcherDelegate extends NetworkAwareByLocationSearcher<Void,  List<PlaceHere>, Boolean> {
	@Override
	public Void search(Location location) throws LocationNullException, NoNetworkException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException;
}
