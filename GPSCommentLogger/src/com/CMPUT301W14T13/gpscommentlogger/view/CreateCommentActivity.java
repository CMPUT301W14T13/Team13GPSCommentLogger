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



public class CreateCommentActivity extends Activity
{
	
	private Intent intent;
	private int rowNumber;
	private String username;
	private String commentText;
	SubmissionController controller;
	private String title = null;
	private boolean hasTitle = false;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_comment);
        
        intent = getIntent();
        rowNumber = intent.getIntExtra("row number", -1);
        
	}
	
	
	public void ExtractTextFields(){
		
		EditText text = (EditText) findViewById(R.id.set_comment_username);
		username = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.set_comment_text);
		commentText = text.getText().toString().trim();
		
		
	}
	
	
	public void submitReply(View v){
		
		Context context = getApplicationContext();
		boolean submission_ok;
		controller = new SubmissionController();
		
		ExtractTextFields();
		
		submission_ok = controller.checkTextFields(context, hasTitle, title, username, commentText);
		if (submission_ok){
			
			if (username.length() == 0){
				username = "Anonymous";
			
			}
			
			//create comment
			Comment comment = new Comment();
			comment.setUsername(username);
			comment.setCommentText(commentText);
			
			//send comment back as well as which comment it belongs to
			intent.putExtra("comment", comment);
			intent.putExtra("row number", rowNumber);
			
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	

	
	
}


