package net.iubris.ulysses.ui.fragments.details;

import net.iubris.ulysses.model.Place;
import android.support.v4.app.Fragment;

public class DetailsFragmentBase extends Fragment {

	private Place place;
	
	public void setPlace(Place place) {
		this.place = place;
	}
	
	protected Place getPlace() {
		return place;
	}
}
