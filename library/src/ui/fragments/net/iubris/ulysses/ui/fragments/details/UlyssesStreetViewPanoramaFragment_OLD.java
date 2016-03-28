package net.iubris.ulysses.ui.fragments.details;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.ui.tasks.populate.map._utils.LocationUtils;
import roboguice.util.Ln;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class UlyssesStreetViewPanoramaFragment_OLD extends SupportStreetViewPanoramaFragment{
    public static final UlyssesStreetViewPanoramaFragment newInstance(double latitude, double longitude){
        Bundle args = new Bundle();
        args.putDouble("latitude", latitude);
        args.putDouble("longitude", longitude);

        UlyssesStreetViewPanoramaFragment fragment =  new UlyssesStreetViewPanoramaFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    private Bundle arguments;
	protected StreetViewPanorama streetViewPanorama;
	private Location location;
    
    public UlyssesStreetViewPanoramaFragment() {
    	Ln.d(this);
	}
	
	
//	private Button buttonGetPanorama;
//	private LinearLayout layoutPanorama;
//	private PlaceProvideable activity;
//	private double latitude;
//	private double longitude;

	private OnStreetViewPanoramaReadyCallback onStreetViewPanoramaReadyCallback = new OnStreetViewPanoramaReadyCallback() {
		@Override
		public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
			if(streetViewPanorama != null){
				UlyssesStreetViewPanoramaFragment.this.streetViewPanorama = streetViewPanorama;
//				layoutPanorama.setVisibility(View.VISIBLE);
				
				if (location!=null)
					streetViewPanorama.setPosition( LocationUtils.locationToLatLng(location));
				else {
		            double latitude = arguments.getDouble("latitude", -1);
		            double longitude = arguments.getDouble("longitude", -1);
		            streetViewPanorama.setPosition(new LatLng(latitude, longitude));
				}
				streetViewPanorama.setOnStreetViewPanoramaChangeListener(onStreetViewPanoramaChangeListener);
	            
//	            buttonGetPanorama.setVisibility(View.GONE);
	        }
		}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Ln.d(this);
		View rootView = inflater.inflate(R.layout.frame_street_view, container, false);
//		LinearLayout layoutPanorama = (LinearLayout) rootView.findViewById(R.id.layout_panorama);
//		layoutPanorama.setVisibility(View.VISIBLE);
		return rootView;
	};
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		LinearLayout layoutPanorama = (LinearLayout) getActivity().findViewById(R.id.layout_panorama);
//		layoutPanorama.setVisibility(View.VISIBLE);
//		buttonGetPanorama = (Button) getActivity().findViewById(R.id.button_get_panorama);
//		buttonGetPanorama.setOnClickListener( new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				arguments = getArguments();
//				Ln.d("clicked for panorama");
//				layoutPanorama.setVisibility(View.VISIBLE);
////		    	Location location = StreetViewFragment.this.activity.getPlace().getLocation();
////		    	latitude = location.getLatitude();
////		    	longitude = location.getLongitude();
////		    	StreetViewFragment.newInstance(location.getLatitude(), location.getLongitude());
//		    	getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback );
//			}
//		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Ln.d("onStart");
		getStreetViewPanoramaAsync(onStreetViewPanoramaReadyCallback );
	}
	
    
    private float getBearing(double startLat, double startLng, double endLat, double endLng){
        android.location.Location startLocation = new android.location.Location("startlocation");
        startLocation.setLatitude(startLat);
        startLocation.setLongitude(startLng);
        
        android.location.Location endLocation = new android.location.Location("endlocation");
        endLocation.setLatitude(endLat);
        endLocation.setLongitude(endLng);

        return startLocation.bearingTo(endLocation);
    }
    
    private OnStreetViewPanoramaChangeListener onStreetViewPanoramaChangeListener = new OnStreetViewPanoramaChangeListener() {
		@Override
		public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
			//Get the angle between the target location and road side location
	        float bearing = getBearing(
	            streetViewPanoramaLocation.position.latitude,
	            streetViewPanoramaLocation.position.longitude,
	            getArguments().getDouble("latitude", -1),
	            getArguments().getDouble("longitude", -1) );
	        
	        //Remove the listener 
	        getStreetViewPanorama().setOnStreetViewPanoramaChangeListener(null);
	       //Change the camera angle
	        StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
	            .bearing(bearing)
	            .build();
	        getStreetViewPanorama().animateTo(camera, 1);
		}
	};
	

	public void setLocation(Location location) {
		if (streetViewPanorama!=null)
			streetViewPanorama.setPosition( LocationUtils.locationToLatLng(location));
		else
			this.location = location;
	}
}