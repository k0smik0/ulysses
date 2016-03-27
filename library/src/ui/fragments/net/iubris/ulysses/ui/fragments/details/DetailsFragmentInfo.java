package net.iubris.ulysses.ui.fragments.details;

import in.flashbulb.coloredratingbar.ColoredRatingBar;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.utils.menu.MenuUtils;
import net.iubris.ulysses.utils.misc.PlacesUtils;
import roboguice.RoboGuice;
import roboguice.util.Ln;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
//import roboguice.util.Ln;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailsFragmentInfo extends DetailsFragmentBase {
	
//	public static String TAG = "DetailsFragmentInfo_To_Change";

//	private String reviewsString;

//	private Button gplus;
//	private TextView url;

//	private String internationalPhoneNumber;
//	private String gplusUri;

//	private Holder holder;
	
	private ImageLoadingListenerS imageLoadingListenerS;

	@Inject
	private Sieve sieve;
	
//	@Inject
	private final ImageLoader imageLoader = ImageLoader.getInstance();

	private TextView name;
	private ImageView photo;
	private ProgressBar photoSpinner;
	private TextView address;
	private TextView distance;
	private ColoredRatingBar ratingBar;
	private TextView ratingSuffix;
	private TextView urlLabel;
	
//	private TextView urlNo;
//	private ImageView iconWeb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_details_info, container, false);
		
		// using holder, not working
		/*if (holder==null) {
			holder = new Holder();
			holder.name = (TextView) rootView.findViewById(R.id.name);
			holder.photo = (ImageView) rootView.findViewById(R.id.photo);
			holder.photoSpinner = (ProgressBar) rootView.findViewById(R.id.progressbar_photo);
			holder.address = (TextView) rootView.findViewById(R.id.address);
			holder.distance = (TextView) rootView.findViewById(R.id.distance);
			holder.ratingBar = (ColoredRatingBar) rootView.findViewById(R.id.rating_bar);
			holder.ratingSuffix = (TextView) rootView.findViewById(R.id.rating_text);
			holder.urlLabel = (TextView) rootView.findViewById(R.id.url_label);
//			reviewsString = ((Activity) activity).getResources().getString(R.string.reviews);
			holder.urlNo = (TextView) rootView.findViewById(R.id.url_no);
			holder.iconWeb = (ImageView)rootView.findViewById(R.id.icon_web);
			
			imageLoadingListenerS = new ImageLoadingListenerS(holder.photoSpinner);
		}*/
		name = (TextView) rootView.findViewById(R.id.name);
		photo = (ImageView) rootView.findViewById(R.id.photo);
		photoSpinner = (ProgressBar) rootView.findViewById(R.id.progressbar_photo);
		address = (TextView) rootView.findViewById(R.id.address);
		distance = (TextView) rootView.findViewById(R.id.distance);
		ratingBar = (ColoredRatingBar) rootView.findViewById(R.id.rating_bar);
		ratingSuffix = (TextView) rootView.findViewById(R.id.rating_text);
		urlLabel = (TextView) rootView.findViewById(R.id.url_label);
//		reviewsString = ((Activity) activity).getResources().getString(R.string.reviews);
//		urlNo = (TextView) rootView.findViewById(R.id.url_no);
//		iconWeb = (ImageView)rootView.findViewById(R.id.icon_web);
		
		imageLoadingListenerS = new ImageLoadingListenerS(photoSpinner);
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		RoboGuice.getInjector(getActivity()).injectMembers(this);
	}
	@Override
	public void onResume() {
		super.onResume();
		fillData(photo, name, address, distance, ratingBar, ratingSuffix, urlLabel/*, iconWeb, urlNo*/);
	}
	
	/*private static class Holder {
		private TextView name;
		private ImageView photo;
		private ProgressBar photoSpinner;
		private TextView address;
		private TextView distance;
		private ColoredRatingBar ratingBar;
		private TextView ratingSuffix;
		private TextView urlNo;
		private TextView urlLabel;
		private ImageView iconWeb;		
	}*/
	
	/*@Override
	public void onResume() {
		super.onResume();
		fillHolderData();
	}*/
	private void fillData(ImageView photo, TextView name, TextView address, TextView distance, ColoredRatingBar ratingBar, TextView ratingSuffix, TextView urlLabel/*, ImageView iconWeb, View urlNo*/) {
		final Place place = getPlace();
//		final Place place = placeEnhanced.getPlace();
//		final Details details = placeEnhanced.getDetails();
	
		Ln.d(place.getPlaceName());
	
	//		gplus = (Button) rootView.findViewById(R.id.gplus);
	
		List<String> photosUrl = place.getPhotosUrls();
		if (photosUrl!=null) {
			if (photosUrl.size()>0) {
				String photoUrl = photosUrl.get(0);
				
//				ImageLoader imageLoader = ImageLoader.getInstance();
				Ln.d("ImageLoader:"+imageLoader);
				imageLoader.displayImage( photoUrl, photo, imageLoadingListenerS);
			} else {
				// TODO cache sieve search!
				Bitmap bitmap = sieve.find(place);
				photo.setImageBitmap( bitmap );
//				imageLoader.displayImage(bitmap, icon);
			}
		}
		
		
		name.setText( place.getPlaceName() );
	
		address.setText(PlacesUtils.getUsefulAddress(place.getFormattedAddress(), 2)); // 2 = country, city
	
		distance.setText(PlacesUtils.getFormattedDistance(place.getDistance()));
	
		ratingBar.setRating(place.getRating());
		int reviewsCount = place.getReviewsCount();
		if (reviewsCount > 0) {
			ratingSuffix.setText("(" + reviewsCount /*+reviewsString */+ ")");
		}
	
		try {
			String website = place.getWebsite();
			if (website!=null && !website.isEmpty()) {
				Ln.d(place.getWebsite());
				urlLabel.setText(place.getWebsite());
				urlLabel.setVisibility(View.VISIBLE);
//				iconWeb.setVisibility(View.VISIBLE);
//				urlNo.setVisibility(View.GONE);
			} else {
				urlLabel.setVisibility(View.GONE);
//				iconWeb.setVisibility(View.GONE);
//				urlNo.setVisibility(View.VISIBLE);
			}		
		} catch (NullPointerException e) {
			// e.printStackTrace();
			Ln.d(e.getClass().getSimpleName());
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
		MenuUtils.addCall(menu, getPlace().getInternationalPhoneNumber(), getActivity());
		MenuUtils.addGPlus(menu, getPlace().getPlusUrl(), getActivity());
	}

	private class ImageLoadingListenerS implements ImageLoadingListener {
		private ProgressBar spinner;
		public ImageLoadingListenerS(ProgressBar spinner) {
			this.spinner = spinner;
		}
		@Override
		public void onLoadingCancelled(String arg0, View arg1) {}
		@Override
		public void onLoadingComplete(String url, View view, Bitmap image) {
			((ImageView)view).setImageBitmap(image);
			spinner.setVisibility(View.GONE);
		}
		@Override
		public void onLoadingFailed(String url, View view, FailReason arg2) {
			spinner.setVisibility(View.GONE);
		}
		
		@Override
		public void onLoadingStarted(String ur, View view) {
			spinner.setVisibility(View.VISIBLE);
		}
	};
}
