/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * DefaultPlaceTypeAction.java is part of 'Ulysses'
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ulysses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.map.overlay.places;



import net.iubris.hipparcos.overlay.places.PlaceItemizedOverlay;
import net.iubris.hipparcos.overlay.places.PlaceItem;
import net.iubris.ulysses.model.PlaceHere;
import android.graphics.drawable.Drawable;

public class DefaultPlaceTypeAction implements PlaceTypeAction<Void,PlaceItem> {

	private Drawable defaultMarker;	
	
	public DefaultPlaceTypeAction(Drawable defaultMarker) {
		this.defaultMarker = defaultMarker;
	}
	
	/**
	 * as default, add a PlaceItem to overlay using name as title, and an empty string as snippet:<br/>
	 * {@code hereOverlay.addItem( buildItem(placeHere,"", defaultMarker) ); }
	 */
	@Override
	public Void act(PlaceHere placeHere, PlaceItemizedOverlay<PlaceItem> hereOverlay) {
		hereOverlay.addItem( buildItem(placeHere,"", defaultMarker) );
		//buildItemAndAddToOverlay(hereOverlay, placeHere, defaultMarker);
		return null;
	}
		
	/**
	 * return a new PlaceItem whose title is Place.getName() as default  
	 * 
	 * @param placeHere - PlaceHere
	 * @param snippet - String for snippet
	 * @param drawable - Drawable for marker
	 * @return PlaceItem
	 */
	protected PlaceItem buildItem(PlaceHere placeHere, String snippet, Drawable drawable) {
		return new PlaceItem(placeHere.getPlace().getGeometry().getLocation().toGeoPoint(), 
				placeHere.getPlace().getName(), 
				snippet,
				drawable);
	}	
	
	/**
	 * @return empty string as default
	 */
	/*protected String getSnippet() {
		return "";
	}*/
/*

	protected void buildItemAndAddToOverlay(PlaceHere placeHere, 
			PlaceItemizedOverlay<PlaceItem> overlay, 
			 
			String snippet) {
		overlay.addItem( new PlaceItem( placeHere.getPlace().getGeometry().getLocation().toGeoPoint(),
				placeHere.getPlace().getName(), snippet));
	}
	
	public static void buildItemAndAddToOverlay(PlaceItemizedOverlay<PlaceItem> overlay, PlaceHere placeHere, String snippet) {
		overlay.addItem( new PlaceItem( placeHere.getPlace().getGeometry().getLocation().toGeoPoint(),
				placeHere.getPlace().getName(), snippet));
	}
	public static void buildItemAndAddToOverlay(PlaceItemizedOverlay<PlaceItem> overlay, PlaceHere placeHere, String snippet, Drawable drawable) {
		PlaceItem pi = new PlaceItem( placeHere.getPlace().getGeometry().getLocation().toGeoPoint(),
				placeHere.getPlace().getName(), snippet, drawable);
		overlay.addItem(pi);
	}
	public static void buildItemAndAddToOverlay(PlaceItemizedOverlay<PlaceItem> overlay, PlaceHere placeHere, Drawable drawable) {
		PlaceItem pi = new PlaceItem( placeHere.getPlace().getGeometry().getLocation().toGeoPoint(),
				placeHere.getPlace().getName(), "snippet", drawable);
		overlay.addItem(pi);
	}*/
}
