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
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.SubmissionController;




public class CreateCommentActivity extends Activity
{
	
	private Intent intent;
	private int rowNumber;
	private String username;
	private String commentText;
	private SubmissionController controller;
	private Comment comment;
	
	private Location gpsLocation;
	private Location mapLocation;
	private LocationManager lm; 
	private LocationListener ll;
	
	private static final int REQUEST_CODE = 1;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_comment);
        
        intent = getIntent();
        rowNumber = intent.getIntExtra("row number", -1);
        
      //mapLocation does not have listener attached so it only changes when mapActivity returns a result
        gpsLocation = new Location(LocationManager.GPS_PROVIDER);
        mapLocation = gpsLocation;
        
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
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
		
	private void ExtractTextFields(){
		
		EditText text = (EditText) findViewById(R.id.set_comment_username);
		username = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.set_comment_text);
		commentText = text.getText().toString().trim();
		
		
	}
	
	//create the comment
	private void constructComment(){
		
		comment = new Comment();
		comment.setUsername(username);
		comment.setCommentText(commentText);
		
		if (username.length() == 0){
			comment.setAnonymous();
		
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
	
	public void submitReply(View v){
		
		Context context = getApplicationContext();
		boolean submission_ok;
		controller = new SubmissionController();
		
		ExtractTextFields();
		constructComment();
		
		submission_ok = controller.checkSubmission(context, comment); //check that the comment is valid
		if (submission_ok){
			
			
			
			//send comment back as well as which comment it belongs to
			intent.putExtra("comment", comment);
			intent.putExtra("row number", rowNumber);
			
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	

	
	
}


