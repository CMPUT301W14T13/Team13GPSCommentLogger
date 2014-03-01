package com.CMPUT301W14T13.gpscommentlogger.model;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/*
 * Controller for checking the text fields when creating and editing
 * comments and topics
 */

public class SubmissionController
{

	
	
	
	//check that text fields are valid
		public boolean checkTextFields(Context context, boolean hasTitle, String title, String username, String commentText){
			
			boolean submission_ok = true;
			Toast toast = null;
			String text = "";
			int duration = Toast.LENGTH_LONG;
			
			
			if (hasTitle && title.length() == 0){
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
				toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
				toast.show();
				
			}
			
			return submission_ok;
		}
	
}
