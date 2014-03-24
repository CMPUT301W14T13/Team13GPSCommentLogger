package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.CMPUT301W14T13.gpscommentlogger.NetworkReceiver;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.PostNewServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;
import com.CMPUT301W14T13.gpscommentlogger.view.MapViewActivity;


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
public class CreateSubmissionActivity extends Activity{

	private String username;
	private String title;
	private String commentText;
	private Bitmap image;
	private ImageAttacher imageAttacher;
	private Viewable submission;
	private int constructCode; //0: Creating topic, 1: Creating comment, 2,3: Editing
	private int rowNumber;
	private EditText text;
	private String currentUsername = "";
	private int submitCode; //0: Reply to topic, 1: Reply to comment, 2: Edited topic 3: Edited comment
	private Location gpsLocation;
	private Location mapLocation;
	private LocationManager lm; 
	private LocationListener ll;
	private NetworkReceiver nr;
	private static final int REQUEST_CODE = 1;
	private static final int PICK_FROM_FILE = 2;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create the location stuff up here */

		constructCode = getIntent().getIntExtra("construct code", -1);
		submitCode = getIntent().getIntExtra("submit code", -1);

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
			}
		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		gpsLocation = new Location(LocationManager.GPS_PROVIDER);
		mapLocation = gpsLocation;
		rowNumber = getIntent().getIntExtra("row number", -1);
		CommentLogger cl = CommentLogger.getInstance();

		//get the user's global username so they don't have to always enter it
		currentUsername = cl.getCurrentUsername();

		switch(constructCode){

		case(0): // constructing a new topic
			setContentView(R.layout.create_topic); //creating a topic
		getActionBar().setDisplayHomeAsUpEnabled(true);
		text = (EditText) findViewById(R.id.setTopicUsername);
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
		cl = CommentLogger.getInstance();


		if (constructCode == 3){ //CheckSubmission needs to check the title

			submission = cl.getCurrentTopic();
			title = submission.getTitle();
		}
		else{
			submission = cl.getCommentList().get(rowNumber);
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
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}


	//extract the information that the user has entered
	private void extractTextFields(){


		//Constructing a topic(0)
		if (constructCode == 0){

			//only get the title if it's a topic
			text = (EditText) findViewById(R.id.setTitle);
			title = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.setTopicUsername);
			username = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.setTopicText);
			commentText = text.getText().toString().trim();
		}

		//Constructing a comment(1), editing a comment(2), or editing a topic(3)
		else{

			text = (EditText) findViewById(R.id.set_comment_username);
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

		//Add a title if a topic is being made
		if (constructCode == 0 || constructCode == 3){
			submission = new Topic();
			submission.setTitle(title);


		}
		else{
			submission = new Comment();
		}

		submission.setUsername(username);
		submission.setCommentText(commentText);
		submission.setGPSLocation(mapLocation);


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
		if(nr.isConnected){
			Intent map = new Intent(this, MapViewActivity.class);
			map.putExtra("lat", gpsLocation.getLatitude());
			map.putExtra("lon", gpsLocation.getLongitude());
			startActivityForResult(map, REQUEST_CODE);
		} else {

			new AlertDialog.Builder(this)
			.setTitle("Location")
			.setMessage("Please enter your desired location")
			.setView(view)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// continue with delete
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// do nothing
				}
			})
			.setIcon(R.drawable.location)
			.show();

		}
	}
	/**
	 * extracts a longitude and latitude from MapViewActivity to be used
	 * in construction the topic. if from some reason a lat and lon cannot
	 * be retrieved it gets the current gps location. Also used to attach
	 * an image to the submission.
	 */


	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE){
			if (resultCode == RESULT_OK){
				double latitude = data.getDoubleExtra("lat", gpsLocation.getLatitude());
				double longitude = data.getDoubleExtra("lon", gpsLocation.getLongitude());
				mapLocation.setLongitude(longitude);
				mapLocation.setLatitude(latitude);
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

			}
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
	 */
	public void submit(View v){


		boolean submission_ok;
		ArrayList<Viewable> commentList;

		extractTextFields();
		constructSubmission();

		submission_ok = checkSubmission(submission); //check that the submission is valid
		if (submission_ok){


			int row = rowNumber;
			Comment prev_comment = new Comment();
			CommentLoggerController controller = new CommentLoggerController(CommentLogger.getInstance());
			CommentLogger cl = CommentLogger.getInstance();
			commentList = cl.getCommentList();


			switch (submitCode){

			case(0):  //reply to topic

				cl.addComment((Comment) submission);
			cl.getCurrentTopic().incrementCommentCount(); //increment the count keeping track of how many comments are in the topic
			break;

			case(1): //reply to comment


				if (cl.getCurrentTopic().getChildren().size() >= 1){
					prev_comment = (Comment) commentList.get(row); //get the comment being replied to
					((Comment) submission).setIndentLevel(prev_comment.getIndentLevel() + 1); //set the indent level of the new comment to be 1 more than the one being replied to
				}

			//For the moment, don't add any comments if their indent is beyond what is in comment_view.xml. Can be dealt with later.
			if (((Comment) submission).getIndentLevel() <= 5){
				prev_comment.addChild(submission);
				cl.getCurrentTopic().incrementCommentCount();
			}

			break;

			case(2)://edit topic

				cl.getCurrentTopic().setUsername(submission.getUsername());
			cl.getCurrentTopic().setCommentText(submission.getCommentText());
			cl.getCurrentTopic().setLocation(submission.getGPSLocation());
			cl.getCurrentTopic().setImage(submission.getImage());
			break;

			case(3): //edit comment

				commentList.get(row).setUsername(submission.getUsername());
			commentList.get(row).setCommentText(submission.getCommentText());
			commentList.get(row).setGPSLocation(submission.getGPSLocation());
			commentList.get(row).setImage(submission.getImage());
			break;

			default:
				Log.d("onActivityResult", "Error adding comment reply");


			}


			cl.updateTopicChildren(commentList); 
			controller.updateCommentList(); //this will update the comment list being displayed to show the new changes
			finish();
		}
	}

	/**
	 * Submits the new topic. When the user creates a topic and hit submit,
	 * it will extract the text fields and contruct a new topic from this
	 * information. It then checks that the topic is valid and then adds it to
	 * the root in the model which holds the list of topics.
	 * 
	 * @param v the submit button
	 */
	public void submitTopic(View v){

		boolean submission_ok;

		extractTextFields();
		constructSubmission();

		submission_ok = checkSubmission(submission); //check that the submission is valid
		if (submission_ok){

			CommentLogger cl = CommentLogger.getInstance();
			cl.addTopic((Topic) submission);
			ClientController client = ClientServerSystem.getInstance().getClient();
			PostNewServerTask task = new TaskFactory(client.getDispatcher(),client.getMockup(),client.getDataManager()).getNewPoster();
			task.setObj(submission);
			task.setSearchTerm("ROOT");
			try
			{
				task.setId(Integer.toString(task.hashCode()));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			client.addTask(task);

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
	private boolean checkSubmission(Viewable submission){

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

	public Viewable getSubmission(){
		return this.submission;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		lm.removeUpdates(ll);
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}




