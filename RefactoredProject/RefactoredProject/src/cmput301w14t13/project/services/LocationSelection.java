package cmput301w14t13.project.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


/**
 * This class is responsible for returning the user's  current location. This is done by: 1) By GPS: Starting a location manager, attach a listener to it and request a location update from the location listener. Once a location change is detected, we assign the new location to the variable gpsLocation. 2) By network: fill in----------- This class is used during attaching location to comments,  as well getting current location for sorting purposes.
 * @returns  Location
 */
public class LocationSelection
{

	
	private LocationListener locationListener;
	private LocationManager locationManager; 
	private Location gpsLocation = new Location("default");
	private Context context;


	private static final LocationSelection Instance = new LocationSelection();
	
	private LocationSelection() {

	}
	
	public void registerContext(Context context)
	{
		this.context = context;
	}
	
	
	public static LocationSelection getInstance()
	{
		return Instance;
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
	
	/*activityName.getContext()*/
	
	public void startLocationSelection() {
		Log.d("LocationSelection", "Started location manager and listener");
		
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		Log.d("LocationSelection", "Location manager is " + locationManager.toString());

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
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); // update current GPS location
			Log.d("LocationSelection", "Got GPS Provider");
		}
		else{ 
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); // update current network location
			Log.d("LocationSelection", "Got Network Provider");
		}
		
	}

	/**
	 * This function stops location manager and listener.
	 * 
	 * This function is called after a location is retrieved
	 * and returned, and GPS location pulls are no longer required. 
	 */
	public void stopLocationSelection() {
		locationManager.removeUpdates(locationListener);
		Log.d("LocationSelection", "Stopped location manager and listener");
	}
	
	/**
	 * ForTesting purposes
	 * Allows our unit Tests to create a mock location provider
	 * so that this class can acquire location when unit tested on emulator 
	 * where GPS and Network providers are absent
	 * @param provier
	 */
	public void setProvider( String provider){
		this.locationManager.addTestProvider(provider, false, false, false, false, true, true, true, 0, 5);
		this.locationManager.setTestProviderEnabled(provider, true);
	}
	/**
	 * This function sets a specific location 
	 * for the Test provider above
	 */
	public void setProviderLocation (String provider, Location location){
		this.locationManager.setTestProviderLocation(provider, location);
		
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
	public Location getLocation() {
		
		return gpsLocation;

	}

	public LocationListener getLocationListener(){
		return locationListener;
	}



}
