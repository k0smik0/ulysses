package net.iubris.ulysses.ui.activity.details;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.ui.fragments.details.DetailsFragmentGallery;
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

	// Cole St, San Fran
//    private static final LatLng SAN_FRAN = new LatLng(37.765927, -122.449972);

//    private StreetViewPanorama streetViewPanorama;

//    private CheckBox mStreetNameCheckbox;
//    private CheckBox mNavigationCheckbox;
//    private CheckBox mZoomCheckbox;
//    private CheckBox mPanningCheckbox;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street_view_panorama);
		
		if (savedInstanceState == null) {
			location = (Location) getIntent().getSerializableExtra(DetailsFragmentGallery.EXTRA_ULYSSES_LOCATION);
		}

		// mStreetNameCheckbox = (CheckBox) findViewById(R.id.streetnames);
		// mNavigationCheckbox = (CheckBox) findViewById(R.id.navigation);
		// mZoomCheckbox = (CheckBox) findViewById(R.id.zoom);
		// mPanningCheckbox = (CheckBox) findViewById(R.id.panning);

		streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
		streetViewPanoramaFragment.getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback);
	}
	
	private Location getLocation() {
		return location;
	}
	
	final OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback = new OnStreetViewPanoramaReadyCallback() {
		@Override
		public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
			// StreetViewPanoramaActivity.this.streetViewPanorama =
			// streetViewPanorama;
			streetViewPanorama.setStreetNamesEnabled(
			// mStreetNameCheckbox.isChecked()
					true);
			streetViewPanorama.setUserNavigationEnabled(
			// mNavigationCheckbox.isChecked()
					true);
			streetViewPanorama.setZoomGesturesEnabled(
			// mZoomCheckbox.isChecked()
					true);
			streetViewPanorama.setPanningGesturesEnabled(
			// mPanningCheckbox.isChecked()
					true);

			Location location = getLocation();
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
	        float bearing = getBearing(
	            streetViewPanoramaLocation.position.latitude,
	            streetViewPanoramaLocation.position.longitude,
	            getLocation().getLatitude(),
	            getLocation().getLongitude() );
	        
	        //Remove the listener 
	        streetViewPanoramaFragment.getStreetViewPanorama().setOnStreetViewPanoramaChangeListener(null);
	        //Change the camera angle
	        StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
	            .bearing(bearing)
	            .build();
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

    /*private boolean checkReady() {
        if (streetViewPanorama == null) {
//            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onStreetNamesToggled(View view) {
        if (!checkReady()) {
            return;
        }
        streetViewPanorama.setStreetNamesEnabled(mStreetNameCheckbox.isChecked());
    }

    public void onNavigationToggled(View view) {
        if (!checkReady()) {
            return;
        }
        streetViewPanorama.setUserNavigationEnabled(mNavigationCheckbox.isChecked());
    }

    public void onZoomToggled(View view) {
        if (!checkReady()) {
            return;
        }
        streetViewPanorama.setZoomGesturesEnabled(mZoomCheckbox.isChecked());
    }

    public void onPanningToggled(View view) {
        if (!checkReady()) {
            return;
        }
        streetViewPanorama.setPanningGesturesEnabled(mPanningCheckbox.isChecked());
    }*/
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.flip_to, R.anim.flip_to);
	}
}
