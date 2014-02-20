package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
//import com.CMPUT301W14T13.gpscommentslogger.LocationSelectionView
import com.CMPUT301W14T13.gpscommentlogger.view.LocationSelectionView;

@SuppressLint("NewApi")
public class GetLocationTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public GetLocationTest(String name) {
		super(DebugActivity.class);
	}
	
	public void testCurrentLocation () {
		// Goal: Get location locally in the test file, compare against LocationSelectionView.
		
		LocationManager locManager = (LocationManager)LocationSelectionView.getSystemService(Context.LOCATION_SERVICE);         

		
		// For locationListener, there is the option of implementing it here,
		// or using the one from LocationSelectionView.
		LocationListener locationListener = LocationSelectionView.getLocationListener();
	    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, locationListener);
	    Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    
	    // Get current long and lat
	    double latitude=0;
	    double longitude=0;
	    latitude = location.getLatitude();
	    longitude = location.getLongitude();
	    
	    
	    
	    // Test latitude
	    assertEquals("Latitudes should be equal", LocationSelectionView.getLocation().getLatitude(), latitude);
	    // Test longitude
	    assertEquals("Longitudes should be equal", LocationSelectionView.getLocation().getLongitude(), longitude);
	}

}
