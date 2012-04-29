package net.iubris.ulysses.fragment.list.adapter;

import java.util.Comparator;
import java.util.List;

import com.androidquery.AQuery;

import net.iubris.socrates.model.data.places.search.Place;
import net.iubris.ulysses.R;
import net.iubris.ulysses.model.PlaceHere;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesHereListAdapter extends ArrayAdapter<PlaceHere> {

	private final FragmentActivity fragmentActivity;
	private final int textViewResourceId;
	private AQuery aQuery;

	public PlacesHereListAdapter(FragmentActivity fragmentActivity, int textViewResourceId, List<PlaceHere> places) {		
		super(fragmentActivity, textViewResourceId,places);
		
		this.fragmentActivity = fragmentActivity;
		this.textViewResourceId = textViewResourceId;
		this.aQuery = new AQuery(fragmentActivity);
		
		fragmentActivity.setProgressBarIndeterminateVisibility(true);				
	}
	public PlacesHereListAdapter(FragmentActivity activity, int textViewResourceId) {
		super(activity, textViewResourceId);
		
		this.fragmentActivity = activity;
		this.textViewResourceId = textViewResourceId;
		this.aQuery = new AQuery(fragmentActivity);
		
		fragmentActivity.setProgressBarIndeterminateVisibility(true);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (!isEmpty()) {			
		
			PlaceHolder placeHolder;
			if(convertView == null){
	            placeHolder = new PlaceHolder();            
	            convertView = fragmentActivity.getLayoutInflater().inflate(textViewResourceId, null, false);
	            
	            placeHolder.icon = (ImageView) convertView.findViewById(R.id.icon);            
				placeHolder.name = (TextView) convertView.findViewById(R.id.name_text_view);
		        placeHolder.distance = (TextView) convertView.findViewById(R.id.distance_text_view);
		        placeHolder.rating = (TextView) convertView.findViewById(R.id.rating_text_view);
//		        placeHolder.compass = (CompassView) convertView.findViewById(R.id.arrow);
		        
	            convertView.setTag(placeHolder);
		    }else{
		        placeHolder = (PlaceHolder) convertView.getTag();
		    }

			final PlaceHere placeHere = getItem(position);
			final Place place = placeHere.getPlace();
			
			placeHolder.name.setText( place.getName() );
			placeHolder.distance.setText(placeHere.getDistance()+"m");
			placeHolder.rating.setText( place.getRating()+"" );
			
			setImage(convertView, placeHolder.icon, place.getIcon(), position);
		}		
		return convertView;
	}
	
	private void setImage(View convertView, ImageView icon, String iconUrl, int position) {
		aQuery.recycle(convertView).id( icon ).image(iconUrl,true,true);
		// last image => stop bar
		if (position == getCount()-1) fragmentActivity.setProgressBarIndeterminateVisibility(false);
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
