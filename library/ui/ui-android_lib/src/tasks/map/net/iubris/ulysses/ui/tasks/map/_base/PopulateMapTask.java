package net.iubris.ulysses.ui.tasks.map._base;

import java.util.List;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.ui.tasks._base.PopulateTask;
import android.location.Location;

import com.google.android.gms.maps.model.Marker;

public interface PopulateMapTask extends PopulateTask {
	List<Marker> getMarkers();
	PlaceEnhanced findPlaceEnhancedById(String id);
	void execute(Location myLocation) /*throws LocationNullException*/;
}
