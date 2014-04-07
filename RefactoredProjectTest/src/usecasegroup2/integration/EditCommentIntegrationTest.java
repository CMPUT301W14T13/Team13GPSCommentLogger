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
import cmput301w14t13.project.views.TopicView;
import cmput301w14t13.project.views.submissions.EditTopicSubmissionView;
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;
import cmput301w14t13.project.views.submissions.SubmissionView;

@SuppressLint("NewApi")
public class EditCommentIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;
	SubmissionView create;
	CommentTree ct = CommentTree.getInstance();
	
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
		homeView = getActivity();

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(TopicView.class.getName(), null , false);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
				topicList.performItemClick(topicList.getChildAt(0), 0,
						topicList.getAdapter().getItemId(0));
			}

		});


		getInstrumentation().waitForIdleSync();
		topicView = (TopicView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(topicView);

		submissionMonitor = getInstrumentation().addMonitor(ReplyToTopicCommentSubmissionView.class.getName(), null , false);



		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				Button editButton = (Button) topicView.findViewById(cmput301w14t13.project.R.id.topic_edit_button);
				assertNotNull(editButton);


				editButton.performClick();

			}

		});


		getInstrumentation().waitForIdleSync();
		create = (ReplyToTopicCommentSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				EditText username = (EditText) create.findViewById(cmput301w14t13.project.R.id.set_comment_username);
				EditText commentText = (EditText) create.findViewById(cmput301w14t13.project.R.id.set_comment_text);
				Button submitButton = (Button) create.findViewById(cmput301w14t13.project.R.id.submit);

				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("User1");
				commentText.setText("text");

				submitButton.performClick();

				int size = ct.getCommentList(topicView).size();
				CommentTreeElement comment = ct.getCommentList(topicView).get(size-1);

				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
			}

		});


		topicView.finish();
	}
}


