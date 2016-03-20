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
import java.util.List;

import net.iubris.apollus2.ui.fragments.map._base.MarkerShowable;
import net.iubris.apollus2.ui.fragments.tabspager.selectable.FragmentSelectable;
import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.search.utils.Buffer;
import net.iubris.ulysses.utils.misc.PlacesUtils;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

//import com.darrenmowat.imageloader.ImageLoader;

public class PlacesListAdapter extends ArrayAdapter<Place> {

	private final Activity activity;
	private final int textViewResourceId;
	private final ImageLoader imageLoader;

	private final MarkerShowable markerShowable;
	private final FragmentSelectable fragmentSelectable;
	
	private final Buffer buffer;
	private final Class<? extends Activity> detailsActivityClass;
	
//	iconImageLoadingListener = new

	public PlacesListAdapter(Activity activity, int textViewResourceId,
			MarkerShowable markerShowable, FragmentSelectable fragmentSelectable,
			Class<? extends Activity> detailsActivityClass,
			Buffer buffer 
			/*,Comparator<Place>... comparators*/) {
		super(activity, textViewResourceId);
		
		this.activity = activity;
		this.textViewResourceId = textViewResourceId;
		this.markerShowable = markerShowable;
		this.fragmentSelectable = fragmentSelectable;
		this.detailsActivityClass = detailsActivityClass;
		this.buffer = buffer;
//		this.aQuery = new AQuery(activity);
		this.imageLoader = ImageLoader.getInstance();
//		Ln.d("ImageLoader:"+imageLoader);
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
				placeHolder.ratingText = (TextView) convertView.findViewById(R.id.rating_text);
				placeHolder.photoIcon = (ImageView) convertView.findViewById(R.id.hasPhotoIcon);
//				placeHolder.photoIconSpinner = (ProgressBar) convertView.findViewById(R.id.icon_progress_bar);
//				imageLoadingListenerS = new ImageLoadingListenerS(placeHolder.photoIconSpinner);
				
//				placeHolder.compass = (CompassView) convertView.findViewById(R.id.arrow);
				
				placeHolder.buttonToMap = (ImageButton) convertView.findViewById(R.id.button_list_to_map);
				placeHolder.buttonToMap.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick(View v) {
						String placeId = place.getPlaceId();
//						Log.d("PlaceListAdapter",placeId);
						markerShowable.showMarker( placeId );
						fragmentSelectable.setCurrentItem(1, true);
					}
				});
				convertView.setTag(placeHolder);
			} else {
				placeHolder = (PlaceHolder) convertView.getTag();
			}

			
			displayImage(place, placeHolder.icon/*, placeHolder.photoIconSpinner*/);
			
//Log.d("PlacesHereListAdapter:89",place.getName());	
			placeHolder.name.setText( place.getPlaceName() );
			
			placeHolder.address.setText( PlacesUtils.getUsefulAddress(place.getFormattedAddress(), 2)); // 2 = country, city 
			
			double distance = place.getDistance();
			if (distance!=Place.UNREACHABLE_DISTANCE && distance!=0) {
				placeHolder.distance.setText( PlacesUtils.getFormattedDistance( distance ));
			} else {
				placeHolder.distance.setVisibility(View.GONE);
			}
			
			float rating = place.getRating();
			if (rating>0) {
				placeHolder.ratingBar.setVisibility(View.VISIBLE);
				placeHolder.ratingBar.setRating( rating );
				placeHolder.ratingText.setVisibility(View.VISIBLE);
				placeHolder.ratingText.setText( "("+place.getReviewsCount()+")" );
			} else { 
				placeHolder.ratingBar.setVisibility(View.GONE);
				placeHolder.ratingText.setVisibility(View.GONE);
				placeHolder.ratingText.setText( "" );
			}
			
			List<String> photosUrls = place.getPhotosUrls();
			if (photosUrls == null || photosUrls.size()<1) {
				placeHolder.photoIcon.setVisibility(View.GONE);
//				ImageLoader.getInstance().
			} 
			if (photosUrls != null && photosUrls.size()>0) {
				placeHolder.photoIcon.setVisibility(View.VISIBLE);
//				Ln.d(place.getPlaceName()+" "+place.getPhotosUrls().size()+" photos");				
			}
			
//			placeHolder.rating.setText( Math.floor(place.getRating()*2*10)+"%" );
//Log.d("PlacesHereListAdapter:93","position: "+position);
			
			if (position == getCount()-1) activity.setProgressBarIndeterminateVisibility(false);
			
			setOnClickListener(place, placeHolder.name, placeHolder.address, placeHolder.distance, placeHolder.ratingBar, placeHolder.icon, placeHolder.photoIcon);
		}
		return convertView;
	}
	
	protected void displayImage(Place place, ImageView icon/*, ProgressBar spinner*/) {
		imageLoader.displayImage(place.getIcon().toString(), icon);
//		this.image = place.getIcon().toString();
	}
	
//	private void displayImage(ImageView icon) {
//		imageLoader.displayImage(getImage(), icon);
//	}
	
	
	private void setOnClickListener(final Place place, View... views) {
		for (View view : views) {
			view.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					handleClick(place);
				}
			});
		}
	}
	private void handleClick(Place place) {
		buffer.set(place);
		activity.startActivity( new Intent(activity, detailsActivityClass));
	}
	
	/*
	private void setImage(View convertView, ImageView icon, String iconUrl, int position) {
//		aQuery.recycle(convertView).id( icon ).image(iconUrl,true,true);
		imageLoader.setImage(iconUrl, icon, activity);
		
		// last image => stop bar
		if (position == getCount()-1) activity.setProgressBarIndeterminateVisibility(false);
	}*/
	
//	public void setMarkerShowable(MarkerShowable markerShowable, FragmentSelectable fragmentSelectable) {
//		this.markerShowable = markerShowable;
//		this.fragmentSelectable = fragmentSelectable;
//	}
	
	@Override
	public void sort(Comparator<? super Place> comparator) {
		super.sort(comparator);
		notifyDataSetChanged();
	}
	
	class PlaceHolder {		
//		public ProgressBar photoIconSpinner;
		public ImageView photoIcon;
		public TextView ratingText;
		public ImageButton buttonToMap;
		public ImageView icon;
		public TextView name;
		public TextView address;
		public TextView distance;
		public ColoredRatingBar ratingBar;
//		public CompassView compass;
	}
}
