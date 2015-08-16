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

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

/**
 * heavy inspired to RoboSplashActivity (from RoboGuice): as default, show a splash image for 500ms,<br/>
 * then start main activity; time can be modified overriding protected field 'minDisplayMs'<br/><br/>
 * 
 * Launching main activity, an action intent will be passed: ACTION_POST_SPLASH. You could use to know if you're 
 * really in post_boot.
 * @author Massimiliano Leone - k0smik0
 */
abstract public class SplashActivity
//<HA extends HermesClient<C, HS>, HS extends IHermesService<C>,C>
//</*A extends Activity,*/ HS extends Service & ContainerService<C>,C>
extends Activity {
	
	public static String ACTION_POST_SPLASH = "action_post_splash";
	
//	private HermesSplashDelegate<A,HS,C> splashActivityDelegate;
	
	/**
	 * milliseconds
	 */
	protected int minDisplayMs = 500;

	
	
	/**
	 * ispired by robosplashactivity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(getLayoutResID());
		super.onCreate(savedInstanceState);
		
		
		// old
		/*
		splashActivityDelegate = getDelegate();
		
Log.d("HermesSplashActivity:47",""+splashActivityDelegate);
		
//		new SplashThread<A,HS,C>(this, splashActivityDelegate, wait).start();
		
		splashActivityDelegate.startService();
		splashActivityDelegate.doOtherStuff();
		splashActivityDelegate.startMainActivity();
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finish();
		*/
		
		
//		setContentView(getLayoutResID());
		
		final Application application = getApplication();
		
		final long start = System.currentTimeMillis();
		
		
		
		new Thread(){
			public void run() {
				doOtherStuffinBackground(application);
				final long duration = System.currentTimeMillis() - start;
//Log.d("HermesSplashActivity:92","duration: "+duration);
				if (duration < minDisplayMs) {
//Log.d("HermesSplashActivity:94","sleeping still: "+(minDisplayMs-duration));					
					try {
						Thread.sleep(minDisplayMs - duration);
					} catch (InterruptedException e) {
						Thread.interrupted();
//Log.d("HermesSplashActivity:100","interrupted");
					}
				}
				
//				startHermesService(); // we execute after sleep
				
				startMainActivity();
				finish();				
			};
		}.start();

		
	}
	/*@Override
	protected void onResume() {
		super.onResume();
		
		final Application app = getApplication();
		startHermesService();
		final long start = System.currentTimeMillis();
		new Thread(){
			public void run() {
				doOtherStuffinBackground(app);
//				pause();
				final long duration = System.currentTimeMillis() - start;
Log.d("HermesSplashActivity:92","duration: "+duration);
				if (duration < minDisplayMs) {
Log.d("HermesSplashActivity:94","sleeping still: "+(minDisplayMs-duration));					
					try {
						Thread.sleep(minDisplayMs - duration);
					} catch (InterruptedException e) {
						Thread.interrupted();
Log.d("HermesSplashActivity:100","interrupted");
					}
				}
			};
		}.run();

		startMainActivity();
		finish();
	}*/
	
	/*private void pause() {
		final long duration = System.currentTimeMillis() - start;
		if (duration < minDisplayMs) {
			try {
				Thread.sleep(minDisplayMs - duration);
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
		}
	}*/
	
//	protected abstract HermesSplashDelegate<A,HS,C> getDelegate();
	
	
	
	// ex hermes zone
//	public void startHermesService() {		
//		startService( new Intent(this, getServiceToStart()) );
//	}
	
	public void startMainActivity() {
		final Intent startIntent = new Intent(this,getActivityToStart());
		startIntent.setAction(SplashActivity.ACTION_POST_SPLASH);
		startIntent.setFlags(getLauncherModeForMainActivity());
		startActivity(startIntent);
	}

	/**
	 * use this to start some stuff in background
	 * @param application
	 */
	public void doOtherStuffinBackground(Application application) {}
	
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
	
	
	
	/**
	 * from RoboSplashActivity
	 * @param app
	 */	
	/*
	@Override
	public void doOtherStuffInBackground() {
		//startServiceInBackground();
	}
	
	public void startServiceInBackground() {		
		startService( new Intent(this, splashActivityDelegate.getServiceToStart()) );
	}
	
	public void startMainActivity() {
		final Intent startIntent = new Intent(this,splashActivityDelegate.getActivityToStart());
		startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startIntent);
	}*/
}
