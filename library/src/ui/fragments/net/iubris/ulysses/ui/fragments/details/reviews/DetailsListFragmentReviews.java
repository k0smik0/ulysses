package net.iubris.ulysses.ui.fragments.details.reviews;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.model.Place.Review;
import net.iubris.ulysses.search.utils.Buffer;
import net.iubris.ulysses.ui.activity.details.PlaceProvideable;
import roboguice.fragment.RoboListFragment;
import roboguice.util.Ln;
//import roboguice.util.Ln;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailsListFragmentReviews extends RoboListFragment implements PlaceProvideable {

	@Inject
	private Buffer buffer;
	
	private ReviewsAdapter adapter;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Ln.d("onCreateView");
		setListAdapter(adapter);
		return inflater.inflate(R.layout.list, null, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = buildAdapter( getActivity() );
		setupListView( getListView() );
	}
	@Override
	public void onResume() {
		super.onResume();
		final List<Review> reviews = getPlace().getReviews();
		adapter.clear();
		Ln.d("adding "+reviews.size()+" reviews");
		for (Review review : reviews) {
			Ln.d("review:"+review);
		}
		adapter.addAll(reviews);
		adapter.sort( new DescendingTimeReviewsComparator() );
	}
	private class DescendingTimeReviewsComparator implements Comparator<Review>{
		@Override
		public int compare(Review lhs, Review rhs) {
			if (lhs.getTime() < rhs.getTime() ) return 1;
			if (lhs.getTime() > rhs.getTime() ) return -1;
			return 0;
		}
	}
	
	private ReviewsAdapter buildAdapter(Activity activity) {
		final ReviewsAdapter adapter = new ReviewsAdapter(activity, R.layout.list_row_review);
		adapter.setNotifyOnChange(true);
		return adapter;
	}

	private void setupListView(ListView listView) {
		listView.setTextFilterEnabled(true);
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
		// TODO implements
//		listView.setOnItemClickListener( buildOnItemClickListener(listView) );
//		listView.setOnItemLongClickListener( buildOnLongClickListener() );
	}
	
	@Override
	public Place getPlace() {
		return buffer.get();
	}
	
	private static class ReviewsAdapter extends ArrayAdapter<Review> {

		private static final String STRING_SPACE = " ";
		
		private final Calendar calendar = Calendar.getInstance();
		private final DateFormat df; 		
		private final Activity activity;
		private final String datePrefix;

		private ReviewHolder holder;
		
		public ReviewsAdapter(Activity activity, int resource) {
			super(activity, resource);
			this.activity = activity;
			final Resources resources = activity.getResources();
			this.datePrefix = resources.getString(R.string.reviews_date_prefix);
			final String datePattern = resources.getString(R.string.reviews_date_pattern);
			this.df = new SimpleDateFormat(datePattern,Locale.getDefault());
			
		}
		
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (!isEmpty()) {
				if (convertView==null) {
					convertView = activity.getLayoutInflater().inflate(R.layout.list_row_review, null, false);
					holder = new ReviewHolder();
					holder.authorName = (TextView) convertView.findViewById(R.id.review_name);
					holder.date = (TextView) convertView.findViewById(R.id.review_time);
					holder.text = (TextView) convertView.findViewById(R.id.review_text);				
					convertView.setTag(holder);
				} else {
					holder = (ReviewHolder) convertView.getTag();
				}
//				HolderStateContext.INSTANCE.handleReviewHolder(holder, convertView, activity);
				
				final Review review = getItem(position);
//				Ln.d("review:"+review);
				
				String authorName = review.getAuthorName();
				if (authorName.contains(STRING_SPACE)) {
					authorName=authorName.split(STRING_SPACE)[0];
				}
				holder.authorName.setText( authorName );
				
				long time = review.getTime();
				Date date = new Date(time);
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
//				Ln.d("year:"+year);
				// not ms?! 
				if (year== 1970) {
					time = review.getTime()*1000;
					date = new Date(time);	
				}
				final String formattedDate = df.format(date);
//				Ln.d("time:"+time+" to date:"+date+" to formattedDate:"+formattedDate);
				holder.date.setText(" - "+datePrefix+" "+formattedDate );
				
				holder.text.setText( review.getText() );
			}
			return convertView;
		}
		
		@Override
		public void sort(Comparator<? super Review> comparator) {
			super.sort(comparator);
			notifyDataSetChanged();
		}
		
		private static class ReviewHolder {
			TextView authorName;
//			Uri authorUri;
			TextView date;
			TextView text;
		}
	
		/*private static enum HolderStateContext {
			INSTANCE;
			
			private HolderState holderState = HolderState.BEFORE;
			
			public void handleReviewHolder(ReviewHolder holder, View convertView, Activity activity) {
				holderState.handleReviewHolder(holder, convertView, activity, holderState);
			}
			
			private static enum HolderState {
				BEFORE {
					@Override
					public void handleReviewHolder(ReviewHolder holder, View convertView, Activity activity, HolderState holderState) {
						convertView = activity.getLayoutInflater().inflate(R.layout.review_list_row, null, false);
						holder = new ReviewHolder();
						Ln.d("new holder:"+holder);
						holder.authorName = (TextView) convertView.findViewById(R.id.review_name);
						Ln.d("authorName:"+holder.authorName);
						holder.date = (TextView) convertView.findViewById(R.id.review_time);
						holder.text = (TextView) convertView.findViewById(R.id.review_text);				
						convertView.setTag(holder);
						holderState = HolderState.AFTER;
					}
				},
				AFTER {
					@Override
					public void handleReviewHolder(ReviewHolder holder, View convertView, Activity activity, HolderState holderState) {
						holder = (ReviewHolder) convertView.getTag();
						Ln.d("holder:"+holder);
					}
				};
				public abstract void handleReviewHolder(ReviewHolder holder, View convertView, Activity activity, HolderState holderState);
			}
		}*/
	}
	

//	@Override
//	public void setPlace(Place place) {
//		this.place = place;
//	}
}
