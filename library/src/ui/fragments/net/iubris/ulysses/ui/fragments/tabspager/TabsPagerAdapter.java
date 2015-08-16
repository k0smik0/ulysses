/*******************************************************************************
 * Copyright (c) 2015 StreetFoodSquare - http://www.streetfoodsquare.org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     StreetFoodSquare Team - first release API and implementation
 ******************************************************************************/
package net.iubris.ulysses.ui.fragments.tabspager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.iubris.ulysses.ui.fragments._base.Titleable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_TABS = 2;
    
    private List<Fragment> fragments = new ArrayList<Fragment>(NUM_TABS);
    private final List<String> titles = new ArrayList<String>(NUM_TABS);
	
	public TabsPagerAdapter(FragmentManager fm, Fragment... fragmentsToAdd) {
		super(fm);
		
		fragments.addAll(Arrays.asList( fragmentsToAdd) );
        for (Fragment fragment : fragmentsToAdd) {
        	if (fragment instanceof Titleable)
                titles.add( ((Titleable)fragment).getTitle() );
        	else
        		titles.add( fragment.toString() );
        }
	}
	
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return NUM_TABS;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return titles.get(position);
	}
}
