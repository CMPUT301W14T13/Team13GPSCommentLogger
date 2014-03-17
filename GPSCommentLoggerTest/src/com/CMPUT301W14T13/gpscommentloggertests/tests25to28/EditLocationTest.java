package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import android.annotation.SuppressLint;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;

@SuppressLint("NewApi")
public class EditLocationTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	// Test coordinates. Insure they are different from mock emulator location.	
	private static final String PROVIDER = "flp";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;
    
    public EditLocationTest(String name) {
		super(DebugActivity.class);
	}
    
    public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
	
	public void testEditLocation () {
		// Goal: Get location locally in the test file, compare against LocationSelectionView.
		
		// Create comment with default location
		Topic topic = new Topic();
		
		// Create a new Location from test data
	    Location testLocation = createLocation(LAT, LNG, ACCURACY);
	    
	    // Save old location
	    Location oldLocation = topic.getGPSLocation();
	    
	    // Set new location to comment
	    topic.setGPSLocation(testLocation);
	    
	    // Test difference
	    assertNotSame("Old location should be different that test location", oldLocation, topic.getGPSLocation());
		
		
			
	}

}
