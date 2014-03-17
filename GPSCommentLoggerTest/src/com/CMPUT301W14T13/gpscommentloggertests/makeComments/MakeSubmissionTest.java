package com.CMPUT301W14T13.gpscommentloggertests.makeComments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.view.CreateSubmissionActivity;


@SuppressLint("NewApi")
public class MakeSubmissionTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	
	public MakeSubmissionTest() {
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
	public void testMakeTopic() throws Throwable {
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
		
				Intent intent = new Intent();
				setActivityIntent(intent);
				CreateSubmissionActivity activity = getActivity();

				assertNotNull(activity);

				CommentLogger cl = CommentLoggerApplication.getCommentLogger();
				Root root = cl.getRoot();

				CommentLoggerController controller = new CommentLoggerController(cl);
				intent.putExtra("construction code", 0);

				EditText title = (EditText) activity.findViewById(R.id.topic_title);
				EditText username = (EditText) activity.findViewById(R.id.topic_username);
				EditText commentText = (EditText) activity.findViewById(R.id.topic_comment);		
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

				assertEquals("Title not the same", topic.getTitle(), title.toString() );
				assertEquals("User not the same", topic.getUsername(), username.toString());
				assertEquals("Text not the same", topic.getCommentText(), commentText.toString());


			}
		});
	}
}