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
package net.iubris.ulysses.map.asynctask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.iubris.ulysses.asynctask.UlyssesAsyncTask;
import net.iubris.ulysses.map.LocationUtils;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public abstract class PopulateMapAsyncTask extends UlyssesAsyncTask {

	protected static int LATLNG_BOUNDS_PADDING = 20;
	protected final GoogleMap map;
	private final List<Marker> markers;
	private final int mapFragmentResId;
	private Location myLocation;
	private Map<String, PlaceHere> placeByMarkerIdMap;
	
	protected PopulateMapAsyncTask(Context context, GoogleMap map, int mapFragmentResId) {
		super(context);
		this.map = map;
		this.mapFragmentResId = mapFragmentResId;
		markers = new ArrayList<Marker>();
		placeByMarkerIdMap = new HashMap<String, PlaceHere>();
	}
	
	public List<Marker> getMarkers() {
		return markers;
	}
	public PlaceHere findPlaceHereById(String id) {
		return placeByMarkerIdMap.get(id);
	}
	
	public void execute(Location myLocation) {
		this.myLocation = myLocation;
		super.execute();
	}
	
	/*@Override
	protected void onSuccess(List<PlaceHere> placesHere) throws Exception {
		populateOverlayFromResult(placesHere);
	}*/
	
	protected void populateOverlayFromResult(List<PlaceHere> placeHeres) {
		/*
		Builder boundsBuilder = new LatLngBounds.Builder();
		
		for (PlaceHere placeHere: placeHeres) {
			MarkerOptions mo = new MarkerOptions();
			mo
				.position( LocationUtils.locationToLatLng( placeHere.getPlace().getGeometry().getLocation() ) )
				.title( placeHere.getPlace().getName() )
				.snippet( placeHere.getPlace().getRating() +"")
				.icon( BitmapDescriptorFactory.fromBitmap( getSieve().find(placeHere.getPlace()) ) );
			
			Marker marker = map.addMarker(mo);
			markers.add( marker );
			placeByMarkerIdMap.put(marker.getId(), placeHere);
			
			boundsBuilder.include(mo.getPosition());
		}
		
		boundsBuilder.include(LocationUtils.locationToLatLng(myLocation));
		
		LatLngBounds latLngBounds = boundsBuilder.build();
		autoZoom(latLngBounds);
		*/
		Builder boundsBuilder = createBoundsBuilder(placeHeres);		
		
		buildBoundsAndZoom(boundsBuilder);
	}
	
	protected void populateOverlayFromResultsIncludingMyPosition(List<PlaceHere> placeHeres) {
		Builder boundsBuilder = createBoundsBuilder(placeHeres);
		
		boundsBuilder.include(LocationUtils.locationToLatLng(myLocation));
		
		buildBoundsAndZoom(boundsBuilder);
	}
	
	private void buildBoundsAndZoom(Builder boundsBuilder) {
		LatLngBounds latLngBounds = boundsBuilder.build();
		autoZoom(latLngBounds);
	}
	
	private Builder createBoundsBuilder(List<PlaceHere> placeHeres) {
		Builder boundsBuilder = new LatLngBounds.Builder();
		
		for (PlaceHere placeHere: placeHeres) {
			MarkerOptions mo = new MarkerOptions();
			mo
				.position( LocationUtils.locationToLatLng( placeHere.getPlace().getGeometry().getLocation() ) )
				.title( placeHere.getPlace().getName() )
				.snippet( placeHere.getPlace().getRating() +"")
				.icon( BitmapDescriptorFactory.fromBitmap( getSieve().find(placeHere.getPlace()) ) );
			
			Marker marker = map.addMarker(mo);
			markers.add( marker );
			placeByMarkerIdMap.put(marker.getId(), placeHere);
			
			boundsBuilder.include(mo.getPosition());
		}
		
		return boundsBuilder;
	}
	
	protected void clearMarkers() {
		for (Marker m: markers) {
			m.remove();
		}
		markers.clear();
		placeByMarkerIdMap.clear();
	}
	
	protected abstract Sieve getSieve();
	
	protected void autoZoom(final LatLngBounds latLngBounds) {
		final View mapView = ((FragmentActivity)context).getSupportFragmentManager().findFragmentById(mapFragmentResId).getView();
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
					map.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, LATLNG_BOUNDS_PADDING));
				}
			});
		}
	}
}
