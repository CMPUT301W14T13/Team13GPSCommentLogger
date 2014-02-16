package com.CMPUT301W14T13.gpscommentlogger.test;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import android.graphics.Bitmap;

public class MakeCommentTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	
	public MakeCommentTest() {
		super(DebugActivity.class);
	}
	
	
	//Certain fields in a comment cannot be null
	public void testNullFields(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		
		/*
		 * various fields for making a comment that can be
		 * used for testing the asserts below 
		 */
		
		//int ID = 4324;
		//String username = "Austin";
		//Bitmap picture = Bitmap.createBitmap();
		//boolean hasPicture = false;
		//Date timestamp;
		//String commentText = "Test comment";
		
		Comment comment = new Comment();
		
		
		assertNotNull(comment.getID());
		assertNotNull(comment.getUsername());
		assertNotNull(comment.getTimestamp());
		
		
		if (comment.getCommentText == null){
			assertEquals("If comment text is empty, then it must have a picture", true, 
							comment.getHasPicture());
		}
		
		
		
		
	}
	
	
	//test to check that 
	public void testCommentFields(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		
		/*
		 * various fields for making a comment that can be
		 * used for testing the asserts below 
		 */
		
		int ID = 4324;
		String username = "Austin";
		Bitmap picture = Bitmap.createBitmap(); //must add arguments
		boolean hasPicture = false;
		Date timestamp;
		String commentText = "Test comment";
		
		Comment comment = new Comment();
		
		assertEquals("Comment IDs should be the same", ID, comment.getID());
		assertEquals("Usernames should be the same", username, comment.getUsername());
		assertEquals("Picture should be the attached picture", picture.hashCode(), 
						comment.getPicture().hashCode());
		assertEquals("Timestamps should be the same", timestamp, comment.getTimestamp());
		assertEquals("Comment text should be the same", commentText, comment.getCommentText());
		
		
	}
}
