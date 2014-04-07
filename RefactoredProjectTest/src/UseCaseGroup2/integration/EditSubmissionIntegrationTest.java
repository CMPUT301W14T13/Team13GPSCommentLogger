package usecasegroup2.integration;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;
import cmput301w14t13.project.views.submissions.EditCommentSubmissionView;
import cmput301w14t13.project.views.submissions.EditTopicSubmissionView;
import cmput301w14t13.project.views.submissions.SubmissionView;

@SuppressLint("NewApi")
public class EditSubmissionIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;
	SubmissionView edit;
	CommentTree ct = CommentTree.getInstance();
	
	public EditSubmissionIntegrationTest() {
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

		submissionMonitor = getInstrumentation().addMonitor(EditTopicSubmissionView.class.getName(), null , false);



		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				Button editButton = (Button) topicView.findViewById(cmput301w14t13.project.R.id.topic_edit_button);
				assertNotNull(editButton);


				editButton.performClick();

			}

		});


		getInstrumentation().waitForIdleSync();
		edit = (EditTopicSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(edit);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				EditText username = (EditText) edit.findViewById(cmput301w14t13.project.R.id.set_comment_username);
				EditText commentText = (EditText) edit.findViewById(cmput301w14t13.project.R.id.set_comment_text);
				Button submitButton = (Button) edit.findViewById(cmput301w14t13.project.R.id.submit);

				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("Edit1");
				commentText.setText("Edited text");

				submitButton.performClick();
				
				CommentTreeElement topic = ct.getElement(topicView);

				assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());
			}

		});


		topicView.finish();
	}
	
	public void testEditComment() throws Throwable{
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

		submissionMonitor = getInstrumentation().addMonitor(EditCommentSubmissionView.class.getName(), null , false);



		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				ListView commentList = (ListView) topicView.findViewById(cmput301w14t13.project.R.id.comment_list);
				View v = commentList.getAdapter().getView(0, null, null);
				Button editButton = (Button) v.findViewById(cmput301w14t13.project.R.id.comment_edit_button);
				assertNotNull(editButton);
				

				editButton.performClick();

			}

		});


		getInstrumentation().waitForIdleSync();
		edit = (EditCommentSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(edit);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				EditText username = (EditText) edit.findViewById(cmput301w14t13.project.R.id.set_comment_username);
				EditText commentText = (EditText) edit.findViewById(cmput301w14t13.project.R.id.set_comment_text);
				Button submitButton = (Button) edit.findViewById(cmput301w14t13.project.R.id.submit);

				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				username.setText("comment edit");
				commentText.setText("comment edited text");

				submitButton.performClick();

				
				CommentTreeElement comment = ct.getCommentList(topicView).get(0);
				
				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
			}

		});


		topicView.finish();
	}
}


