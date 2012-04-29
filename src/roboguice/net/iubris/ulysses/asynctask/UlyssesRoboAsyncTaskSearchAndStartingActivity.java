package net.iubris.ulysses.asynctask;

import net.iubris.hermes.client.HermesClient;
import net.iubris.ulysses.controller.UlyssesSearcher;
import android.app.Activity;
import android.content.Intent;

public abstract class UlyssesRoboAsyncTaskSearchAndStartingActivity
<HC extends Activity & HermesClient<US>, US extends UlyssesSearcher, A extends Activity> 
extends UlyssesRoboAsyncTaskSearch<HC,US> 
implements ActivityStarter {

	protected Class<A> activityToStartClass;

	protected UlyssesRoboAsyncTaskSearchAndStartingActivity(HC context, Class<A> activityToStartClass) {
		super(context);
		this.activityToStartClass = activityToStartClass;
	}	

	@Override
	public void startNextActivity() {
		context.startActivity( new Intent(context,activityToStartClass) );
	}
}
