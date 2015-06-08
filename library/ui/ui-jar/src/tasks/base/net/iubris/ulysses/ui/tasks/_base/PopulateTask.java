package net.iubris.ulysses.ui.tasks._base;

import java.util.List;
import java.util.concurrent.Callable;

import net.iubris.ulysses.engine.model.PlaceEnhanced;


public interface PopulateTask extends Callable<List<PlaceEnhanced>> {
	void execute();
}
