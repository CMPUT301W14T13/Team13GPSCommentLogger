package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.LocationSelection;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;

@SuppressLint("NewApi")
public class EditLocationTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	// Test coordinates. Insure they are different from mock emulator location.
	CreateSubmissionActivity activity;
	int constructCode;
	int submitCode;
	
	Intent intent;
	CommentLogger cl;
		
	private static final String PROVIDER = "gps";
    private static final double LAT = 37.377166;
    private static final double LNG = -122.086966;
    private static final float ACCURACY = 3.0f;
    
    public EditLocationTest() {
		super(CreateSubmissionActivity.class);
	}
    
    @Before
	public void setUp() throws Exception {
		super.setUp();
		intent = new Intent();
		setActivityIntent(intent);
		
		cl = CommentLogger.getInstance();
		cl.getTopics().clear();
		cl.addTopic(new Topic());
		cl.setCurrentTopic(0);
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
    
    
    /* MIGHT NOT NEED!!!!!
	// Testing setting map location in comment object
	public void testSetMapLocation () throws Throwable {

		intent.putExtra("construct code", 2);
		intent.putExtra("submit code", 3);
		intent.putExtra("row number", 0);
		
		cl.addComment(new Comment());
		cl.getTopicChildren().get(0).setImage(Bitmap.createBitmap(1,1, Config.ARGB_8888));
		activity = getActivity();
		assertNotNull(activity);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);
				
				ImageButton attachLocationButton = (ImageButton) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.mapButton);
				assertNotNull(attachLocationButton);
				
				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("User2");
				commentText.setText("text2");
				
				attachLocationButton.performClick(); // This click take us to another activity
				
				Button mapSubmitButton = (Button) getActivity().findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submitLocation); // submit button in map view
				assertNotNull(mapSubmitButton);				
				
				mapSubmitButton.performClick(); // this click attaches default location of map (set to Edmonton)
				
				submitButton.performClick();

				Bitmap bitmap = Bitmap.createBitmap(5,2, Config.ARGB_8888);
				cl.getTopicChildren().get(0).setImage(bitmap);
				Comment comment = (Comment) cl.getTopicChildren().get(0);

				assertEquals("Usernames wasn't edited", comment.getUsername(), username.getText().toString());
				assertEquals("Text wasn't edited", comment.getCommentText(), commentText.getText().toString());
				assertEquals("Image wasn't edited", comment.getImage(), bitmap);


			}
		});
	}
	*/
	
	// Testing setting default GPS in comment object
		public void testDefaultSetLocation () throws Throwable {

			intent.putExtra("construct code", 2);
			intent.putExtra("submit code", 3);
			intent.putExtra("row number", 0);
			
			cl.addComment(new Comment());
			cl.getTopicChildren().get(0).setImage(Bitmap.createBitmap(1,1, Config.ARGB_8888));
			activity = getActivity();
			assertNotNull(activity);

			// set up mock location
			LocationSelection locationGetter = new LocationSelection(getActivity());
	    	
	    	locationGetter.startLocationSelection();
	    	locationGetter.setProvider(PROVIDER);
	    	
	    	final Location mockLocation = createLocation(LAT, LNG, ACCURACY);
	    	
	    	locationGetter.setProviderLocation(PROVIDER, mockLocation);
	    	
	    	Thread.sleep(100); // wait for phone to fetch location    	
	    	// set up done
			
			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {


					EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
					EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
					Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);
					
					assertNotNull(username);
					assertNotNull(commentText);
					assertNotNull(submitButton);

					username.setText("User2");
					commentText.setText("text2");
					
					submitButton.performClick();

					Bitmap bitmap = Bitmap.createBitmap(5,2, Config.ARGB_8888);
					cl.getTopicChildren().get(0).setImage(bitmap);
					Comment comment = (Comment) cl.getTopicChildren().get(0);

					assertEquals("Usernames wasn't edited", comment.getUsername(), username.getText().toString());
					assertEquals("Text wasn't edited", comment.getCommentText(), commentText.getText().toString());
					assertEquals("Image wasn't edited", comment.getImage(), bitmap);
					
					// location asserts
					assertNotNull("Didn't get a location", comment.getGPSLocation());
			    	assertEquals("Mock location latitudes not equal to provider location", comment.getGPSLocation().getLatitude(), mockLocation.getLatitude());
			    	assertEquals("Mock location longitudes not equal to provider location", comment.getGPSLocation().getLongitude(), mockLocation.getLongitude());


				}
			});
		}
	

}
