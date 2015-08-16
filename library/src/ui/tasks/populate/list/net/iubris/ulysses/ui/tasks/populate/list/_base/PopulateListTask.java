package net.iubris.ulysses.ui.tasks.populate.list._base;

import android.location.Location;
import net.iubris.ulysses.ui.tasks._base.PopulateTask;

public interface PopulateListTask extends PopulateTask {
	public void execute(Location location);
}
