package net.iubris.ulysses.ui.fragments.list;



import javax.inject.Inject;

import net.iubris.apollus2.ui.activity._base.Refreshable;
import net.iubris.apollus2.ui.fragments._base.Titleable;
import net.iubris.apollus2.ui.fragments._base.Updatable;
import net.iubris.apollus2.ui.fragments.list._base.Markerable;
import net.iubris.apollus2.ui.fragments.map._base.MarkerShowable;
import net.iubris.apollus2.ui.fragments.tabspager.selectable.FragmentSelectable;
import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import net.iubris.ulysses.model.comparators.PlaceComparatorByDiscendingRating;
import net.iubris.ulysses.search.utils.Buffer;
import net.iubris.ulysses.ui.activity._base.UlyssesSearchActivity;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.intentable.IntentUtils;
import net.iubris.ulysses.ui.list.adapter.PlacesListAdapter;
import net.iubris.ulysses.ui.tasks._base.SearchType;
import net.iubris.ulysses.ui.tasks.populate.list._base.ListTasksMap;
import net.iubris.ulysses.ui.tasks.populate.list.aware.PopulateListAwareTask;
import net.iubris.ulysses.ui.tasks.populate.list.localized.PopulateListLocalizedTask;
import net.iubris.ulysses.ui.toast.utils.UIUtils;
import net.iubris.ulysses.ui.utils.menu.MenuUtils;
import roboguice.fragment.RoboListFragment;
import roboguice.util.Ln;
//import roboguice.util.Ln;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.maps.SupportMapFragment;

//@ContentView(R.layout.list)
public abstract class UlyssesResultListFragment
<ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable> 
extends RoboListFragment 
implements Refreshable, /*Searchable,*/ Titleable, /*Clickable,*/ Markerable, Updatable {

	private static final String TITLE = "List";

	protected static final int INDEX = 0;

	private PlacesListAdapter placesAdapter;
	
	private final PlaceComparatorByAscendingDistance placeComparatorByAscendingDistance = new PlaceComparatorByAscendingDistance();
	private final PlaceComparatorByDiscendingRating placeComparatorByDiscendingRating = new PlaceComparatorByDiscendingRating();
	
	@Inject private ListTasksMap listTasksMap;
	@Inject private Sieve sieve;
	@Inject private Buffer buffer;

//	private ListMapTabsSearchTypableLocatableActivity<ListFragmentMarkerable, MarkerShowableMapFragment> activity;
	private UlyssesSearchActivity<ListFragmentMarkerable, MarkerShowableMapFragment> activity;

	private MarkerShowable markerShowable;
	private FragmentSelectable fragmentSelectable;

	@SuppressWarnings("unchecked")
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.activity = (UlyssesSearchActivity<ListFragmentMarkerable, MarkerShowableMapFragment>) context;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
        setHasOptionsMenu(true);
	}
	
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// old ratafia
		setListAdapter(placesAdapter);
		return inflater.inflate(R.layout.list, null, false);
		
		// from streetfoodmapp
		/*View view =  inflater.inflate(R.layout.list, container, false);
		
		emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.my_recycler_view);
		
		ScaleInAnimator animator = new ScaleInAnimator();
		int time = 500;
		animator.setAddDuration(time);
	    animator.setRemoveDuration(time);
	    animator.setMoveDuration(time);
	    animator.setChangeDuration(time);
	    emptyRecyclerView.setItemAnimator(animator);
		emptyRecyclerView.addItemDecoration(new DividerItemDecoration( activity, null));
		
		listFragmentRecyclerViewAdapter = new ListFragmentRecyclerViewAdapter(activity, results);
		listFragmentRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				Intent mIntent = new Intent(activity, Details.class);
				List<Place> sfes = results;
				mIntent.putExtra(Details.INTENT_TAG, sfes.get(position).getId() );
				startActivity(mIntent);
			}
		});
		listFragmentRecyclerViewAdapter.setMarkerShowable(markerShowable, fragmentSelectable);
		
		return view;*/
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
//		if (listener.isDualPane()) {
//            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//	    } else {
//	            getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
//	    }
		super.onActivityCreated(savedInstanceState);
		
		placesAdapter = buildPlaceAdapter( getDetailsActivityClass() );
        
        listTasksMap.putTask(SearchType.AWARE, new PopulateListAwareTask(activity, placesAdapter));
		listTasksMap.putTask(SearchType.LOCALIZED, new PopulateListLocalizedTask(activity, placesAdapter));
		
		// old ratafia
		setupListView( getListView() );
	}
	private PlacesListAdapter buildPlaceAdapter(Class<? extends Activity> clazz) {
//		Ln.d(activity+" "+R.layout.list_row+" "+markerShowable+" "+fragmentSelectable+" "+clazz+" "+buffer);
		PlacesListAdapter placesListAdapter = new PlacesListAdapter(activity, R.layout.list_row_searchresults, markerShowable, fragmentSelectable, clazz, buffer) {
			
//			private boolean isImageSetup;
			@Override
			protected void displayImage(Place url, ImageView icon) {
				// TODO cache sieve search!
				Bitmap bitmap = sieve.find(url);
				icon.setImageBitmap( bitmap );
			}
			
			/*@Override
			protected void getImage(Place place) {
				if (!isImageSetup) {
					Bitmap bitmap = sieve.find(place);
					
					isImageSetup = true;
				}
			}*/
		};
//		placesEnhancedListAdapter.setMarkerShowable(markerShowable, fragmentSelectable);
		placesListAdapter.setNotifyOnChange(true);
		return placesListAdapter;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		updateData();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		MenuUtils.addSearch(menu, this, activity);
		MenuUtils.addRefresh(menu, this);
		MenuUtils.List.addSort(menu, activity, placesAdapter, 
				placeComparatorByAscendingDistance, 
				placeComparatorByDiscendingRating
		);
	}
	
	
	@Override
	public void refresh() {
//		refreshAsyncTask.execute();
		UIUtils.showShortToast("coming soon", activity);
	}
	
