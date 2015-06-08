/*******************************************************************************
 * Copyleft LGPL 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * Actionable.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.intentable;

import net.iubris.ulysses.ui.tasks._base.SearchType;



public interface SearchTypable {
	void setSearchType(SearchType searchType);
	SearchType getSearchType();
}
