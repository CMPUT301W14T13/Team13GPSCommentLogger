package cmput301w14t13.project.services;

import cmput301w14t13.project.models.CommentTree;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * This class is constantly listening for changes in the network signal
 * once a change is detected the onReceive method changes the isConnected
 * field to true if we are connected and false if not.
 * 
 * It is Used to determine if an action the user takes needs to be done locally
 * or through the server. For Example, when creating, editing, Topics or comments,
 * or editing or getting location we need to know if we have a connection.
 * 
 * @author nsd
 *
 */
public class NetworkReceiver extends BroadcastReceiver
{
	public static boolean isConnected = false;

    public static void initialState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        Log.w("IsOnline",Boolean.toString(netInfo != null && netInfo.isConnectedOrConnecting()));
        isConnected = netInfo != null && netInfo.isConnected();
    }
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		Log.w("IsOnline",Boolean.toString(netInfo != null && netInfo.isConnectedOrConnecting()));
	   

	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	    	if(isConnected == false)
	    	{
	    		isConnected = true;
	    		try
				{
	    			if(!CommentTree.getInstance().isEmpty())
	    			{
		    			String text = "You have acquired an internet connection and have switched to online mode"; 
		    			int duration = Toast.LENGTH_LONG;
		    			Toast toast = Toast.makeText(context, text, duration);
		    			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		    			toast.show();
						CommentTree.getInstance().refresh();
	    			}
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
	    	}
	    }else {

	    	if(isConnected == true)
	    	{
		    	isConnected = false;
	    		try
				{
	    			if(!CommentTree.getInstance().isEmpty())
	    			{
		    			String text = "You have lost your internet connection and have switched to offline mode"; 
		    			int duration = Toast.LENGTH_LONG;
		    			Toast toast = Toast.makeText(context, text, duration);
		    			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
		    			toast.show();
						CommentTree.getInstance().refresh();
	    			}
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
	    	}
	    }

	}
}


