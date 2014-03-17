package com.CMPUT301W14T13.gpscommentloggertests.tests25to28;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;

@SuppressLint("NewApi")
public class AttachPictureTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public AttachPictureTest() {
		super(DebugActivity.class);
	}
	
	public void testAttachPicture () {
		// Goal: create bitmap, attach to comment, check if comment picture has same hash code
		
		// Create Bitmap object
		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888);
		
		// Create new comment
		Comment comment = new Comment();
		
		// Attach picture to comment
		comment.setImage(picture);
		
		// Check last child
		assertEquals("Picture hash codes should be the same", picture.hashCode(), comment.getImage().hashCode());
	}

}
