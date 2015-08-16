package net.iubris.ulysses.ui.fragments.details;

import net.iubris.ulysses.model.Place;
import android.app.Activity;
import android.support.v4.app.Fragment;

public class DetailsFragmentBase extends Fragment {

	private Place place;
//	private String key;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	public void setPlace(Place place) {
		this.place = place;
	}
//	public void setGoogleKey(String key) {
//		this.key = key;
//	}
	
	protected Place getPlace() {
		return place;
	}
	
	/*protected String buildPhotoUrl(String photoReference) {
		String url = "https://maps.googleapis.com/maps/api/place/photo?"
			+"photoreference="+photoReference
			+"&maxwidth="+256
			+"&key="+key;
		return url;
	}*/
}
