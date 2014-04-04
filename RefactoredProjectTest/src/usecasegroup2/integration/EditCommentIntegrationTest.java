package usecasegroup2.integration;

import java.util.ArrayList;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.controllers.CreateSubmissionController;
import cmput301w14t13.project.controllers.HomeViewController;
import cmput301w14t13.project.controllers.TopicViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;

@SuppressLint("NewApi")
public class EditCommentIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;
	
	CreateSubmissionController create;
	HomeViewController homeControl;
	
	TopicViewController edit;
	Intent intent;
	CommentTree ct = CommentTree.getInstance();

	public EditCommentIntegrationTest() {
		super(HomeView.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		intent = new Intent();
		setActivityIntent(intent);

	}
	// write the integration test for editing a comment! 
	public void testEditTopic() throws Throwable{
		homeView = getActivity();
		assertNotNull(homeView);

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateSubmissionController.class.getName(), null , false);
		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		getInstrumentation().waitForIdleSync();
		create = (CreateSubmissionController) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);

		/* make the comment */
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				// Create the Topic 
				EditText title = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTitle);
				EditText username = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicUsername);
				EditText commentText = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicText);	
				Button submitButton = (Button) create.findViewById(cmput301w14t13.project.R.id.submit);

				assertNotNull(title);
				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				title.setText("title");
				username.setText("User1");
				commentText.setText("text");
				
				submitButton.performClick();

				// give the topic a second to get pushed to the server 
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				/* make sure we have entered the CreateSubmissionController Activity */
				
				ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
				CommentTreeElement topic = topics.get(0);

				assertEquals("Titles not the same", topic.getTitle(), title.getText().toString() );
				assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());
		
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				/* I don't think I am correctly in the activity I think I am */
				ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
				assertNotNull(topicList);
				
				/* click the newly created topic */
				topicList.performItemClick(
					    topicList.getAdapter().getView(0, null, null), 0, 0);
				

				//topicList.performItemClick(topicList.getChildAt(0), 0, topicList.getAdapter().getItemId(0));
				
				
			}
		});
		
		/* Now click the comment and edit it */
		// Instrumentation.ActivityMonitor homeViewMonitor = getInstrumentation().addMonitor(HomeViewController.class.getName(), null , false);
		// give the topic a second to get pushed to the server 

		

		
		

	}
}
