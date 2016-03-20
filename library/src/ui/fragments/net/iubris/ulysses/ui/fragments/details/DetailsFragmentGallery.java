package net.iubris.ulysses.ui.fragments.details;

import java.util.List;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.activity.details.StreetViewPanoramaActivity;
import net.iubris.ulysses.ui.gallery.ImagePagerAdapter;
import net.iubris.ulysses.ui.utils.menu.MenuUtils;
import roboguice.util.Ln;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DetailsFragmentGallery extends DetailsFragmentBase {
	
	public static String TAG = "DetailsFragmentGallery";

	// my own
	private ViewPager galleryPager;
	private ImagePagerAdapter imagePagerAdapter;
	
	// external
//	private ImagePager zoomImagesGalleryPager;
//    private SimpleImagePagerAdapter simpleImagePagerAdapter;
	
//	private TextView galleryComments;
	private TextView galleryLabelNoPhoto;
	private List<String> photosUrls;
	
	private Button buttonGetPanorama;
	private Location location;

	private TextView galleryCounter;

//	private ImageView imageviewTest;
	
	public final static String EXTRA_ULYSSES_LOCATION = "EXTRA_ULYSSES_LOCATION";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.fragment_details_gallery, container, false);
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_details_gallery, container, false);
		
		// using my imagepageradapter
		if (galleryPager==null) {
			galleryPager = (ViewPager) rootView.findViewById(R.id.gallery_pager);
//			galleryPager.setPageTransformer(false, new AlphaTransformer());
			galleryPager.setPageTransformer(false, new DepthPageTransformer());
Ln.d("new galleryPager");
		}
		// external
		/*if (zoomImagesGalleryPager==null) {
			zoomImagesGalleryPager = (ImagePager) rootView.findViewById(R.id.zoom_images_gallery_pager);
			zoomImagesGalleryPager.setPageTransformer(false, new DepthPageTransformer());
Ln.d("new galleryPager");
		}*/
		
		galleryCounter = (TextView) rootView.findViewById(R.id.gallery_counter);
