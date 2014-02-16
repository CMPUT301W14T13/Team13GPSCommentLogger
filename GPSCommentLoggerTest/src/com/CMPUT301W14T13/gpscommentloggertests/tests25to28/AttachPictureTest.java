package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import com.CMPUT301W14T13.gpscommentslogger.Comment;

import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

public class AttachPictureTest extends ActivityInstrumentationTestCase2<Comment> {

	public AttachPictureTest(String name) {
		super(Comment.class);
	}
	
	public void testAttachPicture () {
		// Goal: create bitmap, attach to comment, check if comment picture has same hash code
		
		// Create Bitmap object
		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888);
		
		// Create new comment
		Comment comment = new Comment();
		
		// Attach picture to comment
		comment.attach(picture);
		
		// Check last child
		assertEquals("Picture hash codes should be the same", picture.hashCode(), comment.getPicture().hashCode());
	}

}
