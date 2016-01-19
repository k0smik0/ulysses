package net.iubris.ulysses.ui.tasks.populate.list.localized;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.tasks.populate.list._base.AbstractPopulateListAsyncTask;
import android.app.Activity;
import android.widget.ArrayAdapter;

public class PopulateListLocalizedTask extends AbstractPopulateListAsyncTask {

	@Inject 
	private UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
	public PopulateListLocalizedTask(Activity context, ArrayAdapter<Place> adapter) {
		super(context, adapter);
	}

	@Override
	public List<Place> call() throws Exception {
		return ulyssesLocalizedSearcher.getResult();
	}

}
