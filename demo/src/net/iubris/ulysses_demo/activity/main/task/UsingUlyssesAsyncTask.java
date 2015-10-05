/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UsingUlyssesAsyncTask.java is part of 'Ulysses'.
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

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.polaris.locator.core.provider.LocationProvider;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTask;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UsingUlyssesAsyncTask extends /*UIyssesSearchAsyncTask*/ SearchAwareTask {

	private final UlyssesSearcher ulyssesSearcher;
	private Button buttonList;
	private TextView textView;
	private long start;
	
	@Inject LocationProvider locationProvider;
	
	@Inject
	public UsingUlyssesAsyncTask(Context context, UlyssesSearcher ulyssesSearcher) {
		super(context);
		this.ulyssesSearcher = ulyssesSearcher;
	}
	
	@Override
	protected void onPreExecute() throws Exception {
//Debug.startMethodTracing(Environment.getExternalStorageDirectory().getPath()+"/traces/ulysses__task");
		Toast.makeText(context, "...searching...", Toast.LENGTH_LONG).show();
		buttonList.setClickable(false);
		start = System.currentTimeMillis();
	}

	@Override
	public List<Place> call() throws 
		LocationTooNearException, LocationNotSoUsefulException,
		NoNetworkException,
		StillSearchException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, CacheEmptyException, CacheAwareSearchException
		/*, NetworkAwareSearchException*/ {
		Log.d(getClass().getSimpleName()+":53",""+locationProvider.getLocation());
		ulyssesSearcher.search();
		return ulyssesSearcher.getResult();
	}

	@Override
	protected void onSuccess(List<Place> result) throws RuntimeException {
		StringBuilder sb = new StringBuilder();
		sb.append("Ulysses finds "+result.size()+" places:");
		sb.append("\n\n");
		for (Place r: result) {
			sb.append(r.getPlaceName()+" - "+r.getFormattedAddress());
			sb.append("\n");
		}
		long end = System.currentTimeMillis();
		long delta = (end-start);
		sb.append("\n\nin: "+delta+" ms\n\n");
		buttonList.setClickable(true);
//		Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
		textView.setText( textView.getText()
				+sb.toString());
//Debug.stopMethodTracing();
	}
	
	@Override
	protected void onException(NoNetworkException e) throws RuntimeException {
//		ExceptionUtils.showException(e,textView);
		ExceptionUtils.showException(e,"from cache: \n"+ulyssesSearcher.getResult(), textView,ulyssesSearcher);
	}
	@Override
	protected void onException(NetworkAwareSearchException e) throws RuntimeException {
		textView.setText( textView.getText()
				+"result: "+ulyssesSearcher.getResult().size()+"\n\n");
		ExceptionUtils.showException(e,"no result because the exception:",textView,ulyssesSearcher);
	}
	
	@Override
	protected void onException(LocationTooNearException e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
		buttonList.setClickable(true);
	}
	@Override
	protected void onException(LocationNotSoUsefulException e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
		buttonList.setClickable(true);
	}

//	@Override
	protected void onException(PlacesRetrievingException e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
	}
//	@Override
	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
		/*Log.d("UsingUlyssesAsyncTask", "onException("+e.getClass().getSimpleName()+"); "+e.getMessage());
		Toast.makeText(context, "the search was successful but returned no results - are you in sibery?", Toast.LENGTH_LONG).show();*/
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
	}
//	@Override
	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
	}
	
	@Override
	protected void onException(StillSearchException e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
	}
	
	
	@Override
	protected void onException(NullPointerException e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
		e.printStackTrace();
	}
	
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
		ExceptionUtils.showException(e,textView,ulyssesSearcher);
		e.printStackTrace();
	}

	public void setButtonToHandler(Button buttonList) {
		this.buttonList = buttonList;		
	}

	public void setTextViewToHandler(TextView textView) {
		this.textView = textView;
	}
	
	
	
	/*private void showException(Exception exception) {
		Log.d("UsingUlyssesAsyncTask", "onException("+exception.getClass().getSimpleName()+"); "+exception.getMessage());
		Toast.makeText(context, exception.getCause().getMessage(), Toast.LENGTH_LONG).show();
//		e.printStackTrace();
	}*/
}
