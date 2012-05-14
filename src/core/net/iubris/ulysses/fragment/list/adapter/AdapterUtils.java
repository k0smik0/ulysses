package net.iubris.ulysses.fragment.list.adapter;

import java.util.Comparator;
import java.util.List;

import net.iubris.ulysses.model.PlaceHere;

public class AdapterUtils {
	/**
	 * clean adapter, then populate from list, sort with comparator and notify for the change
	 * 
	 * @param adapterToPopulate 	arrayadapter to populate
	 * @param populatingList 			PlaceHere list used to populate adapter
	 * @param comparator				a PlaceHere comparator to sort adapter
	 */
	public static void clearAndPopulateAdapter(PlacesHereListAdapter adapterToPopulate,List<PlaceHere> populatingList, Comparator<PlaceHere> comparator) {
		adapterToPopulate.clear();
		for (PlaceHere placeHere: populatingList) {
			adapterToPopulate.add(placeHere);
		}
		adapterToPopulate.sort( comparator );
		adapterToPopulate.notifyDataSetChanged();
	}
}
