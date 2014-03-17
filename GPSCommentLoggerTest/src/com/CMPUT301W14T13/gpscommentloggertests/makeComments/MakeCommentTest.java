package com.CMPUT301W14T13.gpscommentloggertests.makeComments;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;

<<<<<<< HEAD
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.view.CreateSubmissionActivity;
=======
import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
>>>>>>> eb5e102a38c2df097a22781d16df567129c5f629

@SuppressLint("NewApi")
public class MakeCommentTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	
	public MakeCommentTest() {
		super(CreateSubmissionActivity.class);
	}
	
	
	/*
	 * Certain fields in a comment cannot be null. The only field
	 * allowed to be null is the comment's text, but only if
	 * it has an attached picture
	 */
	public void testNullFields(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		CreateSubmissionActivity activity = getActivity();

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
		//
		Comment comment = new Comment();
		
		
		assertNotNull(comment.getID());
		assertNotNull(comment.getUsername());
		assertNotNull(comment.getTimestamp());
		//assertNotNull(comment.getGPS());
		
		if (comment.getCommentText() == null){
			assertEquals("If comment text is empty, then it must have a picture", true, 
							comment.getHasImage());
		}
		
		
		
		
	}
	
	
	/*
	 * test to check that comment fields are correctly set
	 * by comparing the values used to create the comment with
	 * what is actually in the comment
	 */
	public void testMakeTopic(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		CreateSubmissionActivity activity = getActivity();

		assertNotNull(activity);
		
		CommentLogger cl = CommentLoggerApplication.getCommentLogger();
		CommentLoggerController controller = new CommentLoggerController(cl);
		
		
		/*
		 * various fields for making a comment that can be
		 * used for testing the asserts below 
		 */
		
		String ID = "4324";
		String username = "Austin";
		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //must add arguments
		Date timestamp = new Date();
		String commentText = "Test comment";
		//
		Comment comment = new Comment(ID, username, picture, timestamp, commentText);
		
		
		
		
		
		assertEquals("Comment IDs should be the same", ID, comment.getID());
		assertEquals("Usernames should be the same", username, comment.getUsername());
		assertEquals("Picture should be the attached picture", picture.hashCode(), 
						comment.getImage().hashCode());
		assertEquals("Timestamps should be the same", timestamp, comment.getTimestamp());
		assertEquals("Comment text should be the same", commentText, comment.getCommentText());
		//assertEquals("GPS coordinates should be the same", GPS, comment.getGPS());
		
	}
}
