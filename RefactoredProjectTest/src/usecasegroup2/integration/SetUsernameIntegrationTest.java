package usecasegroup2.integration;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.controllers.SelectUsernameController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.views.HomeView;

public class SetUsernameIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public SetUsernameIntegrationTest() {
		super(HomeView.class);
	}

	CommentTree ct;
	SelectUsernameController selectUsername;
	
	public void testSetCurrentUsernameActivity() throws Throwable{

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(SelectUsernameController.class.getName(), null , false);

		Intent intent = new Intent();
		setActivityIntent(intent);
		HomeView homeView = getActivity();
		assertNotNull(homeView);

		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_select_username, 0);

		Thread.sleep(2000);	

		getInstrumentation().waitForIdleSync();
		selectUsername = (SelectUsernameController) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 10000);
		assertNotNull(selectUsername);

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText addUsername = (EditText) selectUsername.findViewById(cmput301w14t13.project.R.id.addUsername);	
				Button addButton = (Button) selectUsername.findViewById(cmput301w14t13.project.R.id.addButton);
				ListView usernameList = (ListView) selectUsername.findViewById(cmput301w14t13.project.R.id.usernamesList);

				assertNotNull(addUsername);
				assertNotNull(addButton);
				assertNotNull(usernameList);
				

				addUsername.setText("Austin");
				addButton.performClick();
				addUsername.setText("Austin2");
				addButton.performClick();

				
				
				usernameList.performItemClick(usernameList.getChildAt(0), 0,
						usernameList.getAdapter().getItemId(0));

				assertEquals("Current username wasn't set", "Austin", ct.getCurrentUsername());


				usernameList.performItemClick(usernameList.getChildAt(1), 1,
						usernameList.getAdapter().getItemId(1));

				assertEquals("Current username wasn't set", "Austin2", ct.getCurrentUsername());

				/*Button deleteButton = (Button) selectUsername.findViewById(cmput301w14t13.project.R.id.deleteButton);
				deleteButton.performClick();
				deleteButton.performClick();*/
				
			}
		});
	}
	
}
