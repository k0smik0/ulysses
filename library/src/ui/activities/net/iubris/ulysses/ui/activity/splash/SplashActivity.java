/*******************************************************************************
 * 
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * HermesSplashActivity.java is part of 'Hermes'.
 * 
 * 'Hermes' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Hermes' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Hermes' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.ui.activity.splash;

import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * heavy inspired to RoboSplashActivity (from RoboGuice): as default, show a splash image for 500ms,<br/>
 * then start main activity; time can be modified overriding protected field 'minDisplayMs'<br/><br/>
 * 
 * Launching main activity, an action intent will be passed: ACTION_POST_SPLASH. You could use to know if you're 
 * really in post_boot.
 * @author Massimiliano Leone - k0smik0
 */
abstract public class SplashActivity extends Activity {
	
	public static String ACTION_POST_SPLASH = "action_post_splash";
	
	
	/**
	 * milliseconds
	 */
	protected int minDisplayMs = 700;


	private Application application;


	private CountDownLatch countDownLatch;


	private int countDown;

	/**
	 * ispired by robosplashactivity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(getLayoutResID());
		super.onCreate(savedInstanceState);
		
		application = getApplication();
		
		new Thread(){
			public void run() {
				wrapperStuffInBackground(application);
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		doOtherStuffInForeground(application);
		
		if (countDown==0)
			startMainActivity();
		else new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startMainActivity();
			}
		}, minDisplayMs);
	}
	
	protected void startMainActivity() {
		final Intent startIntent = new Intent(this,getActivityToStart());
		startIntent.setAction(SplashActivity.ACTION_POST_SPLASH);
		startIntent.setFlags(getLauncherModeForMainActivity());
		startActivity(startIntent);
		finish();
	}

	private void wrapperStuffInBackground(Application application) {
		countDownLatch = new CountDownLatch(1);
		countDown = 1;
		doOtherStuffinBackground(application);
		countDownLatch.countDown();
		countDown = 0;
	}
	/**
	 * use this to start some stuff in background
	 * @param application
	 */
	protected void doOtherStuffinBackground(Application application) {}
	
	protected void doOtherStuffInForeground(Application application) {}
	
	/**
	 * provide your splash layout 
	 */
	protected abstract int getLayoutResID();
	/**
	 * provide your launcher mode for this activity
	 */
	protected abstract int getLauncherModeForMainActivity();
//	protected abstract Class<HS> getServiceToStart();
	/**
	 * provide main activity to start after this
	 * @return
	 */
	protected abstract Class<? extends Activity> getActivityToStart();
}
