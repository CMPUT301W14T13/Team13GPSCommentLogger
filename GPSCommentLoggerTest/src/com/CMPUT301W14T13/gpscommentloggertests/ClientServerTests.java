package com.CMPUT301W14T13.gpscommentloggertests;

import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;


@SuppressLint("NewApi")
public class ClientServerTests extends ActivityInstrumentationTestCase2<DebugActivity>
{

	public ClientServerTests()
	{
		super(DebugActivity.class);
	}
	
	public void testThreadCreation()
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		
		assertNotNull(activity);
		
		TextView view = (TextView)activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.debug_window);
		
		assertNotNull(view);
		
		ClientServerSystem.getInstance().init(view);
		
		assertEquals("Server should register message on window","Server Running", view.getText().toString());

		
		ClientServerSystem.getInstance().kill();
	}

}
