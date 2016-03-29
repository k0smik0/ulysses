/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * Buffer.java is part of 'Ratafia'.
 ******************************************************************************/
package net.iubris.ulysses.search.utils;


import javax.inject.Singleton;

import net.iubris.ulysses.model.Place;

@Singleton
public class Buffer {
	private Place place;
	
	public Place get() {
//Ln.d("returning: "+place);
		return place;
	}
	public void set(Place placeEnhanced) {
//Ln.d("setting: "+placeEnhanced);
		this.place = placeEnhanced;
	}
}
