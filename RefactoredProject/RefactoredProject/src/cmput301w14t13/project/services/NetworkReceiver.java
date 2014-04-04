package cmput301w14t13.project.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * This class is constantly listening for changes in the network signal
 * once a change is detected the onReceive method changes the isConnected
 * field to true if we are connected and false if not
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

	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	isConnected = true;
	    }else {
	    	isConnected = false;
	    }	    
	}
}


