package net.iubris.ulysses.map.asynctask;

import java.util.ArrayList;
import java.util.List;

import net.iubris.socrates.model.http.response.data.geocoding.Location;
import net.iubris.ulysses.asynctask.UlyssesAsyncTask;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public abstract class PopulateMapAsyncTask extends UlyssesAsyncTask {

//	protected final PlaceItemizedOverlay hereOverlay;
//	private final MapView mapView;
	protected final GoogleMap map;
	private final List<Marker> markers;
	
	protected PopulateMapAsyncTask(Context context, GoogleMap map/*, PlaceItemizedOverlay hereOverlay*/) {
		super(context);
//		this.mapView = mapView;
//		this.hereOverlay = hereOverlay;
		this.map = map/*, PlaceItemizedOverlay hereOverlay*/;
		markers = new ArrayList<Marker>();
	}
	
	public List<Marker> getMarkers() {
		return markers;
	}
	
	@Override
	protected void onSuccess(List<PlaceHere> placeHeres) throws Exception {
		populateOverlayFromResult(placeHeres);
	}
	
	protected void populateOverlayFromResult(List<PlaceHere> placeHeres) {
		/*for (PlaceHere placeHere: placeHeres) {
			//typeDrawableMap.get(placeHere.getPlace().getTypes())
			Set<PlaceType> placeTypes = placeHere.getPlace().getTypes();
			for (PlaceType placeType: placeTypes) {
//Ln.d(placeHere.getPlace().getName() + ": "+ placeType);
				//GenzianaPlaceTypeAction genzianaPlaceTypeAction = placeTypeActionsMap.get(placeType);
//Ln.d(genzianaPlaceTypeAction);

				//if (placeTypeActionsMap.containsKey(placeType)) {
//Ln.d(placeHere.getPlace().getName() + ": "+ placeType);
//GenzianaPlaceTypeAction genzianaPlaceTypeAction = placeTypeActionsMap.get(placeType);
//Ln.d(genzianaPlaceTypeAction);
					PlaceItem = pi = DefaultMarkerDrawer.buildItem(
							placeHere.getPlace().getGeometry().getLocation().toGeoPoint(), arg1, arg2, arg3);
					getPlaceTypeActionsMap().get(placeType).place(placeHere, hereOverlay);
				//}
					
			}
		}*/
		
		for (PlaceHere placeHere: placeHeres) {
			
			MarkerOptions mo = new MarkerOptions();
			Location location = placeHere.getPlace().getGeometry().getLocation();
			mo
				.position( new LatLng(location.getLatitude(),location.getLongitude()) )
				.title( placeHere.getPlace().getName() )
				.snippet( Math.floor( placeHere.getPlace().getRating() ) +"/5" )
				.icon( BitmapDescriptorFactory.fromBitmap( getSieve().find(placeHere.getPlace()) ) );
			markers.add( map.addMarker(mo) );
			
			/*PlaceItem placeItem = new PlaceItem(
					placeHere.getPlace().getGeometry().getLocation().toGeoPoint(),
					placeHere.getPlace().getName(), 
					Math.floor( placeHere.getPlace().getRating() ) +"/5",
					getSieve().act(placeHere.getPlace())
					);
			hereOverlay.addItem(placeItem);*/
		}
		
		
		/*final GeoPoint center = mapView.get myLocationOverlay.getMyLocation();
		if (center != null) {
			mapView.getController().animateTo(center);
			mapView.getController().setCenter( center );
			mapView.getController().setZoom(16);
		}*/

		// old
//		mapView.invalidate();
	};
	
	protected abstract Sieve getSieve();

}
