package net.iubris.ulysses.ui.fragments.details;

import java.util.List;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.gallery.ImagePagerAdapter;
import net.iubris.ulysses.ui.utils.menu.MenuUtils;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragmentGalleryStreetView extends DetailsFragmentBase {
	
//	public static String TAG = "DetailsFragmentGalleryStreetView_To_Change";

	private ViewPager galleryPager;
	private TextView galleryLabelNoPhoto;
	private List<String> photosUrls;
	
//	private Button buttonGetPanorama;
	private Location location;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_details_gallery_streetview, container, false);
		galleryPager = (ViewPager) rootView.findViewById(R.id.gallery_pager);
		galleryLabelNoPhoto = (TextView) rootView.findViewById(R.id.gallery_label_no_photo);
		
		
		final Place place = getPlace();
		photosUrls = place.getPhotosUrls();
		
		location = place.getLocation();
		
		// TODO add streetview
/*		buttonGetPanorama = (Button) rootView.findViewById(R.id.button_get_panorama);
//		buttonGetPanorama.setVisibility(View.VISIBLE);
		buttonGetPanorama.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
//				arguments = getArguments();
				Ln.d("clicked for panorama");
		    	Location location = place.getLocation();
		    	
//		    	UlyssesStreetViewPanoramaFragment streetViewFragment = UlyssesStreetViewPanoramaFragment.newInstance(location.getLatitude(), location.getLongitude());
//		    	getFragmentManager().beginTransaction().attach(streetViewFragment).show(streetViewFragment).commit();
		    	
		    	UlyssesStreetViewPanoramaFragment ulyssesStreetViewPanoramaFragment = (UlyssesStreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
		    	if (ulyssesStreetViewPanoramaFragment==null) {
		    		Ln.d("was null");
		    		ulyssesStreetViewPanoramaFragment = UlyssesStreetViewPanoramaFragment.newInstance(location.getLatitude(), location.getLongitude());
		    	}
		    	ulyssesStreetViewPanoramaFragment.setLocation(location);
//		    	getChildFragmentManager().beginTransaction().add(R.id.fragment_streetviewpanorama, ulyssesStreetViewPanoramaFragment).commit();
		    	
//		    	UlyssesStreetViewPanoramaFragment findFragmentById = (UlyssesStreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
//		    	if (findFragmentById ==null)
//		    		findFragmentById = UlyssesStreetViewPanoramaFragment.newInstance(location.getLatitude(), location.getLongitude());
//		    	if (!findFragmentById.isVisible())
//		    		getFragmentManager().beginTransaction().show(findFragmentById).commit();

		    	// working
//		    	Intent intent = new Intent();
//		    	intent.putExtra("location", location);
//		    	intent.setClass(getActivity(), StreetViewPanoramaActivity.class);
//		    	startActivity(intent);
			}
		});*/
		
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
			
//			Ln.d("getting photos: "+photosUrls.size());
//			for (String photoUrl : photosUrls) {
//				Ln.d(photoUrl);
//			}
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuUtils.addPanorama(menu, location, getActivity());
	}
}
