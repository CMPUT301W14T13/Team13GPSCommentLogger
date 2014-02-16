package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import java.util.Date;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentRoot;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

@SuppressLint("NewApi")
public class PushCommentTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public PushCommentTest() {
		super(DebugActivity.class);
	}
	
	public void testPushComment() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		
		assertNotNull(activity);
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("default comment is a root", true, activity.getCurrentComment() instanceof CommentRoot);
		assertEquals("default comment has length 3", 3, activity.getCurrentComment().getC().size());
		
		activity.simulateBrowseClick(0);
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("first layer is a thread", true, activity.getCurrentComment() instanceof CommentThread);
		assertEquals("first layer comment has length 3", 3, activity.getCurrentComment().getC().size());
		
		activity.simulateBrowseClick(0);
		
		assertNotNull(activity.getCurrentComment());
		Log.w("DebugMessage", activity.getCurrentComment().toString());
		//assertEquals("second layer is a comment", true, activity.getCurrentComment() instanceof Comment);
		assertEquals("comment originally should have 0 replies", 0, activity.getCurrentComment().getC().size());
		
		String ID = "4324";
		String username = "Austin";
		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //must add arguments
		Date timestamp = new Date();
		String commentText = "Test comment";
		Comment comment = new Comment(ID, username, picture, timestamp, commentText);
		
		activity.simulateAddComment(comment);
		
		Thread.sleep(2000);
		
		assertEquals("comment should now have 1 reply", 1, activity.getCurrentComment().getC().size());
		
		activity.simulateBrowseClick(0);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("comment fields should be the same", ID, activity.getCurrentComment().getID());
		assertEquals("comment fields should be the same", username, activity.getCurrentComment().getUsername());
		assertEquals("comment fields should be the same", picture.hashCode(), activity.getCurrentComment().getImage().hashCode());
		assertEquals("comment fields should be the same", timestamp, activity.getCurrentComment().getTimestamp());
		assertEquals("comment fields should be the same", commentText, activity.getCurrentComment().getCommentText());
	}

}
