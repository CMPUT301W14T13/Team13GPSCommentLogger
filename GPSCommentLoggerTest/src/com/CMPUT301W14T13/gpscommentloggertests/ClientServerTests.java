package com.CMPUT301W14T13.gpscommentloggertests;

import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
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
		
		Handler handler = activity.getHandler();
		ClientServerSystem.getInstance().init(handler, view);

		int j = 0;
		for(int i = 1; i < 1000000; i++)
		{
			j++;
		}
		assertEquals("Server should register message on window","Server Running", view.getText().toString());

	}

}
