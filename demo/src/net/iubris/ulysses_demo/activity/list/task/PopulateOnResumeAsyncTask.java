/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PopulateOnResumeAsyncTask.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses_demo.activity.list.task;

import net.iubris.ulysses.ui.list.adapter.PlacesEnhancedListAdapter;
import net.iubris.ulysses.ui.tasks.list.aware.PopulateListAwareTask;
import net.iubris.ulysses_demo.activity.main.task.ExceptionUtils;
import android.app.Activity;

public class PopulateOnResumeAsyncTask extends PopulateListAwareTask /*AdapterPopulaterTask*/ {
	
//	@Inject private UlyssesSearcher ulyssesSearcher;

	public PopulateOnResumeAsyncTask(Activity context, PlacesEnhancedListAdapter adapter) {
		super(context, adapter);
	}

//	@Override
//	public List<PlaceEnhanced> call() throws Exception {
//		return ulyssesSearcher.getResult();
//	}
	
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
		ExceptionUtils.showException(e, context);		
	}
}
