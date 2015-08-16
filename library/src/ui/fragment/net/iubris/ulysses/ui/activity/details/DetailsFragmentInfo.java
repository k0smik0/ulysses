package net.iubris.ulysses.ui.activity.details;

import in.flashbulb.coloredratingbar.ColoredRatingBar;

import java.util.List;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.intentable.IntentUtils;
import net.iubris.ulysses.utils.misc.PlacesUtils;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailsFragmentInfo extends DetailsFragmentBase {
	
//	public static String TAG = "DetailsFragmentInfo_To_Change";

	private TextView name;

	private ImageView photo;
	private TextView address;
	private TextView distance;
	private ColoredRatingBar ratingBar;
	private TextView ratingSuffix;
//	private String reviewsString;

	private Button gplus;
	private TextView url;

	private String internationalPhoneNumber;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_details_info, container, false);
		final Place place = getPlace();
//		final Place place = placeEnhanced.getPlace();
//		final Details details = placeEnhanced.getDetails();
		
//		Ln.d(place);

		name = (TextView) rootView.findViewById(R.id.name);
		photo = (ImageView) rootView.findViewById(R.id.photo);
		address = (TextView) rootView.findViewById(R.id.address);
		distance = (TextView) rootView.findViewById(R.id.distance);
		ratingBar = (ColoredRatingBar) rootView.findViewById(R.id.rating_bar);
		ratingSuffix = (TextView) rootView.findViewById(R.id.rating_text);
//		reviewsString = ((Activity) activity).getResources().getString(R.string.reviews);

		gplus = (Button) rootView.findViewById(R.id.gplus);
		url = (TextView) rootView.findViewById(R.id.url);

		// for menu item call
		internationalPhoneNumber = place.getInternationalPhoneNumber();

		name.setText( place.getPlaceName() );
		
		List<String> photosUrl = place.getPhotosUrls();
		if (photosUrl!=null && photosUrl.size()>0)
			ImageLoader.getInstance().displayImage( photosUrl.get(0), photo);

		address.setText(PlacesUtils.getUsefulAddress(place.getFormattedAddress(), 2)); // 2 = country, city

		distance.setText(PlacesUtils.getFormattedDistance(place.getDistance()));

		ratingBar.setRating(place.getRating());

		
		int reviewsCount = place.getReviewsCount();
		if (reviewsCount > 0)
			ratingSuffix.setText("(" + reviewsCount /*+reviewsString */+ ")");

		try {
			// gplus.setText( details.getUri().toURL().toString() );
			gplus.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(place.getPlacesUri()));
					startActivity(i);
				}
			});
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}

		try {
			url.setText(place.getWebsite());
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
		menu.add(R.string.menu__call).setOnMenuItemClickListener(
			new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					IntentUtils.call(internationalPhoneNumber, getActivity());
					return false;
				}
			});
	}
}
