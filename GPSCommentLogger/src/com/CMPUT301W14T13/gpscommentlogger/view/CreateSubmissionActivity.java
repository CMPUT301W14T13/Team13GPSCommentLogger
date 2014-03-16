package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.SubmissionController;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentModelList;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

public class CreateSubmissionActivity extends Activity{

	private String username;
	private String title;
	private String commentText;
	private SubmissionController controller;
	private Viewable submission;
	private int code; //0: Creating topic, 1: Creating comment, 2: Editing
	private int rowNumber;
	private EditText text;
	private String currentUsername = "";
	private int code2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create the location stuff up here */

		code = getIntent().getIntExtra("code", -1);
		code2 = getIntent().getIntExtra("code 2", -1);

		switch(code){

			case(0):
				setContentView(R.layout.create_topic); //creating a topic
			getActionBar().setDisplayHomeAsUpEnabled(true);
			break;

			case(1):
				setContentView(R.layout.create_comment); //creating a comment
			rowNumber = getIntent().getIntExtra("row number", -1);
			currentUsername = getIntent().getExtras().getString("current username");

			text = (EditText) findViewById(R.id.set_comment_username);
			text.setText(currentUsername);
			break;

			//These cases are for editing a comment or topic
			case(2):
			case(3):
				setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)
			rowNumber = getIntent().getIntExtra("row number", -1);
			submission = getIntent().getParcelableExtra("submission");

			if (code == 3){ //The submission controller needs to check the title
				title = submission.getTitle();
			}

			text = (EditText) findViewById(R.id.set_comment_text);
			text.setText(submission.getCommentText());

			text = (EditText) findViewById(R.id.set_comment_username);
			text.setText(submission.getUsername());
			extractTextFields();


		}
	}

	//extract the information that the user hasGooglePlayServices entered
	public void extractTextFields(){


		//Making a topic(0)
		if (code == 0){
			text = (EditText) findViewById(R.id.setTitle);
			title = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.setTopicUsername);
			username = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.setTopicText);
			commentText = text.getText().toString().trim();
		}

		//Making a comment(1), editing a comment(2), or editing a topic(3)
		if (code == 1 || code == 2 || code == 3){

			text = (EditText) findViewById(R.id.set_comment_username);
			username = text.getText().toString().trim();

			text = (EditText) findViewById(R.id.set_comment_text);
			commentText = text.getText().toString().trim();
		}

	}


	/* creates the comment/topic to be submitted */
	/**
	 * creates the topic/topic to be submitted
	 */
	public void constructSubmission(){

		//Add a title if a topic is being made
		if (code == 0 || code == 3){
			submission = new Topic();
			submission.setTitle(title);
		}
		else{
			submission = new Comment();
		}

		submission.setUsername(username);
		submission.setCommentText(commentText);

		/* handle getting the GPS location now */
		//submission.setGPSLocation();

		if (username.length() == 0){
			submission.setAnonymous();

		}

	}

	/**
	 * 
	 * @param v is the submit button view
	 * Constructs the submission from the text fields
	 * in the view.
	 */
	public void submit(View v){

		Intent submit = getIntent();
		Context context = getApplicationContext();
		controller = new SubmissionController();
		boolean submission_ok;
		ArrayList<Viewable> commentList;

		extractTextFields();
		constructSubmission();

		submission_ok = controller.checkSubmission(context, submission); //check that the submission is valid
		if (submission_ok){

			
			
			
			
			int row = rowNumber;
			Comment prev_comment = new Comment();
			CommentModelList controller = new CommentModelList(CommentLoggerApplication.getCommentLogger());
			CommentLogger cl = CommentLoggerApplication.getCommentLogger();
			commentList = cl.getCommentList();
			
			
			switch (code2){
			
				case(0):  //reply to topic

					cl.addComment((Comment) submission);
					//commentList.add(comment);
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
			
			
				cl.updateTopicChildren(commentList); //this will update the topic's children to save any changes
				controller.updateCommentList();
			



			finish();
		}
	}

	
	public void submitTopic(View v){
		Intent submit = getIntent();
		Context context = getApplicationContext();
		controller = new SubmissionController();
		boolean submission_ok;
		ArrayList<Viewable> commentList;

		extractTextFields();
		constructSubmission();

		submission_ok = controller.checkSubmission(context, submission); //check that the submission is valid
		if (submission_ok){
			
			CommentLogger cl = CommentLoggerApplication.getCommentLogger();
			CommentModelList controller = new CommentModelList(cl);
			controller.addTopic((Topic) submission);
			
		}
		
		finish();
	}
}


