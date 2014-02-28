package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.sql.DriverManager;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

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
		DataManager dm = new DataManager();//an unimplemented class that
										   // deals with local data

		assertNotNull(activity);
		Comment comment = new Comment();
		dm.SaveInFile(comment);//not implemented in code
		
		ArrayList<Comment> fromFile = dm.loadFromFile();// not implemented in code
		
		AssertTrue("The comment we load from file should be the same as the comment we saved",
				fromFile.contains(comment));
	}
	
	//Use Case 4.1
	public void testSetAsFavorite() {
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		DriverManager dm = new DataManager();
		assertNotNull(activity);

		Topic topComment = new Topic();
		Comment reply = new Comment();
		topComment.setC(reply);
		
		dm.setFavorite(topComment); // not Implemented
		ArrayList<Topic> favorites = dm.LoadFavorites();
		
		assertTrue("top comment we load is the same as the top comment we saved",
				favorites.contains(topComment));
		assertTrue("the reply we loaded is the same as the reply we saved",
				favorites.contains(reply)); //compare the first child
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
		Topic topComment = new Topic();
		Comment reply = new Comment();
		ArrayList<Viewable> c = {reply};
		DataManager dm = new DataManager();
		topComment.setC(c);
		
		dm.setFavorite(topComment); //not implemented
		
		ArrayList<Topic> favorites = dm.LoadFavorites();
		
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
