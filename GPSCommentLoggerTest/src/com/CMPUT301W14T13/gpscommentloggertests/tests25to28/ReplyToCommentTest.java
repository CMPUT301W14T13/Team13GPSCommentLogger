package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;

/**
 * Test to ensure that we can reply to comments
 * 
 * @author mjnichol
 *
 */
@SuppressLint("NewApi")
public class ReplyToCommentTest extends ActivityInstrumentationTestCase2<CreateSubmissionActivity> {

	public ReplyToCommentTest() {
		super(CreateSubmissionActivity.class);
	}
	
	public void testCommentReply () {
		//Goal: create comment, reply to it, check that reply is a child of the comment
		
		// Create reply string
		String replyText = "My reply";
		
		// Create new comment
		Comment comment = new Comment();
		Comment reply = new Comment();
		reply.setCommentText(replyText);
		
		// Reply to comment
		comment.getChildren().add(reply);

		// Check last child		
		assertEquals("Comment reply should appear as latest comment child", replyText,
				comment.getChildren().get(comment.getNumberOfReplies()).getCommentText());
		
		
	}

}
