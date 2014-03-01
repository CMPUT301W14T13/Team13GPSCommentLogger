package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.SubmissionController;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;

public class CreateTopicActivity extends Activity{

	private String username;
	private String title;
	private String commentText;
	private SubmissionController controller;
	private boolean hasTitle = true;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_topic);
        
    }
    
	//extract the information that the user has entered
	public void ExtractTextFields(){
		
		EditText text = (EditText) findViewById(R.id.setTitle);
		title = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.setUsername);
		username = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.setCommentText);
		commentText = text.getText().toString().trim();
	}
	
	
	
	
	public void submitTopic(View v){
		
		Intent submit = getIntent();
		Context context = getApplicationContext();
		controller = new SubmissionController();
		boolean submission_ok;
		
		ExtractTextFields();
		
		submission_ok = controller.checkTextFields(context, hasTitle, title, username, commentText);
		if (submission_ok){
			Topic topic = new Topic();
			
			topic.setTitle(title);
			topic.setUsername(username);
			topic.setCommentText(commentText);
			
			submit.putExtra("Topic", topic); 
			setResult(RESULT_OK, submit);
			finish();
		}
	}
	
}
