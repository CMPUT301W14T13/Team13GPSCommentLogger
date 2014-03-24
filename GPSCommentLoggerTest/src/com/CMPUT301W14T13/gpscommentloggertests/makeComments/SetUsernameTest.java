package com.CMPUT301W14T13.gpscommentloggertests.makeComments;

import java.util.ArrayList;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.SelectUsernameActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;

/**
 * JUnit test to ensure that the user name gets properly set.
 * 
 * @author mjnichol, arweber
 *
 */

@SuppressLint("NewApi")
public class SetUsernameTest extends ActivityInstrumentationTestCase2<SelectUsernameActivity> {

	SelectUsernameActivity activity;
	Intent intent;
	CommentLogger cl = CommentLogger.getInstance();
	Preferences prefs;
	ArrayList<String> usernames;

	public SetUsernameTest() {
		super(SelectUsernameActivity.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		intent = new Intent();
		setActivityIntent(intent);
		
		activity = getActivity();
		prefs = new Preferences(activity.getApplicationContext());
		prefs.saveArray(new ArrayList<String>()); //clear saved usernames in preferences

	}

	/**
	 * 
	 *  Test to check if the default username is
	 *  used for the comment if the user does not
	 *  provide one.
	 */

	public void testDefaultUsername(){

		Intent intent = new Intent();
		setActivityIntent(intent);

		assertNotNull(activity);


		//create new comment with default username
		Comment comment = new Comment();

		assertEquals("Username should be default username", "Anonymous", comment.getUsername());

	}

	/*
	 * Add a username to the username list, select it
	 * and then check if the current username was set in the model
	 */
	public void testSetCurrentUsername() throws Throwable{


		assertNotNull(activity);

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText addUsername = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.addUsername);	
				Button addButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.addButton);
				ListView usernameList = (ListView) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.usernamesList);

				assertNotNull(addUsername);
				assertNotNull(addButton);
				assertNotNull(usernameList);
				assertTrue("Listview should be empty", usernameList.getAdapter().getCount() == 0);
				
				addUsername.setText("Austin");
				addButton.performClick();
				addUsername.setText("Austin2");
				addButton.performClick();

				usernameList.performItemClick(usernameList.getChildAt(0), 0,
						usernameList.getAdapter().getItemId(0));
				
				assertEquals("Current username wasn't set", "Austin", cl.getCurrentUsername());
				
				
				usernameList.performItemClick(usernameList.getChildAt(1), 1,
						usernameList.getAdapter().getItemId(1));
				
				assertEquals("Current username wasn't set", "Austin2", cl.getCurrentUsername());

			}
		});


	}
	
	
}