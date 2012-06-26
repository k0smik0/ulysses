/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlaceTypeActionFilter.java is part of 'Ulysses'
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



import net.iubris.eratosthenes.collections.filters.ActionFilter;
import net.iubris.hipparcos.overlay.places.PlaceItem;
import net.iubris.hipparcos.overlay.places.PlaceItemizedOverlay;
import net.iubris.socrates.model.data.places.PlaceType;
import net.iubris.ulysses.model.PlaceHere;

public class PlaceTypeActionFilter implements ActionFilter<PlaceHere, Void> {
	
	protected final PlaceItemizedOverlay<PlaceItem> placeItemizedOverlay;
	protected final PlaceType placeType;
	
	public PlaceTypeActionFilter(PlaceItemizedOverlay<PlaceItem> placeItemizedOverlay, 
			PlaceType placeType) {
		this.placeItemizedOverlay = placeItemizedOverlay;
		this.placeType = placeType;
	}
	
	@Override
	public boolean matchOn(PlaceHere placeHere) {
		if (placeHere.getPlace().getTypes().contains(placeType)) {
//Ln.d(placeHere.getPlace().getName() +"("+placeHere.getPlace().getTypes()+")" +" match " + placeType);
			return true; 
		}
		return false;
	}
	
	@Override
	public Void act(PlaceHere e) {
//Ln.d("try to adding: "+e.getPlace().getName() +" to "+placeItemizedOverlay);		
		if (matchOn(e)) {
			placeItemizedOverlay.addItem( buildPlaceItem(e,e.getPlace().getName() ) );
			//return true;
		}
		return null;
	}
	
	public PlaceItemizedOverlay<PlaceItem> getOverlay() {
		return placeItemizedOverlay;
	}
	
	public static PlaceItem buildPlaceItem(PlaceHere placeHere, String snippet) {
		return new PlaceItem( placeHere.getPlace().getGeometry().getLocation().toGeoPoint(),
				placeHere.getPlace().getName(), snippet);
	}
	
	@Override
	public String toString() {		
		return "PlaceTypeActionFilter for: "+placeType + " on "+placeItemizedOverlay;
	}
}
