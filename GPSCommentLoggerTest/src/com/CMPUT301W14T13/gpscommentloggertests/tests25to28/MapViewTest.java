package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import org.junit.Before;
import org.osmdroid.util.GeoPoint;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.CMPUT301W14T13.gpscommentlogger.view.MapViewActivity;


@SuppressLint("NewApi")
public class MapViewTest extends ActivityInstrumentationTestCase2<MapViewActivity> {

    public MapViewTest() {
		super(MapViewActivity.class);
	}
    
	Intent intent;
	MapViewActivity activity;
	
    @Before
	public void setUp() throws Exception {
		super.setUp();
		intent = new Intent();
		intent.putExtra("canSetMarker", 1);
		setActivityIntent(intent);
	}

	// Testing setting map location with the marker
	public void testSetMapLocation () throws Throwable {
		
		activity = getActivity();
		assertNotNull(activity);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				
				GeoPoint mockGeoPoint = new GeoPoint(37.377166,-122.086966);
				activity.setMarker(mockGeoPoint);
				
				Button mapSubmitButton = (Button) getActivity().findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submitLocation); // submit button in map view
				assertNotNull(mapSubmitButton);				
				
				mapSubmitButton.performClick(); // this click attaches default location of map (set to Edmonton)
				
				assertEquals("Latitude not set to marker's position", mockGeoPoint.getLatitude(), activity.getReturnPoint().getLatitude());
				assertEquals("Longitude not set to marker's position", mockGeoPoint.getLongitude(), activity.getReturnPoint().getLongitude());

			}
		});
	}
	
    
}
