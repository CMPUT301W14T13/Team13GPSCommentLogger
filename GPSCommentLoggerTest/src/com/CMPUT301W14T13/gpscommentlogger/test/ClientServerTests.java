package com.CMPUT301W14T13.gpscommentlogger.test;

import com.CMPUT301W14T13.gpscommentlogger.ClientServerActivity;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;


public class ClientServerTests extends ActivityInstrumentationTestCase2<ClientServerActivity>
{

	public ClientServerTests()
	{

		super(ClientServerActivity.class);
	}
	
	public void testThreadCreation()
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		ClientServerActivity activity = getActivity();
		
		assertNotNull(activity);
	}

}
