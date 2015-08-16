package net.iubris.ulysses.config;

import android.content.Context;
/**
 * values specified in values/voyager.xml<br/>
 * bind as as singleton!
 */
//@Singleton
public class AppData {
	
//	private static final String DEFAULT_LOCATION_EXTRA_KEY = "main_location";
	
//	private final String appPackage;
//	private String locationExtraKey;// = appPackage+".main_location";
//	private final String locationUpdateIntentAction;// = appPackage+"."+KLocator.ACTION_LOCATION_UPDATED;		
	private int accuracyMaxError;// = 150; // meters
	private int splashDisplayTime;// = 300; //ms
	private String detailsActivity;
	
//	@Inject
//	public AppData(Resources resources) {
////		this.appPackage = resources.getString(R.string.app_package);
////		this.locationExtraKey = appPackage+".main_location";
////		this.accuracyMaxError = resources.getInteger(R.integer.accuracy_max_error);
////		this.splashDisplayTime = resources.getInteger(R.integer.display_splash);
////		this.detailsActivity = resources.getString(R.string.details_activity);
//	}
	
	public AppData(Context context) {
//		this.locationExtraKey = context.getPackageName()+"."+DEFAULT_LOCATION_EXTRA_KEY;
//		this.locationUpdateIntentAction = locationUpdateIntentAction;
		this.accuracyMaxError = 150;
		this.splashDisplayTime = 300;
		this.detailsActivity = "DetailsActivity";
	}
	
	
	/**
	 * @param locationExtraKey to be appended to package name
	 * @param accuracyMaxError default is 150m
	 * @param splashDisplayTime default is 300m
	 * @param detailsActivityFullPackageName default is "DetailsActivity"
	 */
	public AppData(/*String appPackage,*/
			Context context, 
//			String locationExtraKey,
//			String locationUpdateIntentAction, 
			int accuracyMaxError, int splashDisplayTime,
			String detailsActivityFullPackageName) {
//		this.appPackage = appPackage;
//		this.locationExtraKey = context.getPackageName()+"."+locationExtraKey;
//		this.locationUpdateIntentAction = locationUpdateIntentAction;
		this.accuracyMaxError = accuracyMaxError;
		this.splashDisplayTime = splashDisplayTime;
		this.detailsActivity = detailsActivityFullPackageName;
	}



//	public final String getAppPackage() {
//		return appPackage;
//	}
	
	

//	public final String getMainLocation() {
//		return locationExtraKey;
//	}

//	public final String getLocationUpdateIntentAction() {
//		return locationUpdateIntentAction;
//	}

//	public String getLocationExtraKey() {
//		return locationExtraKey;
//	}
//
//
//	public void setLocationExtraKey(String locationExtraKey) {
//		this.locationExtraKey = locationExtraKey;
//	}


	public int getSplashDisplayTime() {
		return splashDisplayTime;
	}


	public void setSplashDisplayTime(int splashDisplayTime) {
		this.splashDisplayTime = splashDisplayTime;
	}


	public void setAccuracyMaxError(int accuracyMaxError) {
		this.accuracyMaxError = accuracyMaxError;
	}


	public void setDetailsActivityFullPackageName(String detailsActivity) {
		this.detailsActivity = detailsActivity;
	}


	public final int getAccuracyMaxError() {
		return accuracyMaxError;
	}

	public final int getDisplaySplash() {
		return splashDisplayTime;
	}
	public String getDetailsActivityFullPackageName() {
		return detailsActivity;
	}
//	private boolean isAppInForeground() {
////		RunningTaskInfo runningTaskInfo =   activityManager.getRunningTasks(1).get(0);
////		ComponentName topActivity = runningTaskInfo.topActivity;
////		topActivity.
//		return true;
//	}
}
