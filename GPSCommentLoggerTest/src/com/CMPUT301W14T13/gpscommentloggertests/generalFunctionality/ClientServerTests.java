package com.CMPUT301W14T13.gpscommentloggertests.generalFunctionality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;


@SuppressLint("NewApi")
public class ClientServerTests extends ActivityInstrumentationTestCase2<DebugActivity>
{

	public ClientServerTests()
	{
		super(DebugActivity.class);
	}
	
	public void testThreadCreation() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		
		assertNotNull(activity);
		
		TextView view = (TextView)activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.debug_window);
		
		assertNotNull(view);

		Thread.sleep(2000);
		
		assertEquals("Server should register message on window","Server Running", view.getText().toString());

	}

}
