package net.iubris.ulysses.ui.tasks._base;

import java.util.List;
import java.util.concurrent.Callable;

import net.iubris.ulysses.model.Place;


public interface PopulateTask extends Callable<List<Place>> {
	void execute();
}
