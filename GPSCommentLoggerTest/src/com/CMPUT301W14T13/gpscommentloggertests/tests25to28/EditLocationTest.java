package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.LocationSelection;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;

@SuppressLint("NewApi")
public class EditLocationTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	// Test coordinates. Insure they are different from mock emulator location.
	CreateSubmissionActivity activity;
	int constructCode;
	int submitCode;
	CommentLogger cl;
	private static final String PROVIDER = "gps";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;
    
    public EditLocationTest() {
		super(CreateSubmissionActivity.class);
	}
    
    public Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location(PROVIDER);
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }
    public void testLocationSelection () throws InterruptedException{
    	LocationSelection locationGetter = new LocationSelection(getActivity());
    	
    	locationGetter.startLocationSelection();
    	locationGetter.setProvider(PROVIDER);
    	
    	Location mockLocation = createLocation(LAT, LNG, ACCURACY);
    	
    	locationGetter.setProviderLocation(PROVIDER, mockLocation);
    	
    	Thread.sleep(100); // wait for phone to fetch location    	
    	
    	Location location = locationGetter.getLocation();
    	
    	assertNotNull("Didn't get a location", location);
    	assertEquals("Mock location latitudes not equal to provider location", location.getLatitude(), mockLocation.getLatitude());
    	assertEquals("Mock location longitudes not equal to provider location", location.getLongitude(), mockLocation.getLongitude());

    }
    
    
    /*
	
	public void testEditLocation () throws Throwable {
		Intent intent = new Intent();
	
		intent.putExtra("construct code", 3);
		intent.putExtra("submit code", 2);
		setActivityIntent(intent);
		activity = getActivity();
		assertNotNull(activity);
		cl = CommentLogger.getInstance();
		cl.getTopics().clear();
		cl.addTopic(new Topic());
		cl.setCurrentTopic(0);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);
				ImageButton attachImage = (ImageButton) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.imageButton1);
				ImageButton editLocation = (ImageButton) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.mapButton);

				assertNotNull(editLocation);
				
				username.setText("User1");
				commentText.setText("text");
				
				
				
				submitButton.performClick();
				
				Bitmap bitmap = Bitmap.createBitmap(5,2, Config.ARGB_8888);
				
				assertEquals("Usernames wasn't edited", topic.getUsername(), username.getText().toString());
				assertEquals("Text wasn't edited", topic.getCommentText(), commentText.getText().toString());
				assertEquals("Image wasn't edited", topic.getImage(), bitmap);

			}
		});	
	}
	*/

}
