package net.iubris.ulysses.ui.fragments.map;



import javax.inject.Inject;

import net.iubris.apollus2.ui.fragments._base.Titleable;
import net.iubris.apollus2.ui.fragments._base.Updatable;
import net.iubris.apollus2.ui.fragments.list._base.Markerable;
import net.iubris.apollus2.ui.fragments.map._base.MarkerShowable;
import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.search.utils.Buffer;
import net.iubris.ulysses.ui.activity._base.UlyssesSearchActivity;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.tasks._base.SearchType;
import net.iubris.ulysses.ui.tasks.populate.map._base.MapTasksMap;
import net.iubris.ulysses.ui.tasks.populate.map._base.PopulateMapTask;
import net.iubris.ulysses.ui.tasks.populate.map.aware.PopulateMapAwareTask;
import net.iubris.ulysses.ui.tasks.populate.map.localized.PopulateMapLocalizedTask;
import roboguice.RoboGuice;
import roboguice.util.Ln;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public abstract class UlyssesMapFragment
<ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable> 
extends SupportMapFragment implements MarkerShowable, Updatable, Titleable/*, SearchTypable*/ {

	protected static final float ZOOM = 15f;
	protected static final int DURATION = 50;
	private static final String TITLE = "Map";
	protected static final int INDEX = 1;
	
	private GoogleMap map;
//	private ListMapTabsSearchTypableLocatableActivity<ListFragmentMarkerable, MarkerShowableMapFragment> activity;
	private UlyssesSearchActivity<ListFragmentMarkerable, MarkerShowableMapFragment> activity;

	@Inject private Buffer buffer;
	@Inject private Sieve sieve;
	@Inject private MapTasksMap populateMapTasksMap;

	private boolean wasDestroyed = true;
	
	public UlyssesMapFragment() {}
	
	

	private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
		@Override
		public void onMapReady(GoogleMap googleMap) {
			map = googleMap;
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMapToolbarEnabled(true);
			
			initTasksAndClickListener(map);
			
			SearchType searchType = activity.getSearchType();
//			Ln.d(searchType);
			PopulateMapTask task = populateMapTasksMap.getTask( searchType );
//			Ln.d(task);
			if (searchType.equals(SearchType.AWARE)) {
				Location location = getLocation();
				if (location!=null) { // need this for onResume, if screen off etc etc
					zoomOnFirstFix(googleMap, location );
					task.execute( location );
				}
			} else {
				task.execute();
			}
		}
		private void zoomOnFirstFix(GoogleMap googleMap, Location location) {
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 15f));
		}
	};
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		this.activity = (ListMapTabsSearchTypableLocatableActivity<ListFragmentMarkerable, MarkerShowableMapFragment>) activity;
		this.activity = (UlyssesSearchActivity<ListFragmentMarkerable, MarkerShowableMapFragment>) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RoboGuice.getInjector(activity).injectMembersWithoutViews(this);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RoboGuice.getInjector(activity).injectViewMembers(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getMapAsync(onMapReadyCallback);
	}
	
	@Override
	public void onPause() {
		if (map!=null) {
			map.setMyLocationEnabled(false);
		}
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		wasDestroyed = true;
		super.onDestroy();
	}
	
	private void initTasksAndClickListener(GoogleMap googleMap) {
		if (wasDestroyed) {
			populateMapTasksMap.putTask(SearchType.AWARE, 		new PopulateMapAwareTask(activity, googleMap, this, sieve) );
			populateMapTasksMap.putTask(SearchType.LOCALIZED, 	new PopulateMapLocalizedTask(activity, googleMap, this, sieve) );

			googleMap.setOnInfoWindowClickListener( 
					new OnInfoWindowClickListener() {
						@Override
						public void onInfoWindowClick(Marker marker) {
							SearchType searchType = activity.getSearchType();
//							Ln.d(searchType);
							PopulateMapTask task = populateMapTasksMap.getTask( searchType );
//							Ln.d(task);
							String id = marker.getId();
							Place place = task.findPlaceByMarkerId(id);
							handleClickForDetails(place);
						}
					}
					);
			wasDestroyed = false;
		}
	}
	protected void handleClickForDetails(Place place) {
		storePlaceForDetailsActivity(place);
		startActivity( new Intent(activity, getDetailsActivityClass()) );
	}
	protected void storePlaceForDetailsActivity(Place place) {
		buffer.set(place);
	}
	protected abstract Class<? extends Activity> getDetailsActivityClass();
	
	protected Location getLocation() {
		return activity.getLocation();
	}
	
	
	public static <extendingUlyssesMapFragment extends UlyssesMapFragment<ListFragmentMarkerable,MarkerShowableMapFragment>, 
			ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
			MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable> 
		extendingUlyssesMapFragment getInstance(FragmentManager fragmentManager, Class<extendingUlyssesMapFragment> clazz) {
			@SuppressWarnings("unchecked")
			extendingUlyssesMapFragment f = (extendingUlyssesMapFragment) fragmentManager.findFragmentByTag("android:switcher:"+R.id.viewpager+":"+getIndex() );
	        if (f==null) {
	        	try {
					f = clazz.newInstance();
				} catch (java.lang.InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
	        }
	        return f;
		}
	private static int getIndex() {
		return INDEX;
	}

	private void zoomOnFirstFixAndPopulate(GoogleMap googleMap, final Location location) {
		LatLng ll = new LatLng( location.getLatitude(),location.getLongitude() );
		CancelableCallback cancellableCallback = new CancelableCallback() {
			@Override
			public void onFinish() {
				SearchType searchType = activity.getSearchType();
//				Ln.d(searchType);
				PopulateMapTask task = populateMapTasksMap.getTask( searchType );
//				Ln.d(task);
				task.execute(location);
			}
			@Override
			public void onCancel() {}
		};
		moveAndZoom(googleMap, ll, ZOOM-1, DURATION, cancellableCallback);
	}

	@Override
	public void showMarker(String id) {
		if (map!=null) {
			final Marker marker = populateMapTasksMap.getTask( activity.getSearchType() ).findMarkerByPlaceEnhancedId(id);
			marker.showInfoWindow();
			final LatLng position = marker.getPosition();
			final int duration = 100;
			int delay = 300;
			new Handler().postDelayed( new Runnable() {
				@Override
				public void run() {
					moveAndZoom(map, position, ZOOM+1, duration, new CancelableCallback() {
						@Override
						public void onFinish() {
							// rezoom first time ?
							if (map.getCameraPosition().zoom!= (ZOOM+1)) {
								moveAndZoom(map, position, ZOOM+1);
							}
						}
						@Override
						public void onCancel() {}
					});
				}
			}, delay);
		}
	}
	
	private void moveAndZoom(GoogleMap map, LatLng position, float zoom) {
		map.animateCamera( CameraUpdateFactory.newLatLngZoom( position, zoom));
	}
	private void moveAndZoom(GoogleMap map, LatLng position, float zoom, int duration, CancelableCallback cancelableCallback) {
		map.animateCamera( CameraUpdateFactory.newLatLngZoom( position, zoom), duration, cancelableCallback);
	}
	
	@Override
	public void updateData(Location... locations) {
		// clear markers and (re)populate
		if (map!=null) {
			Location location = null;
			if (locations!=null && locations.length>0 && locations[0]!=null) {
				location = locations[0];
				Ln.d("using location: "+location);
			} else {
				Ln.d("getting location...");
				location = getLocation();
			}
			zoomOnFirstFixAndPopulate( map, location );
		}
	}

	@Override
	public String getTitle() {
		return TITLE;
	}
}
