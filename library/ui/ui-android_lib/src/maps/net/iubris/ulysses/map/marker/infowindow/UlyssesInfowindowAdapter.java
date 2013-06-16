package net.iubris.ulysses.map.marker.infowindow;

import net.iubris.ulysses.R;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;


public class UlyssesInfowindowAdapter implements InfoWindowAdapter{

	private final View contentsView;
	private final InfowindowHolder infowindowHolder;

	public UlyssesInfowindowAdapter(Activity activity){
		contentsView = activity.getLayoutInflater().inflate(R.layout.infowindow, null);
		infowindowHolder = new InfowindowHolder();
		infowindowHolder.title = ((TextView)contentsView.findViewById(R.id.title));
		infowindowHolder.rating = ((RatingBar)contentsView.findViewById(R.id.rating_bar));
		contentsView.setTag(infowindowHolder);
	}
	
	@Override	
	public View getInfoContents(Marker marker) {
//		TextView tvTitle = ((TextView)contentsView.findViewById(R.id.title));
		infowindowHolder.title.setText(marker.getTitle());
//		TextView tvSnippet = ((TextView)contentsView.findViewById(R.id.rating));
		String snippet = marker.getSnippet();
Log.d("UlyssesInfowindowAdapter:33",snippet);
		try {
			infowindowHolder.rating.setRating( Float.parseFloat(marker.getSnippet()) );
		} catch (NumberFormatException e) {
			Log.d("UlyssesInfowindowAdapter:37",e.getMessage());
			infowindowHolder.rating.setRating(0);
			e.printStackTrace();
		}
		return contentsView;
//		return infowindowHolder;
	}
	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}
	
	class InfowindowHolder {
//		public ImageView image;
		public TextView title;
		public RatingBar rating;
	}
}