package com.CMPUT301W14T13.gpscommentsloggertests.tests25to28;

import com.CMPUT301W14T13.gpscommentslogger.Comment;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

public class AttachPictureTest extends ActivityInstrumentationTestCase2<Comment> {

	public AttachPictureTest(String name) {
		super(Comment.class);
	}
	
	public void testAttachPicture () {
		// Goal: create bitmap, attach to comment, check if comment attachment check returns true
		
		// Create Bitmap object
		Bitmap picture = new Bitmap();
		
		// Create new comment
		Comment comment = Comment();
		
		// Attach picture to comment
		comment.attach(picture);
		
		// Check last child
		assertTrue("Comment attachment check should return true", comment.checkAttachment());
	}

}
