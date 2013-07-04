package net.iubris.ulysses_sample.activity.main.task;

import java.util.List;

import javax.inject.Inject;

import roboguice.util.RoboAsyncTask;

import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

final class UsingSimpleRoboAsyncTask extends RoboAsyncTask<List<PlaceHere>> {

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
	public List<PlaceHere> call() throws 
		/*LocationTooNearException, LocationNotSoUsefulException,
		NoNetworkException,
		StillSearchException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException
		, NetworkAwareSearch*/Exception {
		ulyssesSearcher.search();
		return ulyssesSearcher.getResult();
	}

	@Override
	protected void onSuccess(List<PlaceHere> result) throws RuntimeException {
		StringBuilder sb = new StringBuilder();
		sb.append("Ulysses finds "+result.size()+" places:");
		sb.append("\n\n");
		for (PlaceHere r: result) {
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