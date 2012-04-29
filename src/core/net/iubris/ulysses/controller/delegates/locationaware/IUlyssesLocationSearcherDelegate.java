package net.iubris.ulysses.controller.delegates.locationaware;

import java.util.List;

import net.iubris.diane.searcher.locationaware.LocationAwareSearcher;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNotNewerStateException;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationStateException;
import net.iubris.kusor.updater.LocationUpdater;
import net.iubris.ulysses.model.PlaceHere;

public interface IUlyssesLocationSearcherDelegate extends LocationAwareSearcher<Void,List<PlaceHere>,Boolean>, LocationUpdater {
	@Override
	public Boolean isInNewerLocation() throws LocationNotNewerStateException, LocationStateException;
}
