package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.sql.DriverManager;
import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentloggertests.makeComments.DebugActivity;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

public class DownloadCommentsTest extends ActivityInstrumentationTestCase2<DebugActivity> {

	public DownloadCommentsTest(String name) {
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
		Comment comment = new comment();
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

		CommentThread topComment = new CommentThread();
		Comment reply = new Comment();
		topComment.setC(reply);
		
		dm.setFavorite(topComment); // not Implemented
		ArrayList<CommentThread> favorites = dm.LoadFavorites();
		
		assertTrue("top comment we load is the same as the top comment we saved",
				favorites.contains(topComment));
		assertTrue("the reply we loaded is the same as the reply we saved",
				favorites.contains(reply)); //compare the first child
		
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
