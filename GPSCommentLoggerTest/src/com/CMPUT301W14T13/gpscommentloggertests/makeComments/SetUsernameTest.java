package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;

public class SetUsernameTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public SetUsernameTest() {
		super(DebugActivity.class);
	}

	
	
	public void testDefaultUsername(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		
		//create new comment with default username
		Comment comment = new Comment();
		
		assertEquals("Username should be default username", "Anonymous", comment.getUsername());
		
	}
	
	
	public void testSetUsername(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		String username = "Austin";
		
		//create new comment with username
		Comment comment = new Comment(username);
		
		assertEquals("Username should be what was entered", "Austin", comment.getUsername());
	}
}
