package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

@SuppressLint("NewApi")
public class ReplyToCommentTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public ReplyToCommentTest(String name) {
		super(DebugActivity.class);
	}
	
	public void testCommentReply () {
		//Goal: create comment, reply to it, check that reply is a child of the comment
		
		// Create reply string
		String replyText = "My reply";
		
		// Create new comment
		Comment comment = new Comment();
		Comment reply = new Comment();
		// Reply to comment
		comment.getChildren().add(reply);
		
		// Check last child
		assertEquals("Comment reply should appear as latest comment child", replyText,
				comment.getChildren().get(comment.getNumberOfReplies()).getCommentText());
		
		
	}

}
