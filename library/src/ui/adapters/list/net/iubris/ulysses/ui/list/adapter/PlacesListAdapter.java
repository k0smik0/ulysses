/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlacesHereListAdapter.java is part of 'Ulysses'.
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
package net.iubris.ulysses.ui.list.adapter;

import in.flashbulb.coloredratingbar.ColoredRatingBar;

import java.util.Comparator;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.fragments.map.MarkerShowable;
import net.iubris.ulysses.ui.fragments.tabspager.selectable.FragmentSelectable;
import net.iubris.ulysses.utils.misc.PlacesUtils;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

//import com.darrenmowat.imageloader.ImageLoader;

public class PlacesListAdapter
//<
////extendingUlyssesMapFragment extends UlyssesMapFragment<ListFragmentMarkerable,MarkerShowableMapFragment>,
////eFS extends Fragment & FragmentSelectable, 
//ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
//MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable>
extends ArrayAdapter<Place> {

	private final Activity activity;
	private final int textViewResourceId;
	private ImageLoader imageLoader;
	private MarkerShowable markerShowable;
//	private AQuery aQuery;
	private FragmentSelectable fragmentSelectable;

	public PlacesListAdapter(Activity activity, int textViewResourceId, 
			Comparator<Place>... comparators) {
		super(activity, textViewResourceId);
		
		this.activity = activity;
		this.textViewResourceId = textViewResourceId;
//		this.aQuery = new AQuery(activity);
		this.imageLoader = ImageLoader.getInstance();
//		activity.setProgressBarIndeterminateVisibility(true);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!isEmpty()) {
			
			final Place place = getItem(position);
//			final Place place = placeHere.getPlace();
		
			PlaceHolder placeHolder;
			if(convertView == null) {
				convertView = activity.getLayoutInflater().inflate(textViewResourceId, null, false);

				placeHolder = new PlaceHolder();
				placeHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
				
				placeHolder.name = (TextView) convertView.findViewById(R.id.name);
				placeHolder.address = (TextView) convertView.findViewById(R.id.address);
				placeHolder.distance = (TextView) convertView.findViewById(R.id.distance);
				placeHolder.ratingBar = (ColoredRatingBar) convertView.findViewById(R.id.rating_bar);
//				placeHolder.compass = (CompassView) convertView.findViewById(R.id.arrow);
				
				placeHolder.buttonToMap = (ImageButton) convertView.findViewById(R.id.button_list_to_map);
				placeHolder.buttonToMap.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick(View v) {
						String placeId = place.getPlaceId();
						Log.d("PlacesEnhancedListAdapter",placeId);
						markerShowable.showMarker( placeId );
						fragmentSelectable.setCurrentItem(1, true);
					}
				});
				convertView.setTag(placeHolder);
			} else {
				placeHolder = (PlaceHolder) convertView.getTag();
			}

			
//Log.d("PlacesHereListAdapter:89",place.getName());	
			placeHolder.name.setText( place.getPlaceName() );
			
			placeHolder.address.setText( PlacesUtils.getUsefulAddress(place.getFormattedAddress(), 2)); // 2 = country, city 
			
			placeHolder.distance.setText( PlacesUtils.getFormattedDistance( place.getDistance() ));
			
			placeHolder.ratingBar.setRating(place.getRating());
//			placeHolder.rating.setText( Math.floor(place.getRating()*2*10)+"%" );
//Log.d("PlacesHereListAdapter:93","position: "+position);
			setImage(placeHolder.icon, place);
			if (position == getCount()-1) activity.setProgressBarIndeterminateVisibility(false);
		}
		return convertView;
	}
	
	protected void setImage(ImageView icon, Place placeEnhanced) {
//		imageLoader.setImage(place.getIcon().toString(), icon, activity);
		imageLoader.displayImage(placeEnhanced.getIcon().toString(), icon);
	}
	
	/*
	private void setImage(View convertView, ImageView icon, String iconUrl, int position) {
//		aQuery.recycle(convertView).id( icon ).image(iconUrl,true,true);
		imageLoader.setImage(iconUrl, icon, activity);
		
		// last image => stop bar
		if (position == getCount()-1) activity.setProgressBarIndeterminateVisibility(false);
	}*/
	
	public void setMarkerShowable(MarkerShowable markerShowable, FragmentSelectable fragmentSelectable) {
		this.markerShowable = markerShowable;
		this.fragmentSelectable = fragmentSelectable;
	}
	
	@Override
	public void sort(Comparator<? super Place> comparator) {
		super.sort(comparator);
		notifyDataSetChanged();
	}
	
	class PlaceHolder {		
		public ImageButton buttonToMap;
		public ImageView icon;
		public TextView name;
		public TextView address;
		public TextView distance;
		public ColoredRatingBar ratingBar;
//		public CompassView compass;
	}
}
