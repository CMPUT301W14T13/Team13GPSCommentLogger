package usecasegroup2.unit;


import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.controllers.SelectUsernameController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;

public class SetUsernameTest extends ActivityInstrumentationTestCase2<HomeView> {

	CommentTree ct;
	SelectUsernameController selectUsername;

	public SetUsernameTest() {
		super(HomeView.class);
	}


	public void setUp(){
		ct = CommentTree.getInstance();
	}

	public void testDefaultUsername(){

		//create new comment with default username
		CommentTreeElement comment = new Comment();
		assertEquals("Username should be default username", "Anonymous", comment.getUsername());

	}


	public void testSetUsername(){

		CommentTreeElement comment = new Comment();
		comment.setUsername("Austin");
		assertEquals("Username should be what was set", "Austin", comment.getUsername());
	}

	public void testSetCurrentUsername(){

		ct.setCurrentUsername("Austin");
		assertEquals("Username should be what was set", "Austin", ct.getCurrentUsername());
	}

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
				assertTrue("Listview should be empty", usernameList.getAdapter().getCount() == 0);

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

			}
		});
	}
}


