/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesInfowindowAdapter.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
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
//			Log.d("UlyssesInfowindowAdapter:37",e.getMessage());
			infowindowHolder.rating.setRating(0);
//			e.printStackTrace();
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
