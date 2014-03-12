package gpscommentloggertests.pushToServer;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;


@SuppressLint("NewApi")
public class PushToServer extends ActivityInstrumentationTestCase2<DebugActivity>
{
	public PushToServer() {
		super(DebugActivity.class);	
	}
	
	public void testInitialize(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
	}
}
