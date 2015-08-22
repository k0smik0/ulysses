package net.iubris.ulysses.ui.activity.details;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.ui.tasks.populate.map._utils.LocationUtils;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;

/**
 * This shows how to create an activity with static streetview (all options have been switched off)
 */
public class StreetViewPanoramaActivity extends FragmentActivity {

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

//        mStreetNameCheckbox = (CheckBox) findViewById(R.id.streetnames);
//        mNavigationCheckbox = (CheckBox) findViewById(R.id.navigation);
//        mZoomCheckbox = (CheckBox) findViewById(R.id.zoom);
//        mPanningCheckbox = (CheckBox) findViewById(R.id.panning);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
            new OnStreetViewPanoramaReadyCallback() {
                @Override
                public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
//                	StreetViewPanoramaActivity.this.streetViewPanorama = streetViewPanorama;
                    streetViewPanorama.setStreetNamesEnabled(
//                    		mStreetNameCheckbox.isChecked()
                    		true
                    		);
                    streetViewPanorama.setUserNavigationEnabled(
//                    		mNavigationCheckbox.isChecked()
                    		true
                    		);
                    streetViewPanorama.setZoomGesturesEnabled(
//                    		mZoomCheckbox.isChecked()
                    		true
                    		);
                    streetViewPanorama.setPanningGesturesEnabled(
//                    		mPanningCheckbox.isChecked()
                    		true
                    		);

                    if (savedInstanceState == null) {
                    	Location location = (Location) getIntent().getSerializableExtra("location");
                    	streetViewPanorama.setPosition( LocationUtils.locationToLatLng(location) );
                    }
                }
            });
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
}
