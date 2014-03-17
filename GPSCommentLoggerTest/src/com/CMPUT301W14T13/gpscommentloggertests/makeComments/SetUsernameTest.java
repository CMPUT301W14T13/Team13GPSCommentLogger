package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;

@SuppressLint("NewApi")
public class SetUsernameTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public SetUsernameTest() {
		super(DebugActivity.class);
	}

	
	/*
	 *  Test to check if the default username is
	 *  used for the comment if the user does not
	 *  provide one.
	 */
	
	public void testDefaultUsername(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		
		//create new comment with default username
		Comment comment = new Comment();
		
		assertEquals("Username should be default username", "Anonymous", comment.getUsername());
		
	}
	
	/*
	 * Check if the username that was entered by the
	 * user is stored in the comment
	 */
	public void testSetUsername(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		String username = "Austin";
		
		//create new comment with username
		Comment comment = new Comment(username, true);
		
		assertEquals("Username should be what was entered", "Austin", comment.getUsername());
	}
}
