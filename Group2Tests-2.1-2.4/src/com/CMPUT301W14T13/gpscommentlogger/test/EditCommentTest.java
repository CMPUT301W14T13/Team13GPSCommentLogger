package com.CMPUT301W14T13.gpscommentlogger.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;

public class EditCommentTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public EditCommentTest() {
		super(DebugActivity);
	}

	
	
	public void testEditCommentText(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		Comment comment = activity.getCurrentComment();
		String newText = "Test Edit";
		
		comment.setCommentText(newText);
		
		assertEquals("Comment text should be edited", newText, comment.getCommentText());
				
		}

	public void testEditPicture(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		Comment comment = activity.getCurrentComment();
		
		
		Bitmap picture = Bitmap.createBitmap() //put some arguments in here to create a bitmap
		comment.attachPicture(picture);
		
		assertEquals("Picture hash codes should be equal", picture.hashCode(),
						comment.getPicture().hashCode());
		}
	
	
	

}
