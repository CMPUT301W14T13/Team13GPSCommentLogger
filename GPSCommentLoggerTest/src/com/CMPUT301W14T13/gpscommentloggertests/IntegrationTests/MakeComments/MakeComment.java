package com.CMPUT301W14T13.gpscommentloggertests.IntegrationTests.MakeComments;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeViewActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.BitmapFactory.Options;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class MakeComment extends
		ActivityInstrumentationTestCase2<HomeViewActivity> {

	public MakeComment() {
		super(HomeViewActivity.class);
	}
	
	public void testMakeComment() throws InterruptedException
	{
		// Create an ActivityMonitor that monitor ChildActivity, do not interrupt, do not return mock result:
	    Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateSubmissionActivity.class.getName(), null , false);
		
	    Intent intent = new Intent();
	    setActivityIntent(intent);
		HomeViewActivity mainActivity = getActivity();
		assertNotNull(mainActivity);
		
		Menu menu = mainActivity.getMenu();
		menu.performIdentifierAction(R.id.action_post_thread,0);
		
		Thread.sleep(2000);		
		
		getInstrumentation().waitForIdleSync();
	    CreateSubmissionActivity submissionActivity = (CreateSubmissionActivity) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5);
	    assertNotNull(submissionActivity);


	}

}
