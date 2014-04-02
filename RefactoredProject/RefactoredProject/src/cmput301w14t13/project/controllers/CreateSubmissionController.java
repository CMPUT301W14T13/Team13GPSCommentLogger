package cmput301w14t13.project.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.ImageUpdateServerTask;
import cmput301w14t13.project.models.tasks.LocationUpdateServerTask;
import cmput301w14t13.project.models.tasks.PostNewServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.models.tasks.TextUpdateServerTask;
import cmput301w14t13.project.services.DataStorageService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * This is where the user can construct a new submission or
 * edit a previous one. This activity uses a construct code which tells it how to
 * build the submission, and a submit code which tells it how to submit the
 * submission. It also uses the CheckSubmission function to check if the
 * submission is a valid one. A valid comment must have comment text and a 
 * valid topic must have comment text and a title. The username will default
 * to "Anonymous" if left empty.
 * 
 * @author Austin
 *
 */
public class CreateSubmissionController extends RankedHierarchicalActivity implements AsyncProcess, UpdateInterface{

	private String username;
	private String title;
	private String commentText;
	private Bitmap image;
	private ImageAttachementController imageAttacher;
	private CommentTreeElement submission;
	private int constructCode; //0: Creating topic, 1: Creating comment, 2,3: Editing
	private int rowNumber;
	private String currentUsername = "";
	private int submitCode; //0: Reply to topic, 1: Reply to comment, 2: Edited topic 3: Edited comment
	private Location gpsLocation;
	private Location userLocation;
	private LocationManager lm; 
	private LocationListener ll;
	private static final int REQUEST_CODE = 1;
	private static final int PICK_FROM_FILE = 2;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create the location stuff up here */

		constructCode = getIntent().getIntExtra("construct code", -1);
		submitCode = getIntent().getIntExtra("submit code", -1);
		rowNumber = getIntent().getIntExtra("row number", -1);
		