//		imageviewTest = (ImageView) rootView.findViewById(R.id.imageview_test);
//		galleryComments = (TextView) rootView.findViewById(R.id.gallery_comments);
		galleryLabelNoPhoto = (TextView) rootView.findViewById(R.id.gallery_label_no_photo);
		
		buttonGetPanorama = (Button) rootView.findViewById(R.id.button_get_panorama);
		buttonGetPanorama.setOnClickListener( buttonPanoramaOnClickListener );
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*if (imagePagerAdapter==null) {
			imagePagerAdapter = 
					new ImagePagerAdapter(getView().getContext());
//					new SimpleImagePagerAdapter(getView().getContext());
			Ln.d("new ImagePagerAdapter");
//			galleryPager.setPageTransformer(false, new DepthPageTransformer());
		}*/
		// using my
		if (imagePagerAdapter==null) {
			imagePagerAdapter = new ImagePagerAdapter(getView().getContext(), galleryCounter);
			galleryPager.setAdapter(imagePagerAdapter);
			Ln.d("new ImagePagerAdapter");
		}
		
		/*if (simpleImagePagerAdapter==null) {
			simpleImagePagerAdapter = buildSimpleImagePagerAdapter();
			Ln.d("new SimpleImagePagerAdapter");
			zoomImagesGalleryPager.setAdapter(simpleImagePagerAdapter);
		}*/
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		if (imagePagerAdapter==null) {
//			imagePagerAdapter = 
////					new ImagePagerAdapter(getView().getContext());
//					new SimpleImagePagerAdapter(getView().getContext());
////			galleryPager.setPageTransformer(false, new DepthPageTransformer());
//		}
		final Place place = getPlace();
		photosUrls = place.getPhotosUrls();
		location = place.getLocation();
		retrievePhotos();
	}
	
	private void retrievePhotos() {
//		List<Photo> photos = place.getPhotos();
		if (photosUrls==null || photosUrls.size()==0) {
//			test
//			galleryPager.setVisibility(View.VISIBLE); // remove
//			Image image = new Image(getActivity());
//			image.setImageResId(R.drawable.ic_launcher);
//			imagePagerAdapter.setImages(new ArrayList<Image>());
//			imagePagerAdapter.addImage(image);
//			imagePagerAdapter.notifyDataSetChanged();
			
//			imagePagerAdapter.setPhotoURLS("R.drawable.ic_launcher");
//			galleryPager.setAdapter(imagePagerAdapter);
//			galleryPager.setCurrentItem( 0 );
//			imagePagerAdapter.notifyDataSetChanged();
//			Ln.d("no photo, added ic_drawer");
//			end test
			
			// TODO restore
			// my own
			galleryPager.setVisibility(View.GONE);
			// external not working
//			zoomImagesGalleryPager.setVisibility(View.GONE);
			galleryLabelNoPhoto.setVisibility(View.VISIBLE);
			buttonGetPanorama.setVisibility(View.VISIBLE);
		}
//		if (photosUrls.size()>0) {
		else {
			Ln.d(getPlace().getPlaceName()+", getting photos: "+photosUrls.size());
			galleryLabelNoPhoto.setVisibility(View.GONE);
			buttonGetPanorama.setVisibility(View.GONE);			
			
			// my own
			galleryPager.setVisibility(View.VISIBLE);
			imagePagerAdapter.setPhotoURLS(photosUrls.toArray(new String[]{}));
			galleryPager.setCurrentItem( 0 );
			imagePagerAdapter.notifyDataSetChanged();
			
			// external not completely working
//			zoomImagesGalleryPager.setVisibility(View.VISIBLE);
//			simpleImagePagerAdapter.setImages(new ArrayList<Image>());
//			for (String photoUrl: photosUrls) {
//				Image image = new Image(getContext());
//				image.setImageUrl(photoUrl);
//				simpleImagePagerAdapter.addImage(image);
//				Ln.d("added image: "+photoUrl);
//				simpleImagePagerAdapter.notifyDataSetChanged();
//			}
			
			// test
//			ImageLoader.getInstance().displayImage(photosUrls.get(0), imageviewTest);
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
	
	OnClickListener buttonPanoramaOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
//			arguments = getArguments();
			Ln.d("clicked for panorama");
	    	Location location = getPlace().getLocation();
	    	
//	    	UlyssesStreetViewPanoramaFragment streetViewFragment = UlyssesStreetViewPanoramaFragment.newInstance(location.getLatitude(), location.getLongitude());
//	    	getFragmentManager().beginTransaction().attach(streetViewFragment).show(streetViewFragment).commit();
	    	
	    	/*UlyssesStreetViewPanoramaFragment ulyssesStreetViewPanoramaFragment = (UlyssesStreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
	    	if (ulyssesStreetViewPanoramaFragment==null) {
	    		Ln.d("ulyssesStreetViewPanoramaFragment is null, instancing");
	    		ulyssesStreetViewPanoramaFragment = UlyssesStreetViewPanoramaFragment.newInstance(location.getLatitude(), location.getLongitude());
	    		getChildFragmentManager().beginTransaction().add(R.id.fragment_streetviewpanorama, ulyssesStreetViewPanoramaFragment).commit();
	    	}
	    	ulyssesStreetViewPanoramaFragment.setLocation(location);*/
	    	
//	    	UlyssesStreetViewPanoramaFragment findFragmentById = (UlyssesStreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.fragment_streetviewpanorama);
//	    	if (findFragmentById ==null)
//	    		findFragmentById = UlyssesStreetViewPanoramaFragment.newInstance(location.getLatitude(), location.getLongitude());
//	    	if (!findFragmentById.isVisible())
//	    		getFragmentManager().beginTransaction().show(findFragmentById).commit();

//	    	 working
	    	Intent intent = new Intent();
	    	intent.putExtra(EXTRA_ULYSSES_LOCATION, location);
	    	intent.setClass(getActivity(), StreetViewPanoramaActivity.class);
	    	startActivity(intent);
		}
	};
	
	
	/*private SimpleImagePagerAdapter buildSimpleImagePagerAdapter() {
		final ProgressBar spinner = (ProgressBar) getActivity().findViewById(R.id.gallery_progress_bar);
		return new SimpleImagePagerAdapter(getView().getContext()) {
			@Override
			protected void tryToGetImageFromInternet(final PhotoView imageView, final int position) {
				
				String url = getImages().get(position).getImageUrl();
				ImageLoader imageLoader = ImageLoader.getInstance();
				Ln.d("ImageLoader:"+imageLoader);
				imageLoader.displayImage(url, imageView, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
//						if (spinner!=null) {
//							spinner.setVisibility(View.VISIBLE);
//						}
						Ln.d("loading");
					}
					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
							case IO_ERROR:
								message = "Input/Output error";
								break;
							case DECODING_ERROR:
								message = "Image can't be decoded";
								break;
							case NETWORK_DENIED:
								message = "Downloads are denied";
								break;
							case OUT_OF_MEMORY:
								message = "Out Of Memory error";
								break;
							case UNKNOWN:
								message = "Unknown error";
								break;
						}
						Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
//						spinner.setVisibility(View.GONE);
					}
					@Override
					public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {
//						Ln.d("completed: "+imageUri);
//						Ln.d(loadedImage);
						
						new Handler().post(new Runnable() {
							@Override
							public void run() {
//								ImageLoader.getInstance().displayImage(imageUri, imageView);
//								((ImageView)view).setImageBitmap(loadedImage);
								
								Image image = new Image(getContext());
								image.setImageUrl(imageUri);
								addImage(position, image, loadedImage);
								imageView.setImageBitmap(loadedImage);
								Ln.d("loaded in handler");
							}
						});

						imageView.setImageBitmap(loadedImage);
//						((ImageView)view).setImageBitmap(loadedImage);
//						spinner.setVisibility(View.GONE);
						Ln.d("loaded");
					}
				});
			}
		};
	}*/
}
