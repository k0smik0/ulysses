/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UsingSimpleRoboAsyncTask.java is part of 'Ulysses'.
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
package net.iubris.ulysses_demo.activity.main.task;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

final class UsingSimpleRoboAsyncTask extends RoboAsyncTask<List<PlaceEnhanced>> {

	private UlyssesSearcher ulyssesSearcher;
	private Button buttonList;
	private long start;
	
	@Inject
	UsingSimpleRoboAsyncTask(Context context, UlyssesSearcher ulyssesSearcher) {
		super(context);
		this.ulyssesSearcher = ulyssesSearcher;
	}
	
	@Override
	protected void onPreExecute() throws Exception {
		buttonList.setClickable(false);
		start = System.currentTimeMillis();
		Toast.makeText(context, "...searching...", Toast.LENGTH_LONG).show();
	}

	@Override
	public List<PlaceEnhanced> call() throws 
		/*LocationTooNearException, LocationNotSoUsefulException,
		NoNetworkException,
		StillSearchException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException
		, NetworkAwareSearch*/Exception {
		ulyssesSearcher.search();
		return ulyssesSearcher.getResult();
	}

	@Override
	protected void onSuccess(List<PlaceEnhanced> result) throws RuntimeException {
		StringBuilder sb = new StringBuilder();
		sb.append("Ulysses finds "+result.size()+" places:");
		sb.append("\n\n");
		for (PlaceEnhanced r: result) {
			sb.append(r.getPlace().getName());
			sb.append("\n");
		}
		long end = System.currentTimeMillis();
		long delta = (end-start);
		sb.append("\nin: "+delta+" ms");
		buttonList.setClickable(true);
		Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
	}
	/*
	@Override
	protected void onException(NoNetworkException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	@Override
	protected void onException(NetworkAwareSearchException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	
	@Override
	protected void onException(LocationTooNearException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	@Override
	protected void onException(LocationNotSoUsefulException e) throws RuntimeException {
		Utils.showException(e,context);
	}

	@Override
	protected void onException(PlacesRetrievingException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	@Override
	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
//		Log.d("UsingUlyssesAsyncTask", "onException("+e.getClass().getSimpleName()+"); "+e.getMessage());
//		Toast.makeText(context, "the search was successful but returned no results - are you in sibery?", Toast.LENGTH_LONG).show();
		Utils.showException(e,context);
	}
	@Override
	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	
	@Override
	protected void onException(NullPointerException e) throws RuntimeException {
		Utils.showException(e,context);
	}*/
	
	@Override
	protected void onException(Exception e) throws RuntimeException {
		Utils.showException(e,context);
	}

	public void setButtonToHandler(Button buttonList) {
		this.buttonList = buttonList;		
	}
	
	
	
	/*private void showException(Exception exception) {
		Log.d("UsingUlyssesAsyncTask", "onException("+exception.getClass().getSimpleName()+"); "+exception.getMessage());
		Toast.makeText(context, exception.getCause().getMessage(), Toast.LENGTH_LONG).show();
//		e.printStackTrace();
	}*/
}
