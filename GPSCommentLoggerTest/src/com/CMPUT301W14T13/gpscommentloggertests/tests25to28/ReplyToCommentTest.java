package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.CMPUT301W14T13.gpscommentlogger.controller.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;

/**
 * Test to ensure that we can reply to comments
 * 
 * @author mjnichol
 *
 */
@SuppressLint("NewApi")
public class ReplyToCommentTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	CreateSubmissionActivity activity;
	Intent intent;
	CommentLogger cl;
	
	public ReplyToCommentTest() {
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
	public void testTopicReply() throws Throwable {

		
		intent.putExtra("construct code", 1);
		intent.putExtra("submit code", 0);
		activity = getActivity();

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				assertNotNull(activity);
				
				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);

				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("User1");
				commentText.setText("text");

				submitButton.performClick();

				Comment comment = (Comment) cl.getCommentList().get(0);

				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());


			}
		});
	}

	public void testCommentReply() throws Throwable {


		intent.putExtra("construct code", 1);
		intent.putExtra("submit code", 1);
		intent.putExtra("row number", 0);
		activity = getActivity();

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				assertNotNull(activity);
				
				cl.addComment(new Comment());
				
				
				EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
				EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
				Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);

				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("User2");
				commentText.setText("text2");

				submitButton.performClick();

				Comment comment = (Comment) cl.getCommentList().get(1);

				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
				assertEquals("Indent should be 1", 1, comment.getIndentLevel());

			}
		});
	}
}
