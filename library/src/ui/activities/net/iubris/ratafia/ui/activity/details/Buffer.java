/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * Buffer.java is part of 'Ratafia'.
 ******************************************************************************/
package net.iubris.ratafia.ui.activity.details;


import javax.inject.Singleton;

import net.iubris.ulysses.model.Place;
import android.util.Log;

@Singleton
public class Buffer {
	private Place place;
	
	public Place get() {
Log.d("Buffer:12","returning: "+place);
		return place;
	}
	public void set(Place placeEnhanced) {
Log.d("Buffer:16","setting: "+placeEnhanced);
		this.place = placeEnhanced;
	}
}
