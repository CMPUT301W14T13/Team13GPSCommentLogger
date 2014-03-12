package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/*
 * Controller for checking the text fields when creating and editing
 * comments and topics
 */

public class SubmissionController
{
	
	
	public boolean checkSubmission(Context context, Viewable submission){
		
		boolean submission_ok = true;
		Toast toast = null;
		String text = "";
		int duration = Toast.LENGTH_LONG;
		
		
		if (submission instanceof Topic && submission.getTitle().length() == 0){
			text += "Title cannot be blank";
			submission_ok = false;
		}
		
		if (submission.getCommentText().length() == 0){
			text += "\nComment cannot be blank";
			submission_ok = false;
		}
		
		if (!submission_ok){
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
			
			
		}
		
		return submission_ok;
	
	}
	
	
}
