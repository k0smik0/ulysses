package net.iubris.ulysses.ui.gallery;


import java.util.List;
import net.iubris.ulysses.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImagePagerAdapter extends PagerAdapter {

	private final List<String> imagesURLS;

	private LayoutInflater inflater;
	private final DisplayImageOptions options;

//	private Picasso picasso;
//	private Context context;

	public ImagePagerAdapter(Context context, List<String> imagesURLS) {
//		this.context = context;
		this.imagesURLS = imagesURLS;
		inflater = LayoutInflater.from(context);

		options = new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(android.R.drawable.alert_dark_frame)
				.resetViewBeforeLoading(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return imagesURLS.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
//		Ln.d("ImagePagerAdapter:instantiateItem");
		View imageLayout = inflater.inflate(R.layout.frame_item_pager_image, container, false);
//		assert imageLayout != null;
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.gallery_image_view);
		final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.gallery_progress_bar);
		
		String url = imagesURLS.get(position);

		ImageLoader.getInstance().displayImage(url, imageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				spinner.setVisibility(View.VISIBLE);
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
				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				Ln.d("completed: "+imageUri);
//				Ln.d(loadedImage);
				spinner.setVisibility(View.GONE);
				ImageLoader.getInstance().displayImage(imageUri, imageView);
			}
		});
		
		spinner.setVisibility(View.VISIBLE);
		
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

		container.addView(imageLayout);
		return imageLayout;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) { return view.equals(object); }

//	@Override
//	public void restoreState(Parcelable state, ClassLoader loader) {}

//	@Override
//	public Parcelable saveState() { return null; }
}