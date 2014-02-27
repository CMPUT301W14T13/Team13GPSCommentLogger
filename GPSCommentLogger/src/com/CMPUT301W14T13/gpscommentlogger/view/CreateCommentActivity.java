package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;



public class CreateCommentActivity extends Activity
{
	
	Intent intent;
	int rowNumber;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_comment);
        
        intent = getIntent();
        rowNumber = intent.getIntExtra("row number", -1);
        
	}
	
	
	
	public void submitReply(View v){
		
		Comment comment = new Comment();
		
		intent.putExtra("comment", comment);
		intent.putExtra("row number", rowNumber);
		
		setResult(RESULT_OK, intent);
		finish();
	}
	
	
	
	
	
	
	
}


