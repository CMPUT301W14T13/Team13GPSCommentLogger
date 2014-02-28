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
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;

public class CreateTopicActivity extends Activity{

	private String username;
	private String title;
	private String commentText;
	private boolean submission_ok;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_topic);
      
    }
    
	//extract the information that the user has entered
	public void handleTextFields(){
		
		EditText text = (EditText) findViewById(R.id.setTitle);
		title = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.setUsername);
		username = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.setCommentText);
		commentText = text.getText().toString().trim();
	}
	
	//check that text fields are valid
	public void checkTextFields(){
		
		submission_ok = true;
		Context context = getApplicationContext();
		String text = "";
		int duration = Toast.LENGTH_LONG;
		
		
		if (title.length() == 0){
			text += "Title cannot be blank";
			submission_ok = false;
		}
		
		if (username.length() == 0){
			username = "Anonymous";
		
		}
		
		if (commentText.length() == 0){
			text += "\nComment cannot be blank";
			submission_ok = false;
		}
		
		if (!submission_ok){
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		
	}
	
	public void attachPicture(View v){
		
	}
	
	public void setCoordinates(View v){
		
	}
	
	public void submitTopic(View v){
		
		Intent submit = getIntent();
		
		handleTextFields();
		checkTextFields();
		
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
