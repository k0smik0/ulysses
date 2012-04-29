package net.iubris.ulysses.loader;

import android.content.Context;
import roboguice.content.RoboLoader;

public class UlyssesLoader<Result> extends RoboLoader<Result> {

	public UlyssesLoader(Context context) {
		super(context);
	}

}
