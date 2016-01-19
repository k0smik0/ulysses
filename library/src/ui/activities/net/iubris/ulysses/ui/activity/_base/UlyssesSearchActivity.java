package net.iubris.ulysses.ui.activity._base;

import net.iubris.apollus2.ui.fragments._base.Updatable;
import net.iubris.apollus2.ui.fragments.list._base.Markerable;
import net.iubris.apollus2.ui.fragments.map._base.MarkerShowable;
import net.iubris.apollus2.ui.fragments.tabspager.activity.ListMapTabsSearchTypableLocatableActivity;
import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.ulysses.search.activity.intentable.SearchTypable;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTask;
import net.iubris.ulysses.tasks.search.localized.SearchLocalizedTask;
import net.iubris.ulysses.ui.tasks._base.SearchType;
import roboguice.util.Ln;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

public abstract class UlyssesSearchActivity<ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable
//, SLT extends SearchLocalizedTask, SAT extends SearchAwareTask
>
extends ListMapTabsSearchTypableLocatableActivity<ListFragmentMarkerable, MarkerShowableMapFragment>
implements /*Searchable,*/ SearchTypable {
	
	protected String query;

	protected SearchType searchType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		handleIntent( getIntent() );
	}

	@Override
	public void search(String locationStringAsQuery) {
		try {
			this.query = locationStringAsQuery;
			getSearchLocalizedTask().execute(locationStringAsQuery);
			setSearchType(SearchType.LOCALIZED);
		} catch (SearchException e) {
			Toast.makeText(this, "search error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

//	?? why this do not compile ?!
	@SuppressWarnings("deprecation")
	@Override
	public void search() {
		getSearchAwareTask().execute();
		setSearchType(SearchType.AWARE);
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		// old
//		if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
//			this.query = intent.getStringExtra(SearchManager.QUERY);
//			search(query);
//		}
		Ln.d("handling intent: "+intent);
		if (intent==null) {
			Ln.d("intent null");
			return;
		}
		String action = intent.getAction();
		if (action==null) {
			Ln.d("action null ?!");
			return;
		}
		if ( action.equals(Intent.ACTION_SEARCH) ) {
//			if(savedSearch == false) {	
				this.query = intent.getStringExtra(SearchManager.QUERY);
//			} else {
//				this.savedSearch = false;
//			}	      
	      // Avvio l'operazione di ricerca in background
	      search(this.query);
	    }
	}
	
	@Override
	protected void onDestroy() {
		Ln.d("onDestroy");
		super.onDestroy();
	}
	
	
	protected abstract SearchLocalizedTask getSearchLocalizedTask();
	protected abstract SearchAwareTask getSearchAwareTask();
	
	@Override
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	@Override
	public SearchType getSearchType() {
		return searchType;
	}
	
	@Override
	protected String getQuery() {
		return query;
	}
}
