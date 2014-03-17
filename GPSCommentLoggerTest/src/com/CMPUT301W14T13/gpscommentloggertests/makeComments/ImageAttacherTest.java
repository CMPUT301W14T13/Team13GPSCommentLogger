package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.ImageAttacher;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * @author imamverd
 *
 * This class will test the image attachment 
 * through phone gallery functionality. The test will 
 * provide a mock gallery image result.
 * 
 * This class will have tests for the following:
 * 1) Successfully returning an image from the gallery intent.
 * 2) Successfully check image size limitations (100 KB)
 *
 */

@SuppressLint("NewApi")
public class ImageAttacherTest extends ActivityInstrumentationTestCase2<ImageAttacher>
{

	public ImageAttacherTest(Class<ImageAttacher> activityClass)
	{
		super(activityClass);
	}

	ImageAttacher activity = getActivity(); // activity object to test with


	/**
	 * Test #1: Image fetching from 
	 * external intent (eg. phone gallery)
	 */
	public void attachFromIntent() {
		try
		{
			runTestOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity.attachmentIntentStart();
				}
			});
		} catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
