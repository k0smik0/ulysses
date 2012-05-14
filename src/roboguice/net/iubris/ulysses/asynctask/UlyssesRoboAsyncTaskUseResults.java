/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesRoboAsyncTaskUseResults.java is part of ulysses.
 * 
 * ulysses is free software; you can redistribute it and/or modify
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
 * along with ulysses ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.asynctask;

import java.util.List;

import net.iubris.ulysses.controller.UlyssesSearcher;
import net.iubris.ulysses.model.PlaceHere;
import android.content.Context;

import com.google.inject.Inject;

public class UlyssesRoboAsyncTaskUseResults
<US extends UlyssesSearcher> 
extends SearchRoboAsyncTask<List<PlaceHere>>  {
	
	@Inject Connector<HS, US>
	
	protected UlyssesRoboAsyncTaskUseResults(Context context) {
		super(context);
		//Hermes.getController(context, connector);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlaceHere> call() throws Exception {
		return getController().getSearchResult();
	}

	//@Override
	public US getController() {		
		return null;
	}
}
