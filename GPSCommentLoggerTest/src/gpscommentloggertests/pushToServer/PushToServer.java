package gpscommentloggertests.pushToServer;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivityWithServer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;


@SuppressLint("NewApi")
public class PushToServer extends ActivityInstrumentationTestCase2<DebugActivityWithServer>
{
	public PushToServer() {
		super(DebugActivityWithServer.class);	
	}
	
	public void testInitialize() throws InterruptedException{
		
		Intent intent = new Intent();
		intent.putExtra("filePath", "test01");
		setActivityIntent(intent);
		DebugActivityWithServer activity = getActivity();
		assertNotNull(activity);
		
		Thread.sleep(2000);
		
		//TODO: check HttpResponse for success
	}
}
