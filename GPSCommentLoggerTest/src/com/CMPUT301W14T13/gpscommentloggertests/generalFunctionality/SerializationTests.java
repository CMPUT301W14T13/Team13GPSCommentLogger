package com.CMPUT301W14T13.gpscommentloggertests.generalFunctionality;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
import com.google.gson.Gson;

@SuppressLint("NewApi")
public class SerializationTests extends ActivityInstrumentationTestCase2<DebugActivity> {

	public SerializationTests() {
		super(DebugActivity.class);
	}
	
	public void test()
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		
		//String path = activity.getFilesDir().getPath().toString() + "/test4.sav";
		
		ArrayList<Viewable> testlist = new ArrayList<Viewable>();
		testlist.add(new Comment("test_ID"));
		testlist.add(new Topic("test_ID2"));
		testlist.add(new Root("test_ID3"));
		
		Log.w("Test", new Gson().toJson(testlist));
		
		HashMap<String, Viewable> testmap = new HashMap<String, Viewable>();
		testmap.put("test_ID",new Comment("test_ID"));
		testmap.put("test_ID2", new Topic("test_ID2"));
		testmap.put("test_ID3", new Root("test_ID3"));
		
		Log.w("Test", new Gson().toJson(testmap));
	}

}
