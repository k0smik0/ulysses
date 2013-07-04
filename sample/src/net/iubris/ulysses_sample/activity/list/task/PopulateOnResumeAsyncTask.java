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
package net.iubris.ulysses_sample.activity.list.task;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.list.adapter.asynctask.AdapterPopulaterAsyncTask;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import android.app.Activity;

public class PopulateOnResumeAsyncTask extends AdapterPopulaterAsyncTask {
	@Inject private UlyssesSearcher ulyssesSearcher;

	public PopulateOnResumeAsyncTask(Activity context, PlacesHereListAdapter adapter) {
		super(context, adapter);
	}

	@Override
	public List<PlaceHere> call() throws Exception {
		return ulyssesSearcher.getResult();
	}
}