//	@Override
//	public void search(String query) {
//		UIUtils.showShortToast("coming soon", activity);
//	}
	
	@Override
	public void updateData(Location... locations) {
		Location location = null;
		if (locations!=null && locations.length>0 && locations[0]!=null) {
			location = locations[0];
		} else {
			Ln.d("getting location...");
			location = activity.getLocation();
		}
		Ln.d("using location: "+location);
		listTasksMap.getTask( activity.getSearchType() ).execute(location);
	}
	
	/*
	 * build zone
	 */
	private void setupListView(ListView listView) {
		listView.setTextFilterEnabled(true);
		listView.setAdapter(placesAdapter);
		listView.setItemsCanFocus(false);
		listView.setOnItemClickListener( buildOnItemClickListener(listView) );
		listView.setOnItemLongClickListener( buildOnLongClickListener() );
	}
	
	private OnItemClickListener buildOnItemClickListener(final ListView listView) {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Place place = (Place) listView.getItemAtPosition(position);
				handleClick(place);
			}
		};
	}
	protected void handleClick(Place place) {
		buffer.set(place);
		startActivity( new Intent(activity, getDetailsActivityClass()) );
	}
	protected abstract Class<? extends Activity> getDetailsActivityClass();
	
	private OnItemLongClickListener buildOnLongClickListener(){
		return new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
				Place place = (Place) adapterView.getItemAtPosition(position);
				handleLongClick(place);
				return false;
			}
		};
	}
	protected void handleLongClick(Place place) {
		IntentUtils.call(place.getInternationalPhoneNumber(), activity);
	}
	
	@Override
	public void setMarkerShowable(MarkerShowable markerShowable, FragmentSelectable fragmentSelectable) {
		this.markerShowable = markerShowable;
		this.fragmentSelectable = fragmentSelectable;
	}
	
	@Override
	public String getTitle() {
		return TITLE;
	}
	
	public static <extendingUlyssesListFragment extends UlyssesResultListFragment<ListFragmentMarkerable,MarkerShowableMapFragment>,
	ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
	MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable>
	extendingUlyssesListFragment getInstance(FragmentManager fragmentManager, Class<extendingUlyssesListFragment> clazz) {
		@SuppressWarnings("unchecked")
		extendingUlyssesListFragment extendingUlyssesListFragment = (extendingUlyssesListFragment) fragmentManager.findFragmentByTag("android:switcher:"+R.id.viewpager+":"+getIndex() );
        if (extendingUlyssesListFragment==null) {
        	try {
				extendingUlyssesListFragment = clazz.newInstance();
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
        return extendingUlyssesListFragment;
	}
	private static int getIndex() {
		return INDEX;
	}
}
