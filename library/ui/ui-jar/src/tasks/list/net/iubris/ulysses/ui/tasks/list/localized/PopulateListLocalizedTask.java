package net.iubris.ulysses.ui.tasks.list.localized;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.ui.tasks.list._base.AbstractPopulateListAsyncTask;
import android.app.Activity;
import android.widget.ArrayAdapter;

public class PopulateListLocalizedTask extends AbstractPopulateListAsyncTask {

	@Inject UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
	public PopulateListLocalizedTask(Activity context,
			ArrayAdapter<PlaceEnhanced> adapter) {
		super(context, adapter);
	}

	@Override
	public List<PlaceEnhanced> call() throws Exception {
		return ulyssesLocalizedSearcher.getResult();
	}

}