		initializeLocation();
		initializeFields();
	}

	@Override
	protected void onResume(){
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}


	/**
	 * extracts a longitude and latitude from MapViewActivity to be used
	 * in construction the topic. if from some reason a latitude and longitude cannot
	 * be retrieved it gets the current gps location. Also used to attach
	 * an image to the submission.
	 */


	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE){
			if (resultCode == RESULT_OK){
				double latitude = data.getDoubleExtra("lat", gpsLocation.getLatitude());
				double longitude = data.getDoubleExtra("lon", gpsLocation.getLongitude());
				userLocation = new Location(gpsLocation);
				userLocation.setLongitude(longitude);
				userLocation.setLatitude(latitude);
			}
		}

		if (requestCode == PICK_FROM_FILE) {
			if (resultCode == RESULT_OK){

				Uri selectedImageUri = data.getData();


				try {
					image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				Log.d("Image Attach", "Image received: " + image.toString());
				ImageButton attachButton = (ImageButton) findViewById(R.id.imageButton1); // set attach button to image selected
				/*
				// Check if image satisfies size conditions
				int imageSize = image.getByteCount();
				Log.d("Image Attach", "Image size is: " + imageSize);

				if (imageSize < 102401) {
					attachButton.setImageBitmap(image);
					Log.d("Image Attach", "Image size safe");
				}
				else {
					image = null;
					Log.d("Image Attach", "Image size unsafe");
					Toast.makeText(getApplicationContext(), "Image Size Exceeds 100 KB",
							Toast.LENGTH_LONG).show();
				}
*/
			}
		}

	}


	@Override
	public void onDestroy(){
		super.onDestroy();
		lm.removeUpdates(ll);
		lm = null;
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}


	private void initializeLocation() {
		//mapLocation does not have listener attached so it only changes when mapActivity returns a result

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
				Log.d("locationChange", gpsLocation.toString());
			}
		};
		
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		/* if you cannot get a GPS fix set the values to : 0,0*/
		if (gpsLocation == null){
			gpsLocation = new Location("fake");
			gpsLocation.setLatitude(0);
			gpsLocation.setLongitude(0);
		}
		userLocation = null;
	}
	
	private void initializeFields() {
		CommentTree cl = CommentTree.getInstance();

		//get the user's global user name so they don't have to always enter it
		currentUsername = cl.getCurrentUsername();

		switch(constructCode){

			case(0): // constructing a new topic
				setContentView(R.layout.create_topic); //creating a topic
				getActionBar().setDisplayHomeAsUpEnabled(true);
				EditText text = (EditText) findViewById(R.id.setTopicUsername);
				text.setText(currentUsername);
				break;
	
			case(1): //constructing a new comment
				setContentView(R.layout.create_comment); //creating a comment
				text = (EditText) findViewById(R.id.set_comment_username);
				text.setText(currentUsername);
				break;
	
			//These cases are for editing a comment or topic
			case(2):
			case(3):
				setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)
			
			
				cl = CommentTree.getInstance();
				if (constructCode == 3){ //CheckSubmission needs to check the title
		
					submission = cl.getElement(this);
					title = submission.getTitle();
				}
				else{
					submission = cl.getCommentList(this).get(rowNumber);
				}
		
				/*
				 * Set various text fields below from the topic so that they are displayed when editing it
				 */
				text = (EditText) findViewById(R.id.set_comment_text);
				text.setText(submission.getCommentText());
		
				text = (EditText) findViewById(R.id.set_comment_username);
				text.setText(submission.getUsername());
				extractTextFields();
		
				//text = (EditText) findViewById(R.id.coordinates);
				//text.setText(submission.locationString());
				break;
			default:throw new IllegalArgumentException("Illegal ConstructCode");
		}
			
	}	

	/**
	 * Responsible for creating comments and editing viewables
	 * 
	 * Submits the submission. When the user hits the submit button,
	 * it first extracts the information that the user entered in
	 * the text fields and constructs a topic or comment
	 * from what was entered. Next, it uses CheckSubmission to
	 * verify that the submission is valid. Afterwards it can then
	 * add the submission to the model based on the submit code.
	 * Finally, it updates the model's commentList so that it will
	 * display upon return the TopicViewActivity.
	 * 
	 * 
	 * @param v is the submit button view
	 * @throws InterruptedException 
	 */
	public synchronized void submit(View v) throws InterruptedException{

		extractTextFields();
		constructSubmission();

		if (checkSubmission(submission)){
			int row = rowNumber;
			CommentTree cl = CommentTree.getInstance();
			DataStorageService dss = DataStorageService.getInstance();
			TaskFactory factory = new TaskFactory(dss);

			switch (submitCode){

			case(0):  //reply to topic
				cl.addElementToCurrent(submission);
				factory.requestPost(cl.getElement(this).getID(), submission);
				break;

			case(1): //reply to comment
				CommentTreeElement parent = cl.getCommentList(this).get(row);//get the comment being replied to
				submission.setIndentLevel(parent.getIndentLevel() + 1);//set the indent level of the new comment to be 1 more than the one being replied to
				
				
				//TODO: For the moment, don't add any comments if their indent is beyond what is in comment_view.xml. Can be dealt with later.
				if (submission.getIndentLevel() <= 5){
					parent.addChild(submission);
					factory.requestPost(parent.getID(), submission);
				}
	
				break;

			case(2)://edit topic

				//TODO: no username update support at the moment
				cl.getElement(this).setUsername(submission.getUsername());
				cl.getElement(this).setCommentText(submission.getCommentText());
				cl.getElement(this).setLocation(submission.getGPSLocation());
				cl.getElement(this).setImage(submission.getImage());
				Log.w("UpdateTest", cl.getElement(this).getID());
				//TODO: it wasn't anticipated that more than one field would be set at a time
				factory.requestImageUpdate(submission);
				factory.requestLocationUpdate(submission);
				factory.requestTextUpdate(submission);
				break;

			case(3): //edit comment
				
				//TODO: no username update support at the moment
				cl.getCommentList(this).get(row).setUsername(submission.getUsername());
				cl.getCommentList(this).get(row).setCommentText(submission.getCommentText());
				cl.getCommentList(this).get(row).setGPSLocation(submission.getGPSLocation());
				cl.getCommentList(this).get(row).setImage(submission.getImage());
				
				//TODO: it wasn't anticipated that more than one field would be set at a time
				factory.requestImageUpdate(submission);
				factory.requestLocationUpdate(submission);
				factory.requestTextUpdate(submission);
				break;

			default:
				Log.d("onActivityResult", "Error adding comment reply");
			}

			cl.updateCommentList(this); //this will update the comment list being displayed to show the new changes
			finish();
		}
	}

	/**
	 * Submits the new topic. When the user creates a topic and hit submit,
	 * it will extract the text fields and constructs a new topic from this
	 * information. It then checks that the topic is valid and then adds it to
	 * the root in the model which holds the list of topics.
	 * 
	 * @param v the submit button
	 * @throws InterruptedException 
	 */
	public synchronized void submitTopic(View v) throws InterruptedException{

		boolean submission_ok;

		extractTextFields();
		constructSubmission();

		submission_ok = checkSubmission(submission); //check that the submission is valid
		if (submission_ok){

			CommentTree cl = CommentTree.getInstance();
			cl.addElementToCurrent(submission);
			DataStorageService dss = DataStorageService.getInstance();
			new TaskFactory(dss).requestPost("ROOT", submission);
			finish();
		}


	}


	/**
	 * The function to check if a submission is valid. For a topic it checks
	 * that the title and comment text aren't empty, and for a comment it
	 * checks that the comment text isn't empty. If at any point something
	 * is empty, it will display an appropriate toast notifying the user
	 * what the problem is.
	 * 
	 * @param submission  the topic or comment being checked
	 * @return  a boolean, true if it's a valid submission, false otherwise
	 */
	private boolean checkSubmission(CommentTreeElement submission){

		boolean submission_ok = true;
		Toast toast = null;
		Context context = getApplicationContext();
		String text = ""; 
		int duration = Toast.LENGTH_LONG;

		//check title
		if (submission instanceof Topic && submission.getTitle().length() == 0){
			text += "Title cannot be blank";
			submission_ok = false;
		}

		//check comment text
		if (submission.getCommentText().length() == 0){
			if (!submission_ok){
				text += "\n";
			} 
			text += "Comment cannot be blank";
			submission_ok = false;
		}

		//display toast if submission invalid
		if (!submission_ok){
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
		}
		return submission_ok;

	}

	public CommentTreeElement getSubmission(){
		return this.submission;
	}
	
	//extract the information that the user has entered
	private void extractTextFields(){


		//Constructing a topic(0)
		if (constructCode == 0){

			//only get the title if it's a topic
			EditText text = (EditText) findViewById(R.id.setTitle);
			title = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.setTopicUsername);
			username = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.setTopicText);
			commentText = text.getText().toString().trim();
		}

		//Constructing a comment(1), editing a comment(2), or editing a topic(3)
		else{

			EditText text = (EditText) findViewById(R.id.set_comment_username);
			username = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.set_comment_text);
			commentText = text.getText().toString().trim();
		}



	}


	/**
	 * Constructs the comment/topic to be submitted by checking the
	 * construct code
	 */
	private void constructSubmission(){

		
		if (constructCode == 0){
			//Add a title if a NEW TOPIC is being made
			submission = new Topic();
			submission.setTitle(title);
		}
		else if(constructCode == 3)
		{
			//A TOPIC is being EDITED
			submission = CommentTree.getInstance().getElement(this);
		}
		else if(constructCode == 1){
			//A NEW COMMENT is being made
			submission = new Comment();
		}
		else
		{
			//else a COMMENT is being EDITED
			submission = CommentTree.getInstance().getCommentList(this).get(rowNumber);
		}

		submission.setUsername(username); 
		submission.setCommentText(commentText);
		if(userLocation == null){
			submission.setGPSLocation(gpsLocation);
		} else {
			submission.setGPSLocation(userLocation);
		}


		//username defaults to Anonymous if left blank
		if (username.length() == 0){
			submission.setAnonymous();

		}

		if (image != null) {
			submission.setImage(image);
			Log.d("Image Attach", "Image Attached! " + image.toString());
		}

	}

	/**
	 * Start intent for user to select
	 * image from gallery and return bitmap
	 * if conditions are satisfied
	 */
	public void attachImage(View view) {
		Log.d("Image Attach", "Starting Image Attachment Intent");
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Gallery"), PICK_FROM_FILE);
	}
	/**
	 * Once location button is clicked we check if our user is online, if so we pop up a map 
	 * to edit location otherwise we open a dialog fragment for offline location editing
	 * @param view
	 */
	public void openMap(View view) {
		if(isOnline()){
			Intent map = new Intent(this, MapViewController.class);
			map.putExtra("lat", gpsLocation.getLatitude()); 
			map.putExtra("lon", gpsLocation.getLongitude());
			map.putExtra("canSetMarker", 1);
			map.putExtra("updateRank", rank.getRank());
			startActivityForResult(map, REQUEST_CODE); 
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			LayoutInflater inflater = getLayoutInflater();
			final View dialogView = inflater.inflate(R.layout.offline_location_dialog, null);
			builder.setView(dialogView);
			AlertDialog ad = builder.create();
			ad.setTitle("Select Location");
			ad.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
					new DialogInterface.OnClickListener()
			{	
				@Override
				public void onClick(DialogInterface dialog, int which)
				{  
					EditText text = (EditText) dialogView.findViewById(R.id.offlineLatitude);
					double latitude = Double.parseDouble(text.getText().toString().trim());
					text = (EditText) dialogView.findViewById(R.id.offlineLongitude);
					double longitude = Double.parseDouble(text.getText().toString().trim());
					userLocation = new Location(gpsLocation);
					userLocation.setLatitude(latitude);
					userLocation.setLongitude(longitude);

				}
			});
			ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//do nothing
				}
			});
			ad.show();


		}

	}

	@Override
	public synchronized void receiveResult(String result) {
		notify();		
	}

	@Override
	public void update() {
	}

	@Override
	public UpdateRank getRank() {
		return rank;
	}
}




