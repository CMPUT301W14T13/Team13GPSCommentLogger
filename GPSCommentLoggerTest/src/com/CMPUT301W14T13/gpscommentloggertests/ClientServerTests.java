package com.CMPUT301W14T13.gpscommentloggertests;

import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;


@SuppressLint("NewApi")
public class ClientServerTests extends ActivityInstrumentationTestCase2<HomeView>
{

	public ClientServerTests()
	{
		super(HomeView.class);
	}
	
	public void testThreadCreation()
	{
		Intent intent = new Intent();
		intent.putExtra(HomeView.DEBUG, true);
		setActivityIntent(intent);
		HomeView activity = getActivity();
		
		assertNotNull(activity);
		
		TextView view = (TextView)activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.debug_window);
		
		ClientServerSystem.getInstance().init(view);
		
		assertEquals("Server should register message on window","Server Running", view.getText().toString());
		
	}

}
