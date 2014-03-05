package com.CMPUT301W14T13.gpscommentloggertests;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

@SuppressLint("NewApi")
public class BrowsingTestCases2 extends
		ActivityInstrumentationTestCase2<DebugActivity> {

	public BrowsingTestCases2() {
		super(DebugActivity.class);
	}
	
	public void testReconnect() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		
		assertNotNull(activity);
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("default comment is a root", true, activity.getCurrentComment() instanceof Root);
		assertEquals("default comment has length 3", 3, activity.getCurrentComment().getC().size());
		
		activity.simulateOnlineBrowseClick(0);
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("first layer is a thread", true, activity.getCurrentComment() instanceof Topic);
		assertEquals("first layer comment has length 3", 3, activity.getCurrentComment().getC().size());
		
		activity.simulateOnlineBrowseClick(0);
		activity.forceChangeOnline("initial online title");
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("second layer is a comment", true, activity.getCurrentComment() instanceof Comment);
		assertEquals("this comment does not have the appropriate title", "initial online title", activity.getCurrentComment().getTitle());
		
		Log.w("DebugBrowsing", "Current is: " + activity.getCurrentComment().getID());
		
		activity.forceChangeOffline("initial offline title");
		activity.forceChangeOnline("new online title");
		activity.simulateDisconnectFromServer();
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("second layer is a comment", true, activity.getCurrentComment() instanceof Comment);
		assertEquals("this comment does not have the appropriate title", "initial offline title", activity.getCurrentComment().getTitle());
		
		activity.simulateConnectToServer();
		
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("second layer is a comment", true, activity.getCurrentComment() instanceof Comment);
		assertEquals("this comment does not have the appropriate title", "new online title", activity.getCurrentComment().getTitle());
		
	}


}
