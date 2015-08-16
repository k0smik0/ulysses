/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * Copyright (c) 2015 StreetFoodSquare - http://www.streetfoodsquare.org.
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     StreetFoodSquare Team - first release API and implementation
 *     Massimiliano Leone - second app release: added comparable features for actionbar sort menu; added support for colored rating bar     
 ******************************************************************************/
package net.iubris.ulysses.ui.list.adapter;




import in.flashbulb.coloredratingbar.ColoredRatingBar;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;
import net.iubris.apollus.ui.list.OnItemClickListener;
import net.iubris.apollus.ui.list.RecyclerViewArrayAdapter;
import net.iubris.apollus.ui.map.MarkerShowable;
import net.iubris.apollus.ui.tabspager.selectable.FragmentSelectable;
import net.iubris.ulysses.R;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import roboguice.RoboGuice;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ListFragmentRecyclerViewAdapter extends 
//	RecyclerView.Adapter<SfeRecyclerViewAdapter.MyViewHolder>
	RecyclerViewArrayAdapter<PlaceEnhanced, ListFragmentRecyclerViewAdapter.ViewHolder>
implements Clickable
{
	private final LayoutInflater inflater;
	
	@Inject
	private ImageLoaderDisplayer imageLoaderDisplayer;
	
	
	private OnItemClickListener onItemClickListener;
	private MarkerShowable markerShowable;
	private FragmentSelectable fragmentSelectable;
	
	public ListFragmentRecyclerViewAdapter(Context context, List<PlaceEnhanced> sfeList) {
		super(sfeList);
		this.inflater = LayoutInflater.from(context);
		RoboGuice.getInjector(context).injectMembersWithoutViews(this);
	}
	
	public List<PlaceEnhanced> getData() {
		return data;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		String pathImage = null;
		
//		StreetFoodEstablishment streetFoodEstablishment = data.get(position);

		/*if (streetFoodEstablishment.getImagesStreetFoodEstablishments().size() != 0) {
			pathImage = "assets://"+ streetFoodEstablishment.getImagesStreetFoodEstablishments().get(0).getImageFile();
		}

		if (pathImage != null) {
			imageLoaderDisplayer.display(pathImage, holder.logoSfe);
		}*/

/*		String businessName = streetFoodEstablishment.getBusinessName();
		holder.businessName.setText(businessName);
		holder.sfeId = streetFoodEstablishment.getId();

		if (data.get(position).getOutletType() != null) {
			holder.outletType.setText(streetFoodEstablishment.getOutletType().getType());
		}
		
		if ( streetFoodEstablishment.hasLocation() ) {
			float distanceInMeters = streetFoodEstablishment.getDistanceFromUserLocation();
			if ( distanceInMeters != StreetFoodEstablishment.DEFAULT_UNBELIEVABLE_DISTANCE ) {
//				Ln.d(businessName+": "+distanceInMeters);
				holder.distance.setVisibility(View.VISIBLE);
				holder.buttonToMap.setVisibility(View.VISIBLE);
				
				if (distanceInMeters < 1000) {
					String trailZero = StringUtil.trailZeroFromMeters( distanceInMeters );
					holder.distance.setText( trailZero+" m" );
				} else {
					String trailZero = StringUtil.trailZeroFromKM( distanceInMeters/1000 );
					holder.distance.setText( trailZero+" km" );
				}
			} else {
//				Ln.d(businessName+": "+distanceInMeters);
				holder.distance.setVisibility(View.GONE);
				holder.buttonToMap.setVisibility(View.GONE);
			}
		} else {
			holder.distance.setVisibility(View.GONE);
			holder.buttonToMap.setVisibility(View.GONE);
		}
		
		if (ratingProvidable.isExistingRating()) {
			Float rating = streetFoodEstablishment.getRating();
			holder.ratingBar.setRating(rating);
			holder.ratingBar.setVisibility(View.VISIBLE);
		} else {
			holder.ratingBar.setVisibility(View.GONE);
		}*/
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
		View view = inflater.inflate(R.layout.list_item, parent, false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	class ViewHolder extends /*RecyclerView.ViewHolder*/ AnimateViewHolder implements View.OnClickListener/*, View.OnLongClickListener*/ {
		Long sfeId;
		ImageView logoSfe;
		TextView businessName;
		TextView outletType;
		
		ColoredRatingBar ratingBar;
		TextView distance;
		ImageButton buttonToMap;

		public ViewHolder(View itemView) {
			super(itemView);
			// RESTORE
//			logoSfe = (ImageView) itemView.findViewById(R.id.list_sfe_image);
//			businessName = (TextView) itemView.findViewById(R.id.business_name_text);
//			outletType = (TextView) itemView.findViewById(R.id.outlet_type_text);
//			ratingBar = (ColoredRatingBar) itemView.findViewById(R.id.list_rating_bar);
//			distance = (TextView) itemView.findViewById(R.id.list_distance);
//			buttonToMap = (ImageButton) itemView.findViewById(R.id.button_list_to_map);
			buttonToMap.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					markerShowable.showMarker(sfeId.intValue());
					fragmentSelectable.setCurrentItem(1, true);
				}
			});
			
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(v, getPosition());
			}
		}
		
		// animation zone - start
		@Override
        public void animateRemoveImpl(ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(1000)
                    .setListener(listener)
                    .start();
        }

        @Override
        public void preAnimateAddImpl() {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void animateAddImpl(ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(1000)
                    .setListener(listener)
                    .start();
        }
        // animation zone - end
	}
	
	@Override
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
	
    @Override
	public void sort(Comparator<? super PlaceEnhanced> comparator) {
		super.sort(comparator);
		notifyDataSetChanged();
	}

//    @Override
	public void setMarkerShowable(MarkerShowable markerShowable, FragmentSelectable fragmentSelectable) {
		this.markerShowable = markerShowable;
		this.fragmentSelectable = fragmentSelectable;		
	}
} 
