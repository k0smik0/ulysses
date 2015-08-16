package net.iubris.ulysses.ui.activity.details;

import java.util.List;

import net.iubris.ulysses.R;
import net.iubris.ulysses.ui.gallery.ImagePagerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragmentGalleryStreetView extends DetailsFragmentBase {
	
//	public static String TAG = "DetailsFragmentGalleryStreetView_To_Change";

	private ViewPager galleryPager;
	private TextView galleryLabelNoPhoto;
	private List<String> photosUrls;

//	private Place place;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_details_gallery_street_view, container, false);
		galleryPager = (ViewPager) rootView.findViewById(R.id.gallery_pager);
		galleryLabelNoPhoto = (TextView) rootView.findViewById(R.id.gallery_label_no_photo);
		
		// TODO add streetview
		
		photosUrls = getPlace().getPhotosUrls();
//		final Details details = placeEnhanced.getDetails();
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		retrievePhotos();
	}
	
	private void retrievePhotos() {
//		List<Photo> photos = place.getPhotos();
		if (photosUrls!=null && photosUrls.size()>0) {
			
			galleryLabelNoPhoto.setVisibility(View.GONE);
			galleryPager.setVisibility(View.VISIBLE);
			
			ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getView().getContext(), photosUrls);
			galleryPager.setAdapter(imagePagerAdapter);
			galleryPager.setCurrentItem( 0 );
			imagePagerAdapter.notifyDataSetChanged();
		} else {
			galleryPager.setVisibility(View.GONE);
		}
	}
}
