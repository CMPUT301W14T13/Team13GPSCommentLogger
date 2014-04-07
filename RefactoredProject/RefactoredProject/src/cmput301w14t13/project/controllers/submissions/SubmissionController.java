package cmput301w14t13.project.controllers.submissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cmput301w14t13.project.R;
import cmput301w14t13.project.controllers.ImageAttachmentController;
import cmput301w14t13.project.controllers.MapViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.services.NetworkReceiver;
import cmput301w14t13.project.services.SubmissionMediator;
import cmput301w14t13.project.views.submissions.SubmissionView;
/**
 * abstract Class used for Creating and editting our Topics
 * and Comments, sets up the view necessary for the user
 * to input the information required, then checks the user 
 * submitted information for validity and then submits the 
 * new information in a method appropriate with what the user 
 * is doing (e.g. Creating or editing)
 * 
 * @author nsd
 *
 */
public abstract class SubmissionController {

	protected SubmissionView view;
	protected CommentTreeElementSubmission submission;
	protected static final int REQUEST_CODE = 1; 
	
	public SubmissionController(SubmissionView view) {
		this.view = view;
		this.submission = new CommentTreeElementSubmission(view);
	}
	/**
	 * Constructs the CommentTreeElementSubmission with either a 
	 * existing CommentTreeElement when editting or a new 
	 * CommentTreeElement when Creating a Comment or Topic
	 * Then initializes the Fields for user input when Creating 
	 * or editing Topics or Comments then constructs the layout
	 * for the user to see 
	 * finally sets up an initial location 
	 * 
	 */
	public void initialize()
	{
		constructSubmissionData();
		initializeFields();
		initializeLayout();
		initializeLocation();
	}

	protected abstract void constructSubmissionData();
	
	/**
	 * sets up the initial gps location and user locaiton
	 * gps location is grabbed from LocationSelections which
	 * gets it from the gps. If LocationSelection returns a null
	 * (gps has not provided us with a location) we set the location
	 * to a default location. User Location is set to null and changes
	 * when/if the user sets their own location
	 * 
	 */
	protected void initializeLocation() {
		//mapLocation does not have listener attached so it only changes when mapActivity returns a result
		submission.setGpsLocation(LocationSelection.getInstance().getLocation());
		
		/* if you cannot get a GPS fix set the values to : 0,0*/
		if (submission.getGpsLocation() == null){
			submission.setGpsLocation(new Location("fake"));
			submission.getGpsLocation().setLatitude(0);
			submission.getGpsLocation().setLongitude(0);
		}
		submission.setUserLocation(null);
	}

	protected void initializeFields() {
		//get the user's global user name so they don't have to always enter it
		submission.getSubmission().setUsername(CommentTree.getInstance().getCurrentUsername());
	}

	
	protected abstract void initializeLayout();

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
			processSubmit();
			CommentTree.getInstance().updateCommentList(view); //this will update the comment list being displayed to show the new changes
			view.finish();
		}
	}

	protected abstract void processSubmit();

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
	protected abstract boolean checkSubmission(CommentTreeElement submission);
	
	//extract the information that the user has entered
	protected abstract void extractTextFields();
	
	public CommentTreeElementSubmission getSubmission()
	{
		return submission;
	}

	/**
	 * constructs our Submission from user inputted information
	 */
	protected void constructSubmission(){
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
	 * Starts ImageAttachmentController to handle the 
	 * attaching of a image to the Topic or comment
	 * The AttachmentController opens a gallery for 
	 * the user to select from their library of photos
	 * then handles everything necessary to convert the 
	 * image into a valid bitmap before attaching the image
	 * to the Topic or Comment
	 * 
	 */
	public void attachImage(View view) {
		SubmissionMediator.setSubmission(submission);
		Intent intent = new Intent(this.view, ImageAttachmentController.class);
		this.view.startActivity(intent);
	}
	
	/**
	 * Used for User input of location, if online a map is 
	 * given for the user to place a marker at his desired 
	 * location, if offline a dialog fragment pops up for
	 * manual input of location
	 * @param view
	 */
	public void openMap(View view) {
		if(NetworkReceiver.isConnected){
			Intent map = new Intent(this.view, MapViewController.class);
			map.putExtra("lat", submission.getGpsLocation().getLatitude()); 
			map.putExtra("lon", submission.getGpsLocation().getLongitude());
			map.putExtra("canSetMarker", 1);
			map.putExtra("updateRank", this.view.getRank().getRank());
			this.view.startActivityForResult(map, REQUEST_CODE); 
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
