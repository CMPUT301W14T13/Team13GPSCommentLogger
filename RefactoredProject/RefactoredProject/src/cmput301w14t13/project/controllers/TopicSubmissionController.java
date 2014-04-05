package cmput301w14t13.project.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.tools.ImageAttacher;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.CreateSubmissionView;
import cmput301w14t13.project.views.TopicSubmissionView;

public abstract class TopicSubmissionController {

	protected TopicSubmissionView view;
	protected CommentTreeElementSubmission submission;
	
	public TopicSubmissionController(TopicSubmissionView view) {
		this.view = view;
		this.submission = new CommentTreeElementSubmission(view);
	}
	
	protected abstract void constructSubmissionData();

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
	private boolean checkSubmission(CommentTreeElement submission){

		boolean submission_ok = true;
		Toast toast = null;
		Context context = view.getApplicationContext();
		String text = ""; 
		int duration = Toast.LENGTH_LONG;

		//check title
		if (submission.getTitle().length() == 0){
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
	protected abstract void extractTextFields();


	/**
	 * Constructs the comment/topic to be submitted by checking the
	 * construct code
	 */
	private void constructSubmission(){
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
		this.view.startActivity(Intent.createChooser(intent, "Gallery"));
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

}
