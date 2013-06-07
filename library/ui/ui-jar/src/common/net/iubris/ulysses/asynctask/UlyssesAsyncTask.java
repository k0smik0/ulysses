package net.iubris.ulysses.asynctask;

import java.util.List;
import java.util.concurrent.Executor;

import net.iubris.diane.asynctask.base.RoboSearchAsyncTask;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import android.content.Context;
import android.os.Handler;

public abstract class UlyssesAsyncTask extends RoboSearchAsyncTask<UlyssesSearcher,Void,Void,List<PlaceHere>> {

	protected UlyssesAsyncTask(Context context, Executor executor) {
		super(context, executor);
	}

	protected UlyssesAsyncTask(Context context, Handler handler, Executor executor) {
		super(context, handler, executor);
	}

	protected UlyssesAsyncTask(Context context, Handler handler) {
		super(context, handler);
	}

	protected UlyssesAsyncTask(Context context) {
		super(context);
	}

}
