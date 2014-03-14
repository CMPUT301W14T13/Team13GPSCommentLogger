package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.SubmissionController;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;


public class CreateTopicActivity extends Activity{

	private String username;
	private String title;
	private String commentText;
	private Location location;
	
	private SubmissionController controller;
	private Topic topic;
	private static final int REQUEST_CODE = 1;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_topic);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        LocationManager lm;
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String provider = lm.getProvider(LocationManager.GPS_PROVIDER).toString();
        location = lm.getLastKnownLocation(provider);
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
		
		if (username.length() == 0){
			topic.setAnonymous();
		
		}
		
	}
	public void openMap(View view) {
		Intent map = new Intent(this, MapViewActivity.class);
		map.putExtra("lat", 48.13);
		map.putExtra("lon", -1.63);
		startActivityForResult(map, REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE){
			if (resultCode == RESULT_OK){
				double latitude = data.getDoubleExtra("lat", location.getLatitude());
				double longitude = data.getDoubleExtra("lon", location.getLongitude());
				location.setLongitude(longitude);
				location.setLatitude(latitude);
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
