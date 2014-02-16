package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

@SuppressLint("NewApi")
public class EditCommentTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public EditCommentTest() {
		super(DebugActivity.class);
	}

	
	
	public void testEditCommentText(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		Viewable comment = activity.getCurrentComment();
		String newText = "Test Edit";
		
		comment.setCommentText(newText);
		
		assertEquals("Comment text should be edited", newText, comment.getCommentText());
				
		}

	public void testEditPicture(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		Viewable comment = activity.getCurrentComment();
		
		
		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //put some arguments in here to create a bitmap
		comment.setImage(picture);
		
		assertEquals("Picture hash codes should be equal", picture.hashCode(),
						comment.getImage().hashCode());
		}
	
	
	

}