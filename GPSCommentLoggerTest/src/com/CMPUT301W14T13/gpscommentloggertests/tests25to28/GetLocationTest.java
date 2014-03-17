package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;

/**
 * Android JUnit tests to test the getting and setting of GPS coordinates within our application
 * 
 * @author mjnichol
 *
 */

@SuppressLint("NewApi")
public class GetLocationTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	public GetLocationTest() {
		super(CreateSubmissionActivity.class);
	}
	
	Activity activity;
	
	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}
	
	/**
	 * A simple test to set a Comment's location
	 */
	public void testSetCommentLocation(){
		
		Viewable comment = new Comment();
		Location location = new Location("test");
	    double latitude=0;
	    double longitude=0;
	    location.setLatitude(latitude);
	    location.setLongitude(longitude);
		comment.setGPSLocation(location);
		assertEquals("The GPS coordinates do not match",location,comment.getGPSLocation());
		
	}
	
	/**
	 * A simple test to set a Topic's location
	 */
	public void testSetTopicLocation(){
		
		Viewable topic = new Topic();
		Location location = new Location("test");
	    double latitude=0;
	    double longitude=0;
	    location.setLatitude(latitude);
	    location.setLongitude(longitude);
		topic.setGPSLocation(location);
		
		assertEquals("The GPS coordinates do not match",location,topic.getGPSLocation());
		
	}
	
	/*
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
	*/

}
