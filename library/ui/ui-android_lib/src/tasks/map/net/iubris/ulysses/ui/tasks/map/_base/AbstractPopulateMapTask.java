/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PopulateMapAsyncTask.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.ui.tasks.map._base;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.tasks._base.AbstractUlyssesTask;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.map.marker.infowindow.UlyssesInfowindowAdapter;
import net.iubris.ulysses.ui.tasks.map._utils.LocationUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * abstract class for populate map with search result 
 *
 */
public abstract class AbstractPopulateMapTask extends AbstractUlyssesTask implements PopulateMapTask {

	protected static int LATLNG_BOUNDS_PADDING = 23;
	protected final GoogleMap map;
	protected final List<Marker> markers;
	protected final Fragment mapFragment;
	protected final Map<String, PlaceEnhanced> placeByMarkerIdMap;
	protected final Activity activity;
	
	
//	@Inject private MarkerBitmapProvider markerBitmapProvider;
	
	/*@Inject */private final Sieve sieve;
	
	protected AbstractPopulateMapTask(Activity activity, GoogleMap map, Fragment mapFragment, Sieve sieve) {
		super(activity);
		this.activity = activity;
		this.map = map;
		this.mapFragment = mapFragment;
		this.sieve = sieve;
		this.markers = new ArrayList<Marker>();
		this.placeByMarkerIdMap = new HashMap<String, PlaceEnhanced>();
	}
	
	@Override
	public List<Marker> getMarkers() {
		return markers;
	}
	@Override
	public PlaceEnhanced findPlaceEnhancedById(String id) {
		return placeByMarkerIdMap.get(id);
	}
	
	protected void populateOverlayFromResult(List<PlaceEnhanced> placeHeres) {
		Builder boundsBuilder = createBoundsBuilder(placeHeres);
		buildBoundsAndZoom(boundsBuilder);
	}
	private Builder createBoundsBuilder(final List<PlaceEnhanced> placeHeres) {
		final Builder boundsBuilder = new LatLngBounds.Builder();
		
//		Handler h = new Handler();
//		
//		Runnable r = new Runnable() {
//			@Override
//			public void run() {
		
				for (PlaceEnhanced placeHere: placeHeres) {
					MarkerOptions markerOptions = new MarkerOptions();
					
		//			Bitmap bitmap = getSieve().find(placeHere.getPlace());
					Bitmap bitmap = sieve.find(placeHere.getPlace());
					
					markerOptions.position( LocationUtils.locationToLatLng( placeHere.getPlace().getGeometry().getLocation() ) )
						.title( placeHere.getPlace().getName() )
						.snippet( placeHere.getPlace().getRating() +"")
//						.draggable(false)
						.icon( BitmapDescriptorFactory.fromBitmap( bitmap ) );
					
		//			markerOptions.visible(false);
					
					Marker marker = map.addMarker(markerOptions);
		//			dropPinEffect( marker, new LatLngInterpolator.Linear() );
//					try { Thread.sleep(200); } catch (InterruptedException e) {}
					
					markers.add( marker );
					placeByMarkerIdMap.put(marker.getId(), placeHere);
					
					boundsBuilder.include(markerOptions.getPosition());
				}
//			}
//		};
		
//		handler.postDelayed(r, 500);
		
		return boundsBuilder;
	}
	protected void buildBoundsAndZoom(Builder boundsBuilder) {
		LatLngBounds latLngBounds = boundsBuilder.build();
//		map.animateCamera( CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(), 8));
		autoZoom(latLngBounds);
	}

	/*private void dropPinEffect(final Marker marker, final LatLngInterpolator latLngInterpolator) {
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		final long duration = 1500;
		final Interpolator interpolator = new BounceInterpolator();
		handler.post(new Runnable() {
			@Override
			public void run() {
				marker.setVisible(true);
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = Math.max(1 - interpolator.getInterpolation((float) elapsed / duration), 0);
				t = elapsed / duration;
//				marker.setAnchor(0.5f, 1.0f + 14 * t);
//				LatLng position = marker.getPosition();
				LatLng finalPosition = marker.getPosition();
				LatLng startPosition = new LatLng( finalPosition.latitude+0.00001, finalPosition.longitude) ;
				marker.setPosition( latLngInterpolator.interpolate(interpolator.getInterpolation(t), 
						startPosition, finalPosition ));
				if (t < duration) { // Post again 15ms later.
					handler.postDelayed(this, 15);
				} else {
					marker.showInfoWindow();
				}
			}
		});
	}*/
	
	/*public interface LatLngInterpolator {
	    public LatLng interpolate(float fraction, LatLng a, LatLng b);
	 
	    public class Linear implements LatLngInterpolator {
	        @Override
	        public LatLng interpolate(float fraction, LatLng a, LatLng b) {
	            double lat = (b.latitude - a.latitude) * fraction + a.latitude;
	            double lng = (b.longitude - a.longitude) * fraction + a.longitude;
	            return new LatLng(lat, lng);
	        }
	    }
	}*/
	
	protected void clearMarkers() {
		for (Marker m: markers) {
			m.remove();
		}
		markers.clear();
		placeByMarkerIdMap.clear();
	}
	
	protected void autoZoom(final LatLngBounds latLngBounds) {
		final View mapView = mapFragment.getView();
		if (mapView.getViewTreeObserver().isAlive()) {
			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation")
				@SuppressLint("NewApi") // We check which build version we are using.
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
						mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					} else {
						mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this); 
					}
					
//					map.animateCamera(CameraUpdateFactory.zoomTo(21.0f));
					map.animateCamera(
							CameraUpdateFactory.newLatLngBounds(latLngBounds, LATLNG_BOUNDS_PADDING)
							, new GoogleMap.CancelableCallback() {
								@Override
								public void onFinish() {
									map.animateCamera(CameraUpdateFactory.zoomTo(21f));
								}
								@Override
								public void onCancel() {}
							}
							);
				}
			});
		}
	}
	
	@Override
	protected void onSuccess(List<PlaceEnhanced> places) throws Exception {
		activity.setProgress(66);
		
		populateOverlayFromResult(places);
		
		activity.setProgress(100);
		activity.setProgressBarVisibility(false);
		
		map.setInfoWindowAdapter( new UlyssesInfowindowAdapter(activity) );
	}
}
