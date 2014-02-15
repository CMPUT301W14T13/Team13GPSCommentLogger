package com.CMPUT301W14T13.gpscommentloggertests;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentRoot;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

@SuppressLint("NewApi")
public class BrowsingTestCases extends ActivityInstrumentationTestCase2<DebugActivity> {

	public BrowsingTestCases() {
		super(DebugActivity.class);
	}
	
	public void testBrowse1() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		
		assertNotNull(activity);
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("default comment is a root", true, activity.getCurrentComment() instanceof CommentRoot);
		assertEquals("default comment has length 3", 3, activity.getCurrentComment().getC().size());
		
		Log.w("DebugBrowsing", "Current is: " + activity.getCurrentComment().getID());
		
		activity.simulateBrowseClick(0);
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("first layer is a thread", true, activity.getCurrentComment() instanceof CommentThread);
		assertEquals("first layer comment has length 3", 3, activity.getCurrentComment().getC().size());
		
		Log.w("DebugBrowsing", "Current is: " + activity.getCurrentComment().getID());
	}

}
