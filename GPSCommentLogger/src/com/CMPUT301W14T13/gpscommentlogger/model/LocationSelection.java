package com.CMPUT301W14T13.gpscommentlogger.model;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


/**
 * This is where users can pick a location
 */
public class LocationSelection
{
	
	private static Location currentLocation;
	private static LocationListener locationListener;
	
	public static Location getLocation(){
		return currentLocation;
	}
	
	public static LocationListener getLocationListener(){
		return locationListener;
	}
	public static LocationManager getSystemService(String locationService) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
