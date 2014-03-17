package com.CMPUT301W14T13.gpscommentloggertests.makeComments;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
/**
 * 
 * Test to ensure that the submission are being created correctly
 * @author mjnichol
 *
 */

@SuppressLint("NewApi")
public class MakeSubmissionTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	CreateSubmissionActivity activity;
	
	public MakeSubmissionTest() {
		super(CreateSubmissionActivity.class);
	}
	
	
	/**
	 * Certain fields in a comment cannot be null. The only field
	 * allowed to be null is the comment's text, but only if
	 * it has an attached picture
	 */
	
	
	public void testNullFields(){
		
	/*	Intent intent = new Intent();
		setActivityIntent(intent);
		*/
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
	
	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}
	
	/**
	 * test to check that comment fields are correctly set
	 * by comparing the values used to create the comment with
	 * what is actually in the comment
	 */
	public void testCommentFields(){

		Intent intent = new Intent();
		setActivityIntent(intent);
		CreateSubmissionActivity activity = getActivity();

		assertNotNull(activity);


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
	
	
	/**
	 * test to check that comment fields are correctly set
	 * by comparing the values used to create the comment with
	 * what is actually in the comment
	 */
	public void testMakeTopic() throws Throwable {
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
		
				Intent intent = new Intent();
				setActivityIntent(intent);
				intent.putExtra("construction code", 0);
				
				assertNotNull(activity);
				CommentLogger cl = CommentLoggerApplication.getCommentLogger();
				
				//	View commentLayout = (View) activity.findViewById(R.id.create_topic);
				/*
				EditText title = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.setTitle);
				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.setTopicUsername);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.setTopicText);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);
				 */
				EditText title = (EditText) activity.findViewById(R.id.setTitle);
				EditText username = (EditText) activity.findViewById(R.id.setTopicUsername);
				EditText commentText = (EditText) activity.findViewById(R.id.setTopicText);		
				Button submitButton = (Button) activity.findViewById(R.id.submit);
				
				assertNotNull(title);
				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				title.setText("title");
				username.setText("User1");
				commentText.setText("text");

				submitButton.performClick();

				Topic topic = (Topic) cl.getTopics().get(0);

				assertEquals("Titles not the same", topic.getTitle(), title.toString() );
				assertEquals("Usernames not the same", topic.getUsername(), username.toString());
				assertEquals("Texts not the same", topic.getCommentText(), commentText.toString());


			}
		});
	}
}