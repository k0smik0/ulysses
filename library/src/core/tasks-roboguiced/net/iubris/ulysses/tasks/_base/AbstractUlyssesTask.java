/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesAsyncTask.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.tasks._base;

import java.util.List;
import java.util.concurrent.Executor;

import net.iubris.etask.roboguiced.RoboEnhancedAsyncTask;
import net.iubris.ulysses.model.Place;
import android.content.Context;
import android.os.Handler;

/**
 * for task not searching
 */
public abstract class AbstractUlyssesTask extends RoboEnhancedAsyncTask<List<Place>> {

	protected AbstractUlyssesTask(Context context, Executor executor) {
		super(context, executor);
	}

	protected AbstractUlyssesTask(Context context, Handler handler, Executor executor) {
		super(context, handler, executor);
	}

	protected AbstractUlyssesTask(Context context, Handler handler) {
		super(context, handler);
	}

	protected AbstractUlyssesTask(Context context) {
		super(context);
	}
}
