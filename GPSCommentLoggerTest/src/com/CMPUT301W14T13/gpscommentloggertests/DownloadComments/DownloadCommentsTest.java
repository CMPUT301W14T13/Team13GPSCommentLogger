package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.io.IOException;
import java.util.ArrayList;

<<<<<<< HEAD
import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

=======
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

<<<<<<< HEAD
=======
import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
@SuppressLint("NewApi")
public class DownloadCommentsTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public DownloadCommentsTest() {
<<<<<<< HEAD
		super(DebugActivity.class);
		
=======
		super(DebugActivity.class);	
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
	}
	
	//Use Case 4
	public void testDownloadComments(){

		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
<<<<<<< HEAD
=======
		Comment comment = new Comment();
		dm.SaveInFile(comment);//not implemented in code
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
		
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
		
		/*assertEquals("The comment we load from file should be the same as the comment we saved",
				comment, fromFile);
		*/
		
		assertTrue("The comment we load from file should be the same as the comment we saved",
				comment.equals(fromFile));
		
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

<<<<<<< HEAD
		DataManager dm = new DataManager(testPath);
		
		String testID = "This is a test ID";
		Topic topComment = new Topic(testID);
=======
		Topic topComment = new Topic();
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
		Comment reply = new Comment();
		topComment.getC().add(reply);
		
		dm.saveFavourite(topComment);
		
<<<<<<< HEAD
		activity.finish();
		setActivityIntent(intent);
		activity = getActivity();
		
		dm = new DataManager(testPath);
		try {
			dm.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
=======
		dm.setFavorite(topComment); // not Implemented
		ArrayList<Topic> favorites = dm.LoadFavorites();
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
		
		Topic favoritedTopic = (Topic)dm.getFavourite(testID);
		
		/*
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment, favoritedTopic);
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply, favoritedTopic.getC().get(0)); //compare the first child
		*/
		assertTrue("top comment we load is the same as the top comment we saved",
				topComment.equals(favoritedTopic));
		assertTrue("the reply we loaded is the same as the reply we saved",
				reply.equals(favoritedTopic.getC().get(0))); //compare the first child
		
		activity.finish();
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
<<<<<<< HEAD
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
		String testPath =  activity.getFilesDir().getPath().toString() + "/test3.sav";
		Log.w("DownloadCommentTest", "Filepath = " + testPath);

		DataManager dm = new DataManager(testPath);
		
		String testID = "This is a test ID";
		Topic topComment = new Topic(testID);
=======
		Topic topComment = new Topic();
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
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
		
<<<<<<< HEAD
		Topic favoritedTopic = (Topic)dm.getFavourite(testID);
=======
		ArrayList<Topic> favorites = dm.LoadFavorites();
>>>>>>> e2b2849c80796d9e7a2581a302e6ee184c00fbb7
		
		/*
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment, favoritedTopic);
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply, favoritedTopic.getC().get(0)); //compare the first child
		*/
		assertTrue("top comment we load is the same as the top comment we saved",
				topComment.equals(favoritedTopic));
		assertTrue("the reply we loaded is the same as the reply we saved",
				reply.equals(favoritedTopic.getC().get(0))); //compare the first child
		
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
