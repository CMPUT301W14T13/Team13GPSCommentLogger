package cmput301w14t13.project.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.LocationSelection;

public class CreateSubmissionController
{

	private int constructCode;
	private int rowNumber;
	private int submitCode;
	private ImageAttacher imageAttacher;
	private CommentTreeElementSubmission submission;
	private CreateSubmissionView view;

	public CreateSubmissionController(CreateSubmissionView view, int constructCode, int submitCode, int rowNumber)
	{
		this.view = view;
		this.constructCode = constructCode;
		this.submitCode = submitCode;
		this.rowNumber = rowNumber;
	}
	
	public void initializeLocation() {
		//mapLocation does not have listener attached so it only changes when mapActivity returns a result
		
		submission = new CommentTreeElementSubmission(view);
		submission.setGpsLocation(LocationSelection.getInstance().getLocation());
		
		/* if you cannot get a GPS fix set the values to : 0,0*/
		if (submission.getGpsLocation() == null){
			submission.setGpsLocation(new Location("fake"));
			submission.getGpsLocation().setLatitude(0);
			submission.getGpsLocation().setLongitude(0);
		}
		submission.setUserLocation(null);
	}
	
	public void initializeFields() {
		CommentTree cl = CommentTree.getInstance();

		//get the user's global user name so they don't have to always enter it
		submission.getSubmission().setUsername(cl.getCurrentUsername());
		final String currentUsername = cl.getCurrentUsername();

		switch(constructCode){

			case(0): // constructing a new topic
				view.setContentView(R.layout.create_topic); //creating a topic
				view.getActionBar().setDisplayHomeAsUpEnabled(true);
				EditText text = (EditText) view.findViewById(R.id.setTopicUsername);
				text.setText(currentUsername);
				break;
	
			case(1): //constructing a new comment
				view.setContentView(R.layout.create_comment); //creating a comment
				text = (EditText) view.findViewById(R.id.set_comment_username);
				text.setText(currentUsername);
				break;
	
			//These cases are for editing a comment or topic
			case(2):
			case(3):
				view.setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)
			
			
				cl = CommentTree.getInstance();
				if (constructCode == 3){ //CheckSubmission needs to check the title
		
					submission = new CommentTreeElementSubmission(cl.getElement(view), view);
				}
				else{
					submission = new CommentTreeElementSubmission(cl.getCommentList(view).get(rowNumber), view);
				}
		
				/*
				 * Set various text fields below from the topic so that they are displayed when editing it
				 */
				text = (EditText) view.findViewById(R.id.set_comment_text);
				text.setText(submission.getSubmission().getCommentText());
		
				text = (EditText) view.findViewById(R.id.set_comment_username);
				text.setText(submission.getSubmission().getUsername());
				extractTextFields();
		
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

		if (checkSubmission(submission.getSubmission())){
			int row = rowNumber;
			CommentTree cl = CommentTree.getInstance();
			DataStorageService dss = DataStorageService.getInstance();
			TaskFactory factory = new TaskFactory(dss);

			switch (submitCode){

			case(0):  //reply to topic
				cl.addElementToCurrent(submission.getSubmission());
				factory.requestPost(cl.getElement(view).getID(), submission.getSubmission());
				break;

			case(1): //reply to comment
				CommentTreeElement parent = cl.getCommentList(view).get(row);//get the comment being replied to
				submission.getSubmission().setIndentLevel(parent.getIndentLevel() + 1);//set the indent level of the new comment to be 1 more than the one being replied to
				
				
				//TODO: For the moment, don't add any comments if their indent is beyond what is in comment_view.xml. Can be dealt with later.
				if (submission.getSubmission().getIndentLevel() <= 5){
					parent.addChild(submission.getSubmission());
					factory.requestPost(parent.getID(), submission.getSubmission());
				}
	
				break;

			case(2)://edit topic

				//TODO: no username update support at the moment
				cl.getElement(view).setUsername(submission.getSubmission().getUsername());
				cl.getElement(view).setCommentText(submission.getSubmission().getCommentText());
				cl.getElement(view).setLocation(submission.getSubmission().getGPSLocation());
				cl.getElement(view).setImage(submission.getSubmission().getImage());
				Log.w("UpdateTest", cl.getElement(view).getID());
				//TODO: it wasn't anticipated that more than one field would be set at a time
				factory.requestImageUpdate(submission.getSubmission());
				factory.requestLocationUpdate(submission.getSubmission());
				factory.requestTextUpdate(submission.getSubmission());
				break;

			case(3): //edit comment
				
				//TODO: no username update support at the moment
				cl.getCommentList(view).get(row).setUsername(submission.getSubmission().getUsername());
				cl.getCommentList(view).get(row).setCommentText(submission.getSubmission().getCommentText());
				cl.getCommentList(view).get(row).setGPSLocation(submission.getSubmission().getGPSLocation());
				cl.getCommentList(view).get(row).setImage(submission.getSubmission().getImage());
				
				//TODO: it wasn't anticipated that more than one field would be set at a time
				factory.requestImageUpdate(submission.getSubmission());
				factory.requestLocationUpdate(submission.getSubmission());
				factory.requestTextUpdate(submission.getSubmission());
				break;

			default:
				Log.d("onActivityResult", "Error adding comment reply");
			}

			cl.updateCommentList(view); //this will update the comment list being displayed to show the new changes
			view.finish();
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

		submission_ok = checkSubmission(submission.getSubmission()); //check that the submission is valid
		if (submission_ok){
			CommentTree cl = CommentTree.getInstance();
			cl.addElementToCurrent(submission.getSubmission());
			DataStorageService dss = DataStorageService.getInstance();
			new TaskFactory(dss).requestPost("ROOT", submission.getSubmission());
			view.finish();
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
	public boolean checkSubmission(CommentTreeElement submission){

		boolean submission_ok = true;
		Toast toast = null;
		Context context = view.getApplicationContext();
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

	public CommentTreeElementSubmission getSubmission(){
		return submission;
	}
	
	//extract the information that the user has entered
	public void extractTextFields(){


		//Constructing a topic(0)
		if (constructCode == 0){

			//only get the title if it's a topic
			EditText text = (EditText) view.findViewById(R.id.setTitle);
			submission.getSubmission().setTitle(text.getText().toString().trim());

			text = (EditText) view.findViewById(R.id.setTopicUsername);
			submission.getSubmission().setUsername(text.getText().toString().trim());

			text = (EditText) view.findViewById(R.id.setTopicText);
			submission.getSubmission().setCommentText(text.getText().toString().trim());
		}

		//Constructing a comment(1), editing a comment(2), or editing a topic(3)
		else{

			EditText text = (EditText) view.findViewById(R.id.set_comment_username);
			submission.getSubmission().setUsername(text.getText().toString().trim());

			text = (EditText) view.findViewById(R.id.set_comment_text);
			submission.getSubmission().setCommentText(text.getText().toString().trim());
		}



	}


	/**
	 * Constructs the comment/topic to be submitted by checking the
	 * construct code
	 */
	public void constructSubmission(){

		
		if (constructCode == 0){
			//Add a title if a NEW TOPIC is being made
			submission.setSubmission(new Topic());
		}
		else if(constructCode == 3)
		{
			//A TOPIC is being EDITED
			submission.setSubmission(CommentTree.getInstance().getElement(view));
		}
		else if(constructCode == 1){
			//A NEW COMMENT is being made
			submission.setSubmission(new Comment());
		}
		else
		{
			//else a COMMENT is being EDITED
			submission.setSubmission(CommentTree.getInstance().getCommentList(view).get(rowNumber));
		}
		
		if(submission.getUserLocation() == null){
			submission.getSubmission().setGPSLocation(submission.getGpsLocation());
		} else {
			submission.getSubmission().setGPSLocation(submission.getUserLocation());
		}


		//username defaults to Anonymous if left blank
		if (submission.getSubmission().getUsername().length() == 0){
			submission.getSubmission().setAnonymous();

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
		this.view.startActivityForResult(Intent.createChooser(intent, "Gallery"), CreateSubmissionView.PICK_FROM_FILE);
	}
	
	public void registerImage(Bitmap bitmap)
	{
		ImageAttacher attacher = new ImageAttacher(submission, bitmap);
		attacher.execute();
	}
	
	/**
	 * Once location button is clicked we check if our user is online, if so we pop up a map 
	 * to edit location otherwise we open a dialog fragment for offline location editing
	 * @param view
	 */
	public void openMap(View view) {
		if(DataStorageService.getInstance().isOnline()){
			Intent map = new Intent(this.view, MapViewController.class);
			map.putExtra("lat", submission.getGpsLocation().getLatitude()); 
			map.putExtra("lon", submission.getGpsLocation().getLongitude());
			map.putExtra("canSetMarker", 1);
			map.putExtra("updateRank", this.view.getRank().getRank());
			this.view.startActivityForResult(map, CreateSubmissionView.REQUEST_CODE); 
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.view);

			LayoutInflater inflater = this.view.getLayoutInflater();
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
					float latitude = Float.parseFloat(text.getText().toString().trim());
					text = (EditText) dialogView.findViewById(R.id.offlineLongitude);
					float longitude = Float.parseFloat(text.getText().toString().trim());
					submission.setUserLocation(new Location("New"));
					submission.getUserLocation().setLatitude(latitude);
					submission.getUserLocation().setLongitude(longitude);

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


}