package net.iubris.ulysses.ui.tasks.list.aware;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.ui.tasks.list._base.AbstractPopulateListAsyncTask;
import roboguice.util.Ln;
import android.app.Activity;
import android.widget.ArrayAdapter;

public class PopulateListAwareTask extends AbstractPopulateListAsyncTask {

	@Inject UlyssesSearcher ulyssesSearcher;
	
	public PopulateListAwareTask(Activity context,
			ArrayAdapter<PlaceEnhanced> adapter) {
		super(context, adapter);
	}

	@Override
	public List<PlaceEnhanced> call() {
		List<PlaceEnhanced> result = ulyssesSearcher.getResult();
		Ln.d(result.size());
		return result;
	}
}
