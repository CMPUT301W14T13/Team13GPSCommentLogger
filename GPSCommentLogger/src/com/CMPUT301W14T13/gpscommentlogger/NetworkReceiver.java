package com.CMPUT301W14T13.gpscommentlogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class NetworkReceiver extends BroadcastReceiver
{
	public boolean isConnected = false;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    // if we have or are connecting to the network
	   
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        //do something
	    	Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
	    	isConnected = true;
	    }else {
	    	//do something else
	    	Toast.makeText(context, "Disconnected", Toast.LENGTH_LONG).show();
	    	isConnected = false;
	    }
	    
	}
	


}


