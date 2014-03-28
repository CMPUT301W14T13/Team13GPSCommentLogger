package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.view.TopicViewActivity;

@SuppressLint("NewApi")
public class SetAsFavouriteTest extends ActivityInstrumentationTestCase2<TopicViewActivity> {

	TopicViewActivity activity;
	Intent intent;
	Preferences prefs;
	CommentLogger cl = CommentLogger.getInstance();
	Topic topic;
	Comment comment;
	
	private String topicFile = "topics.sav";
	private String commentFile = "comments.sav";
	
	
	public SetAsFavouriteTest() {
		super(TopicViewActivity.class);
	}

	@Before
	public void setup(){
		
		intent = new Intent();
		setActivityIntent(intent);
		cl.setCurrentTopic(0);
		cl.getTopics().clear();
		
	}

	public void testSetTopicFavourite() throws Throwable{
		
		topic = new Topic("Testing");
		cl.addTopic(topic);
		activity = getActivity();
		assertNotNull(activity);

		prefs = new Preferences(activity.getApplicationContext());
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				Button saveTopic = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.topicSaveButton);
				assertNotNull(saveTopic);
				
				saveTopic.performClick();
				
				ArrayList<Topic> favouriteTopics = prefs.loadTopicFile(topicFile);
				
				Topic savedTopic = favouriteTopics.get(favouriteTopics.size() - 1);
				
				assertTrue("Topics should be the same", savedTopic.equals(topic));
			}
		});
	}

	
public void testSetCommentFavourite() throws Throwable{
		
		topic = new Topic("Testing");
		cl.addTopic(topic);
		comment = new Comment("Testing");
		cl.addComment(comment);
		
		activity = getActivity();
		assertNotNull(activity);

		prefs = new Preferences(activity.getApplicationContext());
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				Button saveComment = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.saveComment);
				ListView commentList = (ListView) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.comment_list);
				
				assertNotNull(saveComment);
				assertNotNull(commentList);
				
				saveComment.performClick();
			
				ArrayList<Comment> favouriteComments = prefs.loadCommentFile(commentFile);
				
				Comment savedComment = favouriteComments.get(favouriteComments.size() - 1);
				assertTrue("Comments should be the same", savedComment.equals(comment));
				
				
			}
		});
	}

}
