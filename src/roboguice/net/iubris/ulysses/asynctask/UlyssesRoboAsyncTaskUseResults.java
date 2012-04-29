package net.iubris.ulysses.asynctask;

import java.util.List;

import android.app.Activity;


import net.iubris.hermes.client.HermesClient;
import net.iubris.hermes.connector.exception.ControllerUnavailableException;
import net.iubris.hermes.utils.asynctask.HermesRoboAsyncTask;
import net.iubris.ulysses.controller.UlyssesSearcher;
import net.iubris.ulysses.model.PlaceHere;

public class UlyssesRoboAsyncTaskUseResults<HC extends Activity & HermesClient<US>, US extends UlyssesSearcher> 
extends HermesRoboAsyncTask<List<PlaceHere>, HC, US>  {
	
	protected UlyssesRoboAsyncTaskUseResults(HC context) {
		super(context);		
	}

	@Override
	public List<PlaceHere> call() throws ControllerUnavailableException {
		return context.getController().getSearchResult();
	}
}
