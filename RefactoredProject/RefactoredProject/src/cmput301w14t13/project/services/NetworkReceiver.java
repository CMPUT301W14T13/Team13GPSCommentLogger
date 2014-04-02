package cmput301w14t13.project.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Network receiver constantly checks for network change, once a change
 * has occured we take action inside the onReceive() function.
 * @author nsd
 *
 */
public class NetworkReceiver extends BroadcastReceiver
{
	public static boolean isConnected = false;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    // if we have or are connecting to the network
	   
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	isConnected = true;
	    }else {
	    	isConnected = false;
	    }
	    
	}
	


}


