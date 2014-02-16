package com.CMPUT301W14T13.gpscommentsloggertests.tests25to28;

import com.CMPUT301W14T13.gpscommentslogger.Comment;

import android.test.ActivityInstrumentationTestCase2;

public class ReplyToCommentTest extends ActivityInstrumentationTestCase2<Comment> {

	public ReplyToCommentTest(String name) {
		super(Comment.class);
	}
	
	public void testCommentReply () {
		//Goal: create comment, reply to it, check that reply is a child of the comment
		
		// Create reply string
		String replyText = "My reply";
		
		// Create new comment
		Comment comment = new Comment();
		
		// Reply to comment
		comment.reply(replyText);
		
		// Check last child
		assertEquals("Comment reply should appear as latest comment child", replyText,
				comment.getChildren.getPosition(getChildren().lenghth()));
		
		
	}

}
