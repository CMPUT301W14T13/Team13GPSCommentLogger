package usecasegroup2.integration;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
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
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;
import cmput301w14t13.project.views.submissions.SubmissionView;



@SuppressLint("NewApi")
public class MakeSubmissionIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;
	SubmissionView create;
	CommentTree ct = CommentTree.getInstance();
	protected HomeViewController controller;
	
	public MakeSubmissionIntegrationTest() {
		super(HomeView.class);
	}


	/**
	 * Certain fields in a comment cannot be null. The only field
	 * allowed to be null is the comment's text, but only if
	 * it has an attached picture
	 */


	public void testNullFields(){

		/*
		 * various fields for making a comment that can be
		 * used for testing the asserts below
		 */

		Comment comment = new Comment();


		assertNotNull(comment.getID());
		assertNotNull(comment.getUsername());
		assertNotNull(comment.getTimestamp());

		if (comment.getCommentText() == null){
			assertEquals("If comment text is empty, then it must have a picture", true,
					comment.getHasImage());
		}	
	}

	

	/**
	 * test to check that comment fields are correctly set
	 * by comparing the values used to create the comment with
	 * what is actually in the comment
	 */
	public void testCommentFields(){

		/*
		 * various fields for making a comment that can be
		 * used for testing the asserts below
		 */

		String ID = "4324";
		String username = "Austin";
		Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //must add arguments
		Date timestamp = new Date();
		String commentText = "Test comment";
		Location location = new Location("default");

		Comment comment = new Comment(ID, username, picture, timestamp, commentText);
		comment.setGPSLocation(location);

		assertEquals("Comment IDs should be the same", ID, comment.getID());
		assertEquals("Usernames should be the same", username, comment.getUsername());
		assertEquals("Picture should be the attached picture", picture.hashCode(),
				comment.getImage().hashCode());
		assertEquals("Timestamps should be the same", timestamp, comment.getTimestamp());
		assertEquals("Comment text should be the same", commentText, comment.getCommentText());
		assertEquals("GPS coordinates should be the same", location, comment.getGPSLocation());

	}


	/**
	 * test to check that comment fields are correctly set
	 * by comparing the values used to create the comment with
	 * what is actually in the comment
	 */
	public void testMakeTopic() throws Throwable {

		
		homeView = getActivity();
		assertNotNull(homeView);
		controller = new HomeViewController(homeView);
		controller.init();
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


				ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
				CommentTreeElement topic = topics.get(0);

				assertEquals("Titles not the same", topic.getTitle(), title.getText().toString() );
				assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());


			}
		});
	}

	//Test that the current username is displayed when creating a topic
	public void testTopicCurrentUsername() throws Throwable{

		ct.setCurrentUsername("Austin");
		homeView = getActivity();
		assertNotNull(homeView);

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateTopicSubmissionView.class.getName(), null , false);


		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		getInstrumentation().waitForIdleSync();
		create = (CreateTopicSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				EditText username = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicUsername);	

				assertNotNull(username);

				assertEquals("Text field should display current username", "Austin", username.getText().toString());
			}
		});

	}

	public void testMakeComment() throws Throwable{



		homeView = getActivity();
		assertNotNull(homeView);
		controller = new HomeViewController(homeView);
		controller.init();
		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateTopicSubmissionView.class.getName(), null , false);
		ct.setCurrentUsername("Set username test");

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


				ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
				CommentTreeElement topic = topics.get(0);

				assertEquals("Titles not the same", topic.getTitle(), title.getText().toString() );
				assertEquals("Usernames not the same", topic.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", topic.getCommentText(), commentText.getText().toString());


			}
		});
		
		
		submissionMonitor = getInstrumentation().addMonitor(TopicView.class.getName(), null , false);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
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

				int size = ct.getCommentList(topicView).size();
				CommentTreeElement comment = ct.getCommentList(topicView).get(size-1);

				assertEquals("Usernames not the same", comment.getUsername(), username.getText().toString());
				assertEquals("Texts not the same", comment.getCommentText(), commentText.getText().toString());
			}

		});


		topicView.finish();
	}



	
}




