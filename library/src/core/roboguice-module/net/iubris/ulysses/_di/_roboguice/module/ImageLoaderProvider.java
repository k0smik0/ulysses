package net.iubris.ulysses._di._roboguice.module;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import roboguice.util.Ln;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

@Singleton
public class ImageLoaderProvider implements Provider<ImageLoader> {
	
	private final ImageLoader imageLoader;

	@Inject
	public ImageLoaderProvider(Context context) {
		DisplayImageOptions displayImageOptions = 
//				new DisplayImageOptions.Builder().cacheInMemory(true)
//				//.cacheOnDisc(true)
//				.build();
		new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(android.R.drawable.ic_delete)
//				.showImageOnFail(android.R.drawable.alert_dark_frame)
//				.showImageOnFail(android.R.drawable.ic_menu_gallery)
				.resetViewBeforeLoading(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();
		
		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
		.threadPoolSize(4)
//		.denyCacheImageMultipleSizesInMemory()
		// new perhaps not working ?
		.defaultDisplayImageOptions(displayImageOptions)
		.tasksProcessingOrder(QueueProcessingType.FIFO)
		.diskCacheSize(50*1024*1024) // 50mb
		.build();
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(imageLoaderConfiguration);
		Ln.d("ImageLoader:"+imageLoader);
	}

	@Override
	public ImageLoader get() {
		return imageLoader;
	}
}
