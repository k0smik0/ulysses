package net.iubris.ulysses.ui.gallery;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SimpleImageListenerSpinnerable extends SimpleImageLoadingListener {
		private ProgressBar spinner;
		public SimpleImageListenerSpinnerable(ProgressBar spinner) {
			this.spinner = spinner;
		}
		
		@Override
		public void onLoadingStarted(String imageUri, View view) {
			spinner.setVisibility(View.VISIBLE);
//			Ln.d("loading");
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
		public void onLoadingComplete(final String imageUri, final View view, final Bitmap loadedImage) {
//			Ln.d("completed: "+imageUri);
//			Ln.d(loadedImage);
			
			/*new Handler().post(new Runnable() {
				@Override
				public void run() {
//					ImageLoader.getInstance().displayImage(imageUri, imageView);
//					((ImageView)view).setImageBitmap(loadedImage);
					imageView.setImageBitmap(loadedImage);
					
					Ln.d("loaded in handler");
				}
			});*/

//			imageView.setImageBitmap(loadedImage);
			((ImageView)view).setImageBitmap(loadedImage);
//			((ImageView)view).setImageBitmap(loadedImage);
			spinner.setVisibility(View.GONE);
//			Ln.d("loaded");
		}
	
}
