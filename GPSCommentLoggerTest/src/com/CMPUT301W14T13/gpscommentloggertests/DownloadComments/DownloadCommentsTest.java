package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

@SuppressLint("NewApi")
public class DownloadCommentsTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public DownloadCommentsTest(String name) {
		super(DebugActivity.class);
		
	}
	
	//Use Case 4
	public void testDownloadComments(){
		String testPath = "test.sav";
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		DataManager dm = new DataManager(testPath);

		assertNotNull(activity);
		String testID = "This is a test ID";
		Comment comment = new Comment(testID);
		dm.saveData(comment);
		
		activity.finish();
		activity= getActivity();
		dm = new DataManager(testPath);
		
		Comment fromFile = (Comment)dm.getData(testID);
		
		assertEquals("The comment we load from file should be the same as the comment we saved",
				comment, fromFile);
		
		activity.finish();
	}
	
	//Use Case 4.1
	public void testSetAsFavorite() {
		String testPath = "test2.sav";
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		DataManager dm = new DataManager(testPath);
		assertNotNull(activity);

		String testID = "This is a test ID";
		Topic topComment = new Topic(testID);
		Comment reply = new Comment();
		topComment.getC().add(reply);
		
		dm.saveFavourite(topComment);
		
		activity.finish();
		activity = getActivity();
		
		dm = new DataManager(testPath);
		
		Topic favoritedTopic = (Topic)dm.getFavourite(testID);
		
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment, favoritedTopic);
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply, favoritedTopic.getC().get(0)); //compare the first child
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
		CommentThread topComment = new CommentThread();
		Comment reply = new Comment();
		ArrayList<Viewable> c = {reply};
		DataManager dm = new DataManager();
		topComment.setC(c);
		
		dm.setFavorite(topComment); //not implemented
		
		ArrayList<CommentThread> favorites = dm.LoadFavorites();
		
		assertTrue("top comment we load is the same as the top comment we saved",
				favorites.contains(topComment));
		assertTrue("the reply we loaded is the same as the reply we saved",
				favorites.contains(reply)); //compare the first child
		
		Comment secondReply = new Comment();
		c.add(secondReply);
		topComment.setC(c);
		
		dm.UpdateFavorites();//normally periodically called while connected to server
		
		assertTrue("top comment is still saved locally",favorites.contains(topComment));
		assertTrue("first reply is still saved locally",favorites.contains(reply)); //compare the first child
		assertTrue("second reply should be saved locally now",favorites.contains(secondreply));// compare second child

		
	}
}
