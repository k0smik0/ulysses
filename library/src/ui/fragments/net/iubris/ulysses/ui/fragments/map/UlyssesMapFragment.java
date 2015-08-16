package net.iubris.ulysses.ui.fragments.map;



import javax.inject.Inject;

import net.iubris.ratafia.ui.activity.details.Buffer;
import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.fragments._base.Titleable;
import net.iubris.ulysses.ui.fragments._base.Updatable;
import net.iubris.ulysses.ui.fragments.list.Markerable;
import net.iubris.ulysses.ui.fragments.tabspager.activity.ListMapTabsSearchTypableLocatableActivity;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.tasks._base.SearchType;
import net.iubris.ulysses.ui.tasks.populate.map._base.MapTasksMap;
import net.iubris.ulysses.ui.tasks.populate.map.aware.PopulateMapAwareTask;
import net.iubris.ulysses.ui.tasks.populate.map.localized.PopulateMapLocalizedTask;
import roboguice.RoboGuice;
import roboguice.util.Ln;
//import roboguice.util.Ln;
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
	private ListMapTabsSearchTypableLocatableActivity<ListFragmentMarkerable, MarkerShowableMapFragment> activity;
//	private Activity activityContext;

	@Inject private Buffer buffer;
	@Inject private Sieve sieve;
	@Inject private MapTasksMap populateMapTasksMap;

	private boolean wasDestroyed = true;
//	protected Location location;
	
	public UlyssesMapFragment() {
//		ratafiaListFragment.setTitle( resources.getString(R.string.fragment_list_title) );
	}
	
	

	private final OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
		@Override
		public void onMapReady(GoogleMap googleMap) {
			map = googleMap;
			Ln.d(map);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMapToolbarEnabled(true);
			
			initTasksAndClickListener(map);
			
			Location location = getLocation();
//			Ln.d("location: "+location);
			if (location!=null) { // need this for onResume, if screen off etc etc
				zoomOnFirstFix(googleMap, location );
				
				Ln.d("searchType "+activity.getSearchType());
				
	//			String searchActionString = activityContext.getIntent().getAction();
				// TODO restore
				populateMapTasksMap.getTask( activity.getSearchType() ).execute( location );
			}
		}
		private void zoomOnFirstFix(GoogleMap googleMap, Location location) {
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 15f));
		}
	};
//	private SearchType searchType;
	
	/*private final OnInfoWindowClickListener onInfoWindowClickListener = new OnInfoWindowClickListener() {
		@Override
		public void onInfoWindowClick(Marker marker) {
			Place placeHere = populateMapTasksMap.getTask(
					searchTypable.getSearchType()
					).findPlaceEnhancedById(marker.getId());
			buffer.set(placeHere);
			startActivity( new Intent(activityContext, RatafiaDetailsActivity.class) );
		}
	};*/
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		this.activityContext = activity;
		this.activity = (ListMapTabsSearchTypableLocatableActivity<ListFragmentMarkerable, MarkerShowableMapFragment>) activity;
//		this.location = ((Locatable)activity).getLocation();
//		Ln.d(location);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RoboGuice.getInjector(activity).injectMembersWithoutViews(this);
	}
	
	// called when fragment is represented
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RoboGuice.getInjector(activity).injectViewMembers(this);
//		initTasksAndClickListener();
	}
	
	@Override
	public void onStart() {
		super.onStart();
//		updateData();
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
			populateMapTasksMap.putTask(SearchType.AWARE, new PopulateMapAwareTask(activity, googleMap, this, sieve) );
			populateMapTasksMap.putTask(SearchType.LOCALIZED, new PopulateMapLocalizedTask(activity, googleMap, this, sieve) );

			googleMap.setOnInfoWindowClickListener( 
//					onInfoWindowClickListener 
					new OnInfoWindowClickListener() {
						@Override
						public void onInfoWindowClick(Marker marker) {
							Place place = populateMapTasksMap.getTask( activity.getSearchType() ).
									findPlaceByMarkerId(marker.getId());
							handleClickForDetails(place);
						}
					}
					);
//			Ln.d("populated");
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
	
	/*private Class<?> retrieveDetailsActivityClass(String detailsActivity) {
		try {
			Class<?> detailsActivityClass = Class.forName(detailsActivity);
			return detailsActivityClass;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	protected Location getLocation() {
//		Ln.d("getLocation: "+location);
		return activity.getLocation();
	}
	
	
	public static <extendingUlyssesMapFragment extends UlyssesMapFragment<ListFragmentMarkerable,MarkerShowableMapFragment>, 
		ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
		MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable> 
	extendingUlyssesMapFragment getInstance(FragmentManager fragmentManager, Class<extendingUlyssesMapFragment> clazz) {
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
				populateMapTasksMap.getTask( activity.getSearchType() ).execute(location);
			}
			@Override
			public void onCancel() {}
		};
		moveAndZoom(googleMap, ll, ZOOM-1, DURATION, cancellableCallback);
	}

	@Override
	public void showMarker(String id) {
		if (map!=null) {
//			Ln.d(id);
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
	public void updateData() {
		// clear markers and (re)populate
		if (map!=null) {
//			Ln.d("updating data in map");
			Location location = getLocation();
//			Ln.d("location: "+location);
			zoomOnFirstFixAndPopulate( map, location );
		}
//		Ln.d("map: "+map);
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	/*public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	@Override
	public SearchType getSearchType() {
		return searchType;
	}*/
}
