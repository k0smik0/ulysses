/*******************************************************************************
 * Copyright (c) 2015 StreetFoodSquare - http://www.streetfoodsquare.org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Massimiliano Leone - second app release: part of SfeListFragment refactoring
 ******************************************************************************/
package net.iubris.ulysses.ui.list.adapter;

import javax.inject.Singleton;
import javax.inject.Inject;

// import roboguice.util.Ln;
import android.app.Activity;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

@Singleton
public class ImageLoaderDisplayer {
	
	private final ImageLoader imageLoader;
	private final DisplayImageOptions thumbOptions;

	@Inject
	public ImageLoaderDisplayer(Activity activity/*, DisplayImageOptionsProvider thumbOptionsProvider*/) {
		this.thumbOptions = 
				new DisplayImageOptions.Builder()
//		.showImageForEmptyUri(R.drawable.splash_screen)
//		.showImageOnFail(R.drawable.splash_screen)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(false)
		.displayer(new RoundedBitmapDisplayer(90))
		.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
		.build();
		
		ImageLoaderConfiguration loaderConf = new ImageLoaderConfiguration
				.Builder(activity)
				.defaultDisplayImageOptions(thumbOptions)
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.build();
		
		/*int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
 
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity)
        		.threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize-1000000))
                .denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(thumbOptions)
//                .enableLogging()
                .build();*/
		
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(loaderConf);
//		imageLoader.init(config);
	}

//	@Override
//	public ImageLoader get() {
//		return imageLoader;
//	}
	
	public void display(String pathImage, ImageView imageView) {
//		Ln.d(imageView);
		imageLoader.displayImage(pathImage, imageView, thumbOptions);
	}
	
	public void display(String pathImage, ImageView imageView, ImageLoadingListener imageLoadingListener) {
//		Ln.d(imageView);
		imageLoader.displayImage(pathImage, imageView, thumbOptions, imageLoadingListener);
	}

}
