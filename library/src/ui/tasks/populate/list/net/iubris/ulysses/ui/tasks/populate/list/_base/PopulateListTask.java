package net.iubris.ulysses.ui.tasks.populate.list._base;

import net.iubris.ulysses.ui.tasks._base.PopulateTask;
import android.location.Location;

public interface PopulateListTask extends PopulateTask {
	public void execute(Location location);
}
