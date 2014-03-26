package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;

/**
 * Check to ensure that the comments and topics can be edited
 * 
 * @author mjnichol, arweber
 *
 */

@SuppressLint("NewApi")
public class EditSubmissionTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	CreateSubmissionActivity activity;
	Intent intent;
	int constructCode;
	int submitCode;
	CommentLogger cl;
	
	public EditSubmissionTest() {
		super(CreateSubmissionActivity.class);
	}


	@Before
	public void setUp() throws Exception {
		super.setUp();
		intent = new Intent();
		setActivityIntent(intent);
		
		cl = CommentLogger.getInstance();
		cl.getTopics().clear();
		cl.addTopic(new Topic());
		cl.setCurrentTopic(0);
		
	}

	/**
	 * A comment's text will be edited if the new text that
	 * was set in the comment is the same as the text that
	 * is retrieved from the comment.
	 * @throws Throwable 
	 */
	public void testEditTopic() throws Throwable{

		intent.putExtra("construct code", 3);
		intent.putExtra("submit code", 2);
		cl.getCurrentTopic().setImage(Bitmap.createBitmap(1,1, Config.ARGB_8888));
		activity = getActivity();
		assertNotNull(activity);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);
				ImageButton attachImage = (ImageButton) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.imageButton1);
				
				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);
				assertNotNull(attachImage);
				
				username.setText("User1");
				commentText.setText("text");
				
				
				
				submitButton.performClick();
				
				Bitmap bitmap = Bitmap.createBitmap(5,2, Config.ARGB_8888);
				cl.getCurrentTopic().setImage(bitmap);
				Topic topic = cl.getCurrentTopic();

				assertEquals("Usernames wasn't edited", topic.getUsername(), username.getText().toString());
				assertEquals("Text wasn't edited", topic.getCommentText(), commentText.getText().toString());
				assertEquals("Image wasn't edited", topic.getImage(), bitmap);

			}
		});
	}



	public void testEditComment() throws Throwable{

		intent.putExtra("construct code", 2);
		intent.putExtra("submit code", 3);
		intent.putExtra("row number", 0);
		
		cl.addComment(new Comment());
		cl.getTopicChildren().get(0).setImage(Bitmap.createBitmap(1,1, Config.ARGB_8888));
		activity = getActivity();
		assertNotNull(activity);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);

				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("User2");
				commentText.setText("text2");

				submitButton.performClick();

				Bitmap bitmap = Bitmap.createBitmap(5,2, Config.ARGB_8888);
				cl.getTopicChildren().get(0).setImage(bitmap);
				Comment comment = (Comment) cl.getTopicChildren().get(0);

				assertEquals("Usernames wasn't edited", comment.getUsername(), username.getText().toString());
				assertEquals("Text wasn't edited", comment.getCommentText(), commentText.getText().toString());
				assertEquals("Image wasn't edited", comment.getImage(), bitmap);


			}
		});
	}

	/**
	 * To test if a picture was added, the hashcode of the picture
	 * created and the hashcode of the picture in the comment can be
	 * compared to see if they are the same.
	 */

	public void testEditPicture(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		CreateSubmissionActivity activity = getActivity();

		assertNotNull(activity);

		//Viewable comment = activity.getSubmission();
		Viewable comment = new Comment();

		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //put some arguments in here to create a bitmap
		comment.setImage(picture);

		assertEquals("Picture hash codes should be equal", picture.hashCode(),
				comment.getImage().hashCode());
	}




}
