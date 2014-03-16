package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.SubmissionController;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;


public class CreateTopicActivity extends Activity{

	private String username;
	private String title;
	private String commentText;
	private Location gpsLocation;
	private Location mapLocation;
	private LocationManager lm; 
	private LocationListener ll;
	private SubmissionController controller;
	private Topic topic;
	private static final int REQUEST_CODE = 1;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_topic);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //mapLocation does not have listener attached so it only changes when mapActivity returns a result
        gpsLocation = new Location(LocationManager.GPS_PROVIDER);
        mapLocation = gpsLocation;
        
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new LocationListener() {
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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
    }
	@Override
	protected void onResume(){
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}

	
	
	//extract the information that the user has entered
	public void extractTextFields(){
		
		EditText text = (EditText) findViewById(R.id.setTitle);
		title = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.setUsername);
		username = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.setCommentText);
		commentText = text.getText().toString().trim();
	}
	
	
	
	public void constructTopic(){
		topic = new Topic();
		
		topic.setTitle(title);
		topic.setUsername(username);
		topic.setCommentText(commentText);
		//if map location isnt set then it will be identical to gpsLocation
		topic.setLocation(mapLocation);
		if (username.length() == 0){
			topic.setAnonymous();
		
		}

		
	}
	public void openMap(View view) {
		Intent map = new Intent(this, MapViewActivity.class);
		map.putExtra("lat", gpsLocation.getLatitude());
		map.putExtra("lon", gpsLocation.getLongitude());
		startActivityForResult(map, REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE){
			if (resultCode == RESULT_OK){
				double latitude = data.getDoubleExtra("lat", gpsLocation.getLatitude());
				double longitude = data.getDoubleExtra("lon", gpsLocation.getLongitude());
				mapLocation.setLongitude(longitude);
				mapLocation.setLatitude(latitude);
			}	
		}
	}
	
	public void submitTopic(View v){
		
		Intent submit = getIntent();
		Context context = getApplicationContext();
		controller = new SubmissionController();
		boolean submission_ok;
		
		extractTextFields();
		constructTopic();
		
		submission_ok = controller.checkSubmission(context, topic); //check that the topic is valid
		if (submission_ok){
			
			submit.putExtra("Topic", topic); 
			setResult(RESULT_OK, submit);
			finish();
		}
		
		
	}
	
	
	
}
