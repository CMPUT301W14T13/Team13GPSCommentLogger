package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

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

		assertNotNull(activity);
		Comment comment = new comment();
		SaveInFile(comment);//not implemented in code
		
		ArrayList<Comment> fromFile = loadSavedCommentsFromFile();// not implemented in code
		
		AssertEquals(comment,fromFile.get(0));
	}
	
	//Use Case 4.1
	public void testSetAsFavorite() {
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);

		CommentThread topComment = new CommentRoot();
		Comment reply = new Comment();
		topComment.setC(reply);
		
		setFavorite(topComment); // not Implemented
		ArrayList<CommentThread> favorites = LoadFavorites();
		
		assertEquals(topComment,favorites.get(0));
		assertEquals(reply,favorites.get(0).getC(0)); //get the first child
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
		CommentThread topComment = new CommentThread();
		Comment reply = new Comment();
		ArrayList<Viewable> c = {reply};
		topComment.setC(c);
		//now set as favorite
		SetFavorite(topComment); //not implemented
		
		ArrayList<CommentThread> favorites = LoadFavorites();
		assertEquals(topComment,favorites.get(0));
		assertEquals(reply,favorites.get(0).getC(0)); //get the first child
		
		Comment secondReply = new Comment();
		c.add(secondReply);
		topComment.setC(c);
		
		UpdateFavorites();//normally periodically called while connected to server
		
		assertEquals(topComment,favorites.get(0));
		assertEquals(reply,favorites.get(0).getC(0)); //compare the first child
		assertEquals(secondReply,favorites.get(0).getC(1));// compare second child

		
	}
}
