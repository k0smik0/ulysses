package net.iubris.ulysses.ui.activity.details;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.ui.fragments.details.gallery.DetailsFragmentGallery;
import net.iubris.ulysses.ui.tasks.populate.map._utils.LocationUtils;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

/**
 * This shows how to create an activity with static streetview (all options have been switched off)
 */
public class StreetViewPanoramaActivity extends FragmentActivity {

    private SupportStreetViewPanoramaFragment streetViewPanoramaFragment;

	private Location location;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street_view_panorama);
		
		if (savedInstanceState == null) {
			location = (Location) getIntent().getSerializableExtra(DetailsFragmentGallery.EXTRA_ULYSSES_LOCATION);
		}

		streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
		streetViewPanoramaFragment.getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
	}
	
	private Location getLocation() {
		return location;
	}
	
	final OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback = new OnStreetViewPanoramaReadyCallback() {
		@Override
		public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
			streetViewPanorama.setStreetNamesEnabled(true);
			streetViewPanorama.setUserNavigationEnabled(true);
			streetViewPanorama.setZoomGesturesEnabled(true);
			streetViewPanorama.setPanningGesturesEnabled(true);

			Location location = StreetViewPanoramaActivity.this.getLocation();
			if (location!=null) {
				streetViewPanorama.setPosition(LocationUtils.locationToLatLng(location));
				StreetViewPanoramaLocation streetViewPanoramaLocation = streetViewPanorama.getLocation();
				if (streetViewPanoramaLocation != null && streetViewPanoramaLocation.links != null) {
					streetViewPanorama.setPosition( streetViewPanoramaLocation.links[0].panoId );
				}
			}
		}
	};
	
	OnStreetViewPanoramaChangeListener onStreetViewPanoramaChangeListener = new OnStreetViewPanoramaChangeListener() {

		@Override
		public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
			//Get the angle between the target location and road side location
	        float bearing = getBearing( streetViewPanoramaLocation.position.latitude, streetViewPanoramaLocation.position.longitude, getLocation().getLatitude(), getLocation().getLongitude() );
	        
	        //Remove the listener 
	        streetViewPanoramaFragment.getStreetViewPanorama().setOnStreetViewPanoramaChangeListener(null);
	        //Change the camera angle
	        StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder().bearing(bearing).build();
	        streetViewPanoramaFragment.getStreetViewPanorama().animateTo(camera, 1);
		}		
	};
	
	private float getBearing(double startLat, double startLng, double endLat, double endLng){
		android.location.Location startLocation = new android.location.Location("startlocation");
        startLocation.setLatitude(startLat);
        startLocation.setLongitude(startLng);
        
        android.location.Location endLocation = new android.location.Location("endlocation");
        endLocation.setLatitude(endLat);
        endLocation.setLongitude(endLng);

        return startLocation.bearingTo(endLocation);
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.flip_to, R.anim.flip_to);
	}
}
