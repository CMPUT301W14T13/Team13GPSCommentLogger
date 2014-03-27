package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.util.ArrayList;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

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
	
	
	public SetAsFavouriteTest() {
		super(TopicViewActivity.class);
	}

	@Before
	public void setup(){
		intent = new Intent();
		setActivityIntent(intent);
		
		//prefs.saveInTopicFile(topicFile, new Topic());
		prefs.saveInCommentFile("comments.sav", new ArrayList<Comment>());
		
		cl.getTopicChildren().clear();
		
		
		
	}
	
	public void testSetTopicFavourite() throws Throwable{
		
		topic = new Topic("Testing");
		cl.addTopic(topic);
		cl.setCurrentTopic(0);
		activity = getActivity();
		assertNotNull(activity);

		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				Button saveTopic = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.topicSaveButton);
				assertNotNull(saveTopic);
				
				ArrayList<Topic> favouriteTopics = prefs.loadTopicFile(topicFile);
				
				for (int i = 0; i < favouriteTopics.size(); i++){
					favouriteTopics.add(favouriteTopics.get(i));
				}				


				assertTrue("Topics should be the same", favouriteTopics.get(favouriteTopics.size() - 1).equals(topic));
			}
		});
	}

}
