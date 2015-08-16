/*******************************************************************************
 * Copyright (c) 2015 StreetFoodSquare - http://www.streetfoodsquare.org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     StreetFoodSquare Team 	- first release API and implementation
 *     Massimiliano Leone 		- second app release: refactoring needed for new map feature support
 ******************************************************************************/
package net.iubris.ulysses.ui.fragments.tabspager.selectable;

import android.content.Context;
import android.util.AttributeSet;

public class FragmentSelectableViewPager extends android.support.v4.view.ViewPager implements FragmentSelectable {

	public FragmentSelectableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(0x00000000);
	}

	public FragmentSelectableViewPager(Context context) {
		super(context);
		setBackgroundColor(0x00000000);
	}
}
