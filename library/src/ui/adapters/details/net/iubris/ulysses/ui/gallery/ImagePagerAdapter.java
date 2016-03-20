package net.iubris.ulysses.ui.gallery;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.iubris.ulysses.R;
import roboguice.util.Ln;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImagePagerAdapter extends /*FragmentStatePagerAdapter*/ PagerAdapter {

	private List<String> imagesURLS;

	private LayoutInflater inflater;

	private TextView counter;
//	private final DisplayImageOptions options;

//	private Picasso picasso;
//	private Context context;
	
//	private List<Drawable> testImages;

	public ImagePagerAdapter(Context context, List<String> imagesURLS) {
		Ln.d("ImagePagerAdapter:"+this);
		this.inflater = LayoutInflater.from(context);
		this.imagesURLS = imagesURLS;
	}
	public ImagePagerAdapter(Context context, TextView counter) {
		this(context, new ArrayList<String>());
		this.counter = counter;
	}
	public ImagePagerAdapter(Context context) {
		this(context, new ArrayList<String>());
		Ln.d("ImagePagerAdapter:"+this);
		// test
//		testImages = new ArrayList<>();
//		testImages.add(context.getResources().getDrawable(R.drawable.star_green));
//		testImages.add(context.getResources().getDrawable(R.drawable.star_black));
//		testImages.add(context.getResources().getDrawable(R.drawable.star_red));
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return imagesURLS.size();
//		return testImages.size();
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		Ln.d("new data!");
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
//		Ln.d("ImagePagerAdapter:instantiateItem");
		View imageLayout = inflater.inflate(R.layout.frame_item_pager_image, container, false);
//		assert imageLayout != null;
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.gallery_image_view);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.gallery_progress_bar);
		
		spinner.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.VISIBLE);
		
		String url = imagesURLS.get(position);
		ImageLoader imageLoader = ImageLoader.getInstance();
//		Ln.d("ImageLoader:"+imageLoader);
		imageLoader.displayImage(url, imageView, new SimpleImageListenerSpinnerable(spinner));
		
		Ln.d("position: "+position);
		String counterText = (position+1)+"/"+imagesURLS.size();
		Ln.d("counterText:"+ counterText);
		counter.setText(counterText);
		
//		ImageLoader.getInstance().displayImage(url, imageView);
		
		/*picasso.load(url).into(imageView, new Callback(){
			@Override
			public void onError() {
				Toast.makeText(context, "error downloading images", Toast.LENGTH_SHORT).show();
				spinner.setVisibility(View.GONE);
			}
			@Override
			public void onSuccess() {
				spinner.setVisibility(View.GONE);
			}
		});*/
		
		// test
//		imageView.setImageDrawable(testImages.get(position));

		container.addView(imageLayout);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) { return view.equals(object); }

	public void setPhotoURLS(String... photosUrls) {
		this.imagesURLS = Arrays.asList(photosUrls);
	}
	

//	@Override
//	public void restoreState(Parcelable state, ClassLoader loader) {}

//	@Override
//	public Parcelable saveState() { return null; }
}