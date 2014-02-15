package com.CMPUT301W14T13.gpscommentlogger.test;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;


public class MakeCommentTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	
	public MakeCommentTest() {
		super(DebugActivity.class);
	}
	
	
	
	public void testMakeComment(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);
		
		
		
	}
}
