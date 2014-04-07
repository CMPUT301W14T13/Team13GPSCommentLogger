package usecasegroup2.integration;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.submissions.EditTopicSubmissionView;

@SuppressLint("NewApi")
public class EditCommentIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public EditCommentIntegrationTest() {
		super(HomeView.class);
	}

	@UiThreadTest
	public void testHomeViewUpdate() {
		Intent intent = new Intent();
		setActivityIntent(intent);
		HomeView homeView = getActivity();
		homeView.update();
		ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
		assertNotNull(topicList);
		topicList.performItemClick(
				topicList.getAdapter().getView(0, null, null), 0, 0);

		homeView.finish();
	}
	
	// write the integration test for editing a comment!
	@UiThreadTest
	public void testEditTopic() throws Throwable{
		Intent intent = new Intent();
		setActivityIntent(intent);
		HomeView homeView = getActivity();
		assertNotNull(homeView);

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(EditTopicSubmissionView.class.getName(), null , false);
		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		final EditTopicSubmissionView create = (EditTopicSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);

		/* make the comment */
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

		ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(CommentTree.getInstance().getChildren(homeView));
		CommentTreeElement topic = topics.get(0);

		assertEquals("Titles not the same", topic.getTitle(), title.getText().toString() );
		assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
		assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());


		/* Now click the comment and edit it */
		// Instrumentation.ActivityMonitor homeViewMonitor = getInstrumentation().addMonitor(HomeViewController.class.getName(), null , false);
		// give the topic a second to get pushed to the server 

		/* stop the runnable here */

		/* I don't think I am correctly in the activity I think I am */
		homeView = getActivity();
		homeView.update();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
		assertNotNull(topicList);

		/* click the newly created topic */
		topicList.performItemClick(
				topicList.getAdapter().getView(0, null, null), 0, 0);

		/* you are now in the new Activity, so change the parameters */
/*
		title.setText("title_new");
		username.setText("User2");
		commentText.setText("text_new");

		submitButton.performClick();
		topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
		topics.get(0);

		assertEquals("Titles not the same", topic.getTitle(), title.getText().toString() );
		assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
		assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());
*/

		homeView.finish();
	}
}


