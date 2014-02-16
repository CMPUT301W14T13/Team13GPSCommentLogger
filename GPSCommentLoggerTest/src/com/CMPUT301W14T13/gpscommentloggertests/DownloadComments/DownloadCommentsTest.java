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
		
		AssertEquals("The comment we load from file should be the same as the comment we saved",
				comment,fromFile.get(0));
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
		
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment,favorites.get(0));
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply,favorites.get(0).getC(0)); //get the first child
		
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
		
		assertEquals("top comment we load is the same as the top comment we saved",
				topComment,favorites.get(0));
		assertEquals("the reply we loaded is the same as the reply we saved",
				reply,favorites.get(0).getC(0)); //compare the first child
		
		Comment secondReply = new Comment();
		c.add(secondReply);
		topComment.setC(c);
		
		dm.UpdateFavorites();//normally periodically called while connected to server
		
		assertEquals("top comment is the same",topComment,favorites.get(0));
		assertEquals("first reply is the same",reply,favorites.get(0).getC(0)); //compare the first child
		assertEquals("second reply should be saved locally now",secondReply,favorites.get(0).getC(1));// compare second child

		
	}
}
