package com.CMPUT301W14T13.gpscommentlogger.model;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * This class is responsible for returning the user's 
 * current location.
 * 
 * This is done by:
 * 
 * 1) By GPS:
 * 	  Starting a location manager, attach a listener to it
 *    and request a location update from the location listener.
 *    Once a location change is detected, we assign the new location
 *    to the variable gpsLocation.
 * 
 * 2) By network:
 * 	  fill in-----------
 * 
 * 
 * This class is used during attaching location to comments, 
 * as well getting current location for sorting purposes.
 * 
 * @returns Location
 */
public class LocationSelection
{

	private static LocationListener locationListener;
	private static LocationManager locationManager; 
	private static Location gpsLocation;
	
	/**
	 * Constructor
	 */
	public void LocationSelection() {
	}

	/**
	 * This function initiates a location manager
	 * and a listener for our GPS location in order
	 * to be able to pull current GPS location of
	 * phone.
	 * 
	 * This function will constantly update GPS
	 * or network location based on either one's
	 * availability. 
	 * 
	 * This function is called by other methods
	 * that need to retrieve current location.
	 */
	public static void startLocationSelection() {
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {		
			}			
			@Override
			public void onProviderEnabled(String provider) {			
			}			
			@Override
			public void onProviderDisabled(String provider) {			
			}			
			@Override
			public void onLocationChanged(Location location) {
				gpsLocation = location;				
			}
		};
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) 
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); // update current GPS location
		else 
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); // update current network location
	}
	
	/**
	 * This function stops location manager and listener.
	 * 
	 * This function is called after a location is retrieved
	 * and returned, and GPS location pulls are no longer required. 
	 */
	public static void stopLocationSelection() {
		locationManager.removeUpdates(locationListener);
	}

	
	/**
	 * This function is responsible for
	 * returning the user's current location.
	 * 
	 * This function will check for GPS and network
	 * location availability. Preference is given
	 * to returning a more accurate GPS location,
	 * otherwise a network location is returned.
	 * 
	 * The function will
	 * stop location manager and listener,
	 * returns current network/GPS location.
	 * 
	 * @return Location
	 */
	public static Location getLocation() {
		
		stopLocationSelection(); // stop location manager and listener
		
		return gpsLocation;
		
	}

	public static LocationListener getLocationListener(){
		return locationListener;
	}
	
	public static LocationManager getSystemService(String locationService) {
		// TODO Auto-generated method stub
		return null;
	}


}
