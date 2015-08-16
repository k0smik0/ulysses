package net.iubris.ulysses.ui.tasks.populate.list.aware;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.tasks.populate.list._base.AbstractPopulateListAsyncTask;
import roboguice.util.Ln;
import android.app.Activity;
import android.widget.ArrayAdapter;

public class PopulateListAwareTask extends AbstractPopulateListAsyncTask {

	@Inject UlyssesSearcher ulyssesSearcher;
	
	public PopulateListAwareTask(Activity context, ArrayAdapter<Place> adapter) {
		super(context, adapter);
	}

	@Override
	public List<Place> call() {
		List<Place> result = ulyssesSearcher.getResult();
		Ln.d(result.size());
		return result;
	}
}
