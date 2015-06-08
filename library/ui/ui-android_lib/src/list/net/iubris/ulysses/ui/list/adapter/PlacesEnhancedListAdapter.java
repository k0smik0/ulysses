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

import java.util.Comparator;

import net.iubris.socrates.model.http.response.data.search.Place;
import net.iubris.ulysses.R;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.utils.misc.PlacesUtils;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.darrenmowat.imageloader.ImageLoader;

public class PlacesEnhancedListAdapter extends ArrayAdapter<PlaceEnhanced> {

	private final Activity activity;
	private final int textViewResourceId;
	private ImageLoader imageLoader;
//	private AQuery aQuery;

//	@Inject
	public PlacesEnhancedListAdapter(Activity activity, int textViewResourceId) {
		super(activity, textViewResourceId);
		
		this.activity = activity;
		this.textViewResourceId = textViewResourceId;
//		this.aQuery = new AQuery(activity);
		
		imageLoader = ImageLoader.getInstance();
		
//		activity.setProgressBarIndeterminateVisibility(true);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!isEmpty()) {
		
			PlaceHolder placeHolder;
			if(convertView == null) {
				convertView = activity.getLayoutInflater().inflate(textViewResourceId, null, false);

				placeHolder = new PlaceHolder();
				placeHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
				
				placeHolder.name = (TextView) convertView.findViewById(R.id.name);
				placeHolder.address = (TextView) convertView.findViewById(R.id.address);
				placeHolder.distance = (TextView) convertView.findViewById(R.id.distance);
				placeHolder.rating = (RatingBar) convertView.findViewById(R.id.rating_bar);
//				placeHolder.compass = (CompassView) convertView.findViewById(R.id.arrow);

				convertView.setTag(placeHolder);
			} else {
				placeHolder = (PlaceHolder) convertView.getTag();
			}

			final PlaceEnhanced placeHere = getItem(position);
			final Place place = placeHere.getPlace();
//Log.d("PlacesHereListAdapter:89",place.getName());			
			placeHolder.name.setText( place.getName() );
			
			placeHolder.address.setText( PlacesUtils.getUsefulAddress(placeHere.getDetails().getFormattedAddress(), 2)); // 2 = country, city 
			
			placeHolder.distance.setText( PlacesUtils.getUsefulDistance( placeHere.getDistance() ));
			
			placeHolder.rating.setRating(place.getRating());
//			placeHolder.rating.setText( Math.floor(place.getRating()*2*10)+"%" );
//Log.d("PlacesHereListAdapter:93","position: "+position);
			setImage(placeHolder.icon, place);
			if (position == getCount()-1) activity.setProgressBarIndeterminateVisibility(false);
		}		
		return convertView;
	}
	
	protected void setImage(ImageView icon, Place place) {
		imageLoader.setImage(place.getIcon().toString(), icon, activity);
	}
	
	/*
	private void setImage(View convertView, ImageView icon, String iconUrl, int position) {
//		aQuery.recycle(convertView).id( icon ).image(iconUrl,true,true);
		imageLoader.setImage(iconUrl, icon, activity);
		
		// last image => stop bar
		if (position == getCount()-1) activity.setProgressBarIndeterminateVisibility(false);
	}*/
	
	@Override
	public void sort(Comparator<? super PlaceEnhanced> comparator) {
		super.sort(comparator);
		notifyDataSetChanged();
	}
	
	class PlaceHolder {		
		public ImageView icon;
		public TextView name;
		public TextView address;
		public TextView distance;
		public RatingBar rating;
//		public CompassView compass;
	}
}
