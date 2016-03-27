package net.iubris.ulysses.ui.fragments.details;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.model.Place.Review;
import net.iubris.ulysses.search.utils.Buffer;
import net.iubris.ulysses.ui.activity.details.PlaceProvideable;
import roboguice.fragment.RoboListFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailsFragmentReviews extends RoboListFragment implements PlaceProvideable {

	@Inject
	private Buffer buffer;
	private ReviewsAdapter adapter;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// old ratafia
		setListAdapter(adapter);
		return inflater.inflate(R.layout.list, null, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = buildAdapter( getActivity() );
		setupListView( getListView() );
	
		List<Review> reviews = getPlace().getReviews();
		adapter.clear();
		adapter.addAll(reviews);
	}
	
	private ReviewsAdapter buildAdapter(Activity activity) {
		ReviewsAdapter adapter = new ReviewsAdapter(activity, R.layout.review_list_row);
		adapter.setNotifyOnChange(true);
		return adapter;
	}

	private void setupListView(ListView listView) {
		listView.setTextFilterEnabled(true);
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
//		listView.setOnItemClickListener( buildOnItemClickListener(listView) );
//		listView.setOnItemLongClickListener( buildOnLongClickListener() );
	}
	
	@Override
	public Place getPlace() {
		return buffer.get();
	}
	
	private class ReviewsAdapter extends ArrayAdapter<Review> {
		private ReviewHolder holder;
		private Activity activity;
		
		public ReviewsAdapter(Activity activity, int resource) {
			super(activity, resource);
			this.activity = activity;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (holder==null) {
				convertView = activity.getLayoutInflater().inflate(R.layout.review_list_row, null, false);
				holder.authorName = (TextView) convertView.findViewById(R.id.review_name);
				holder.date = (TextView) convertView.findViewById(R.id.review_time);
				holder.text = (TextView) convertView.findViewById(R.id.review_text);				
				convertView.setTag(holder);
			} else {
				holder = (ReviewHolder) convertView.getTag();
			}
			
			Review review = getItem(position);
			
			holder.authorName.setText( review.getAuthorName() );
			holder.date.setText( new Date(review.getTime()).toLocaleString() );
			holder.text.setText( review.getText() );
			
			return convertView;
		}		
		private class ReviewHolder {
			TextView authorName;
//			Uri authorUri;
			TextView date;
			TextView text;
		}
		
	}
}
