package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.CreateSubmissionActivity;

/**
 * Check to ensure that the comments and topics can be edited
 * 
 * @author mjnichol, arweber
 *
 */

@SuppressLint("NewApi")
public class EditCommentTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	public EditCommentTest() {
		super(CreateSubmissionActivity.class);
	}

	Activity activity;

	public void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}

	/**
	 * A comment's text will be edited if the new text that
	 * was set in the comment is the same as the text that
	 * is retrieved from the comment.
	 */
	public void testEditCommentText(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		CreateSubmissionActivity activity = getActivity();

		assertNotNull(activity);

		//Viewable comment = activity.getSubmission();

		Viewable comment = new Comment();
		comment.setCommentText("test");
		String newText = "Test Edit";

		assertEquals("Comment text should be 'test'", "test", comment.getCommentText());
		comment.setCommentText(newText);
		assertEquals("Comment text should be edited", newText, comment.getCommentText());

	}

	/**
	 * A comment's text will be edited if the new text that
	 * was set in the comment is the same as the text that
	 * is retrieved from the comment.
	 */
	public void testEditTopicText(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		CreateSubmissionActivity activity = getActivity();

		assertNotNull(activity);

		//Viewable comment = activity.getSubmission();

		Viewable topic = new Topic();
		topic.setCommentText("test");
		String newText = "Test Edit";

		assertEquals("Comment text should be 'test'", "test", topic.getCommentText());
		topic.setCommentText(newText);
		assertEquals("Comment text should be edited", newText, topic.getCommentText());

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
