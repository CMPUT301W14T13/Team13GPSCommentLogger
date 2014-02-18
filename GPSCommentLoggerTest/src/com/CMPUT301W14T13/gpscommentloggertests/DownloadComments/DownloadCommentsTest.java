package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.io.IOException;
import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

@SuppressLint("NewApi")
public class DownloadCommentsTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public DownloadCommentsTest() {
		super(DebugActivity.class);
		
	}
	
	//Use Case 4
	public void testDownloadComments(){

		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
		String testPath = activity.getFilesDir().getPath().toString() + "/test.sav";
		Log.w("DownloadCommentTest", "Filepath = " + testPath);
		
		DataManager dm = new DataManager(testPath);
		String testID = "This is a test ID";
		Comment comment = new Comment(testID);
		dm.saveData(comment);
		
		activity.finish();
		setActivityIntent(intent);
		activity = getActivity();
		dm = new DataManager(testPath);
		try {
			dm.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Comment fromFile = (Comment)dm.getData(testID);
		
		assertEquals("The comment we load from file should be the same as the comment we saved",
				comment, fromFile);
		
		activity.finish();
	}
	
	//Use Case 4.1
	public void testSetAsFavorite() {
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
		String testPath =  activity.getFilesDir().getPath().toString() + "/test2.sav";
		Log.w("DownloadCommentTest", "Filepath = " + testPath);

		DataManager dm = new DataManager(testPath);
		
		String testID = "This is a test ID";
		Topic topComment = new Topic(testID);
		Comment reply = new Comment();
		topComment.getC().add(reply);
		
		dm.saveFavourite(topComment);
		
		activity.finish();
		setActivityIntent(intent);
		activity = getActivity();
		
		dm = new DataManager(testPath);
		try {
			dm.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Topic favoritedTopic = (Topic)dm.getFavourite(testID);
		
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment, favoritedTopic);
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply, favoritedTopic.getC().get(0)); //compare the first child
		
		activity.finish();
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
		String testPath =  activity.getFilesDir().getPath().toString() + "/test3.sav";
		Log.w("DownloadCommentTest", "Filepath = " + testPath);
		
		DataManager dm = new DataManager(testPath);
		
		String testID = "This is a test ID";
		Topic topComment = new Topic(testID);
		Comment reply = new Comment();
		ArrayList<Viewable> c = new ArrayList<Viewable>();
		c.add(reply);
		topComment.setC(c);
		
		dm.saveFavourite(topComment); 
		
		activity.finish();
		setActivityIntent(intent);
		activity = getActivity();
		
		dm = new DataManager(testPath);
		try {
			dm.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Topic favorite = (Topic)dm.getData(testID);
		
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment, favorite);
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply, favorite.getC().get(0)); //compare the first child
		
		/* TODO: route this through the clientController
		 * This part needs connection to server or mockup via clientController
		 * 
		 * Comment secondReply = new Comment();
		c.add(secondReply);
		topComment.setC(c);
		
		dm.UpdateFavorites();//normally periodically called while connected to server
		
		assertTrue("top comment is still saved locally",favorites.contains(topComment));
		assertTrue("first reply is still saved locally",favorites.contains(reply)); //compare the first child
		assertTrue("second reply should be saved locally now",favorites.contains(secondreply));// compare second child
		 */
		activity.finish();
	}
	
	//TODO: rewrite tests so that they go through the clientController
}
