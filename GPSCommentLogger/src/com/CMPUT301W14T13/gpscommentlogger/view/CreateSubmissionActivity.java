package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.SubmissionController;
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
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        code = getIntent().getIntExtra("code", -1);
        switch(code){
        
        case(0):
        	setContentView(R.layout.create_topic); //creating a topic
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        	break;
        
        case(1):
        	setContentView(R.layout.create_comment); //creating a comment
        	rowNumber = getIntent().getIntExtra("row number", -1);
        	break;
        
        case(2):
        	setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)
    		rowNumber = getIntent().getIntExtra("row number", -1);
    		submission = getIntent().getParcelableExtra("submission");
    		
    		text = (EditText) findViewById(R.id.set_comment_text);
    		text.setText(submission.getCommentText());
    		
    		text = (EditText) findViewById(R.id.set_comment_username);
    		text.setText(submission.getUsername());
    		extractTextFields();
    		
        
        }
    }
    
	//extract the information that the user has entered
	public void extractTextFields(){
		
		
		//Making a topic
		if (code == 0){
			text = (EditText) findViewById(R.id.setTitle);
			title = text.getText().toString().trim();
			
			text = (EditText) findViewById(R.id.setTopicUsername);
			username = text.getText().toString().trim();
			
			text = (EditText) findViewById(R.id.setTopicText);
			commentText = text.getText().toString().trim();
		}
		
		//Making a comment
		if (code == 1 || code == 2 ){
			
			text = (EditText) findViewById(R.id.set_comment_username);
			username = text.getText().toString().trim();
			
			text = (EditText) findViewById(R.id.set_comment_text);
			commentText = text.getText().toString().trim();
		}
		
	}
	
	
	
	public void constructSubmission(){
		
		//Add a title if a topic is being made
		if (code == 0){
			submission = new Topic();
			submission.setTitle(title);
		}
		else{
			submission = new Comment();
		}
		
		submission.setUsername(username);
		submission.setCommentText(commentText);
		
		if (username.length() == 0){
			submission.setAnonymous();
		
		}
		
	}
	
	public void submit(View v){
		
		Intent submit = getIntent();
		Context context = getApplicationContext();
		controller = new SubmissionController();
		boolean submission_ok;
		
		extractTextFields();
		constructSubmission();
		
		submission_ok = controller.checkSubmission(context, submission); //check that the submission is valid
		if (submission_ok){
			
			switch(code){
			
			case(0):
				submit.putExtra("Topic", (Topic) submission); 
				break;
			
			case(1):
			case(2):
				submit.putExtra("comment", (Comment) submission);
				submit.putExtra("row number", rowNumber);
				
				
			}
			
			setResult(RESULT_OK, submit);
			finish();
		}
	}
	
}
