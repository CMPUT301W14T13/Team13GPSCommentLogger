package usecasegroup2.integration;

import java.util.ArrayList;

import org.junit.BeforeClass;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.controllers.HomeViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;
import cmput301w14t13.project.views.submissions.CreateTopicSubmissionView;
import cmput301w14t13.project.views.submissions.EditCommentSubmissionView;
import cmput301w14t13.project.views.submissions.EditTopicSubmissionView;
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;
import cmput301w14t13.project.views.submissions.SubmissionView;

@SuppressLint("NewApi")
public class EditSubmissionIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;
	SubmissionView create, edit;
	CommentTree ct = CommentTree.getInstance();
	HomeViewController controller;
	static boolean setup = false;
	
	public EditSubmissionIntegrationTest() {
		super(HomeView.class);
	}

	
	public void init() throws Throwable{
		
	/*	if (setup){
			return;
		}
		setup = true;*/
		homeView = getActivity();
		assertNotNull(homeView);
		controller = new HomeViewController(homeView);
		controller.init();
		getActivity();
		Thread.sleep(5000);
		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateTopicSubmissionView.class.getName(), null , false);

		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		getInstrumentation().waitForIdleSync();
		create = (CreateTopicSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);


		
			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {

					EditText title = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTitle);
					EditText username = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicUsername);
					EditText commentText = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicText);	
					Button submitButton = (Button) create.findViewById(cmput301w14t13.project.R.id.submit);

					assertNotNull(title);
					assertNotNull(username);
					assertNotNull(commentText);
					assertNotNull(submitButton);

					title.setText("Junit Test");
					username.setText("User1");
					commentText.setText("text");
					
					submitButton.performClick();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ArrayList<CommentTreeElement> topics = ct.getChildren(homeView);
					CommentTreeElement topic = topics.get(0);

					assertEquals("Titles not the same", topic.getTitle(), title.getText().toString() );
					assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
					assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());
					
				}
			});
		
		
		
		getInstrumentation().waitForIdleSync();
		submissionMonitor = getInstrumentation().addMonitor(TopicView.class.getName(), null , false);

		runTestOnUiThread(new Runnable()  {
			
			@Override
			public void run(){
				
				ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
				assertNotNull(topicList);
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
					
					assertEquals("Username should be current username", ct.getCurrentUsername(), username.getText().toString());
					
					username.setText("User1");
					commentText.setText("Junit test comment");

					submitButton.performClick();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int size = ct.getCommentList(topicView).size();
					CommentTreeElement comment = ct.getCommentList(topicView).get(size-1);

					assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
					assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
				}

			});
		


		topicView.finish();
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
		init();
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
		init();
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

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CommentTreeElement comment = ct.getCommentList(topicView).get(0);
				
				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
			}

		});


		topicView.finish();
	}
}


