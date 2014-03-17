package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.SubmissionController;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;


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
	private SubmissionController controller;
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
	private static final int REQUEST_CODE = 1;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create the location stuff up here */

		constructCode = getIntent().getIntExtra("construct code", -1);
		submitCode = getIntent().getIntExtra("submit code", -1);

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

		rowNumber = getIntent().getIntExtra("row number", -1);

		switch(constructCode){

		case(0): // constructing a new topic
			setContentView(R.layout.create_topic); //creating a topic
		getActionBar().setDisplayHomeAsUpEnabled(true);
		break;

		case(1): //constructing a new comment
			setContentView(R.layout.create_comment); //creating a comment
		CommentLogger cl = CommentLoggerApplication.getCommentLogger();

		//get the user's global username so they don't have to always enter it
		currentUsername = cl.getCurrentUsername();
		text = (EditText) findViewById(R.id.set_comment_username);
		text.setText(currentUsername);
		break;

		//These cases are for editing a comment or topic
		case(2):
		case(3):
			setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)

		submission = getIntent().getParcelableExtra("submission");

		if (constructCode == 3){ //CheckSubmission needs to check the title
			title = submission.getTitle();
		}

		text = (EditText) findViewById(R.id.set_comment_text);
		text.setText(submission.getCommentText());

		text = (EditText) findViewById(R.id.set_comment_username);
		text.setText(submission.getUsername());
		extractTextFields();


		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}



	//extract the information that the user has entered
	public void extractTextFields(){


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
	public void constructSubmission(){

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

	/**
	 * 
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

		controller = new SubmissionController();
		boolean submission_ok;
		ArrayList<Viewable> commentList;

		extractTextFields();
		constructSubmission();

		submission_ok = checkSubmission(submission); //check that the submission is valid
		if (submission_ok){


			int row = rowNumber;
			Comment prev_comment = new Comment();
			CommentLoggerController controller = new CommentLoggerController(CommentLoggerApplication.getCommentLogger());
			CommentLogger cl = CommentLoggerApplication.getCommentLogger();
			commentList = cl.getCommentList();


			switch (submitCode){

			case(0):  //reply to topic

				cl.addComment((Comment) submission);
			cl.getCurrentTopic().incrementCommentCount();
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
			break;

			case(3): //edit comment

				commentList.get(row).setUsername(submission.getUsername());
			commentList.get(row).setCommentText(submission.getCommentText());
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

		controller = new SubmissionController();
		boolean submission_ok;

		extractTextFields();
		constructSubmission();

		submission_ok = checkSubmission(submission); //check that the submission is valid
		if (submission_ok){

			CommentLogger cl = CommentLoggerApplication.getCommentLogger();
			CommentLoggerController controller = new CommentLoggerController(cl);
			controller.addTopic((Topic) submission);

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
	public boolean checkSubmission(Viewable submission){

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

		//display toast is invalid
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
	
}


