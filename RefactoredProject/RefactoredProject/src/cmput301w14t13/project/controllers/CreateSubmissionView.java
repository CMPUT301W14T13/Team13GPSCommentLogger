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
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
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
 * This is where the user can construct a new submission or edit a previous one. This activity uses a construct code which tells it how to build the submission, and a submit code which tells it how to submit the submission. It also uses the CheckSubmission function to check if the submission is a valid one. A valid comment must have comment text and a  valid topic must have comment text and a title. The username will default to "Anonymous" if left empty.
 * @author  Austin
 */
public class CreateSubmissionView extends RankedHierarchicalActivity implements UpdateInterface{

	
	public static final int REQUEST_CODE = 1;
	public static final int PICK_FROM_FILE = 2;
	

	private CreateSubmissionController controller;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create the location stuff up here */

		int constructCode = getIntent().getIntExtra("construct code", -1);
		int submitCode = getIntent().getIntExtra("submit code", -1);
		int rowNumber = getIntent().getIntExtra("row number", -1);
		
		controller = new CreateSubmissionController(this, constructCode, submitCode, rowNumber);
		
		controller.initializeLocation();
		controller.initializeFields();
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
				controller.getSubmission().setUserLocation(controller.getSubmission().getGpsLocation());
			}
		}

		if (requestCode == PICK_FROM_FILE) {
			if (resultCode == RESULT_OK){

				Uri selectedImageUri = data.getData();


				try {
					controller.getSubmission().getSubmission().setImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				Log.d("Image Attach", "Image received: " + controller.getSubmission().getSubmission().getImage().toString());
				ImageButton attachButton = (ImageButton) findViewById(R.id.imageButton1); // set attach button to image selected

			}
		}

	}


	@Override
	public void onDestroy(){
		super.onDestroy();
		lm.removeUpdates(ll);
		lm = null;
	}

	@Override
	public void update() {
	}


	@Override
	public UpdateRank getRank() {
		return rank;
	}
	
}




