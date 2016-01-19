package net.iubris.ulysses.ui.tasks.populate.map._base;

import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.tasks._base.PopulateTask;

import com.google.android.gms.maps.model.Marker;

public interface PopulateMapTask extends PopulateTask {
	Place findPlaceByMarkerId(String id);
	Marker findMarkerByPlaceEnhancedId(String id);
//	void execute(Location myLocation) /*throws LocationNullException*/;
}
