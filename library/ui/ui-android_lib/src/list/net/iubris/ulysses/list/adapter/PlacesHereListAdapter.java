/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
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
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.list.adapter;

import java.util.Comparator;

import net.iubris.socrates.model.http.response.data.search.Place;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses_ui.R;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.darrenmowat.imageloader.ImageLoader;

public class PlacesHereListAdapter extends ArrayAdapter<PlaceHere> {

	private final Activity activity;
	private final int textViewResourceId;
	private ImageLoader imageLoader;
//	private AQuery aQuery;

//	@Inject
	public PlacesHereListAdapter(Activity activity, int textViewResourceId) {
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
			if(convertView == null){
				convertView = activity.getLayoutInflater().inflate(textViewResourceId, null, false);

				placeHolder = new PlaceHolder();
				placeHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
				placeHolder.name = (TextView) convertView.findViewById(R.id.name_text_view);
				placeHolder.distance = (TextView) convertView.findViewById(R.id.distance_text_view);
				placeHolder.rating = (TextView) convertView.findViewById(R.id.rating_text_view);
//				placeHolder.compass = (CompassView) convertView.findViewById(R.id.arrow);

				convertView.setTag(placeHolder);
			} else {
				placeHolder = (PlaceHolder) convertView.getTag();
			}

			final PlaceHere placeHere = getItem(position);
			final Place place = placeHere.getPlace();
//Log.d("PlacesHereListAdapter:89",place.getName());			
			placeHolder.name.setText( place.getName() );
			placeHolder.distance.setText( getHumanDistance( placeHere.getDistance() ));
			placeHolder.rating.setText( place.getRating()+"" );
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
	
	private String getHumanDistance(float distance) {
		String unit = "m";
		if (distance > 1000) {
			distance = distance/1000;
			unit = "km";
		}
		String format = String.format("%.2f", distance);
		return format+unit;
	}

	@Override
	public void sort(Comparator<? super PlaceHere> comparator) {
		super.sort(comparator);
		notifyDataSetChanged();
	}
	
	class PlaceHolder {		
		//public ProgressBar progress;	
		public ImageView icon;
		public TextView name;
		public TextView distance;
		public TextView rating;
//		public CompassView compass;
	}
}
