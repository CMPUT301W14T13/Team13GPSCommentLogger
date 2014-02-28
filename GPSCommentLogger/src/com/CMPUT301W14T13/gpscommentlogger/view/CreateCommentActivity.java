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
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;



public class CreateCommentActivity extends Activity
{
	
	private Intent intent;
	private int rowNumber;
	private String username;
	private String commentText;
	private boolean submission_ok;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_comment);
        
        intent = getIntent();
        rowNumber = intent.getIntExtra("row number", -1);
        
	}
	
	
public void handleTextFields(){
		
		EditText text = (EditText) findViewById(R.id.set_comment_username);
		username = text.getText().toString().trim();
		
		text = (EditText) findViewById(R.id.set_comment_text);
		commentText = text.getText().toString().trim();
		
		
	}
	
	//check that text fields are valid
	public void checkTextFields(){
		
		submission_ok = true;
		Context context = getApplicationContext();
		String text = "";
		int duration = Toast.LENGTH_LONG;
		
		
		if (username.length() == 0){
			username = "Anonymous";
		
		}
		
		if (commentText.length() == 0){
			text += "Comment cannot be blank";
			submission_ok = false;
		}
		
		if (!submission_ok){
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		
	}
	
	public void submitReply(View v){
		
		handleTextFields();
		checkTextFields();
		
		if (submission_ok){
			
			Comment comment = new Comment();
			comment.setUsername(username);
			comment.setCommentText(commentText);
			
			intent.putExtra("comment", comment);
			intent.putExtra("row number", rowNumber);
			
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	

	
	
}


