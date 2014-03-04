package com.CMPUT301W14T13.gpscommentlogger.view;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;



public class LocationSelectionView
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
