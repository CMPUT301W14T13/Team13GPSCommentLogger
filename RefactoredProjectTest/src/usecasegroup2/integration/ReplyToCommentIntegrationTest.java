package usecasegroup2.integration;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;
import cmput301w14t13.project.views.submissions.ReplyToCommentCommentSubmissionView;
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;
import cmput301w14t13.project.views.submissions.SubmissionView;

@SuppressLint("NewApi")
public class ReplyToCommentIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {


	HomeView homeView;
	TopicView topicView;
	SubmissionView create;
	View v;
	CommentTree ct = CommentTree.getInstance();

	public ReplyToCommentIntegrationTest() {
		super(HomeView.class);
	}


	public void testReplyToComment() throws Throwable{

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

				Button replyButton = (Button) topicView.findViewById(cmput301w14t13.project.R.id.topic_reply_button);
				assertNotNull(replyButton);


				replyButton.performClick();

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

			}

		});




		submissionMonitor = getInstrumentation().addMonitor(ReplyToCommentCommentSubmissionView.class.getName(), null , false);



		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				int size = ct.getCommentList(topicView).size();

				ListView commentList = (ListView) topicView.findViewById(cmput301w14t13.project.R.id.comment_list);
				v = commentList.getAdapter().getView(size-1, null, null);

				Button reply = (Button) v.findViewById(cmput301w14t13.project.R.id.comment_reply_button);

				assertNotNull(reply);

				reply.performClick();

			}

		});


		getInstrumentation().waitForIdleSync();
		create = (ReplyToCommentCommentSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
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

				username.setText("User12");
				commentText.setText("text for reply");

				submitButton.performClick();

				int size = ct.getCommentList(topicView).size();

				ListView commentList = (ListView) topicView.findViewById(cmput301w14t13.project.R.id.comment_list);
				v = commentList.getAdapter().getView(size-1, null, null);
				View indent = v.findViewById(cmput301w14t13.project.R.id.left_indent1);

				assertTrue("Indent view should be visible", indent.getVisibility() == View.VISIBLE);

				CommentTreeElement comment = ct.getCommentList(topicView).get(size - 1);
				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
				assertEquals("Comment should be indented 1 level", 1, comment.getIndentLevel());
			}

		});

		topicView.finish();
	}

}
