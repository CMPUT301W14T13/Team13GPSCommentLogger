package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

import java.io.IOException;
import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
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
		
		activity.simulateConnectToServer(); // need to be connected to server
		assertNotNull(activity.getCurrentComment());
		assertEquals("default comment is a root", true, activity.getCurrentComment() instanceof Root);
		
		String testID = "This is a test ID for topComment";
		Topic topComment = new Topic(testID);
		activity.simulateAddComment(topComment);
		activity.simulateBrowseClick(0);
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("first layer is a Topic", true, activity.getCurrentComment() instanceof Topic);
		assertEquals("The Topic should have the correct testID",
				testID.equals(activity.getCurrentComment().getID()));
		
		
		String testID = "This is a test ID for comment";
		Comment comment = new Comment(testID);
		String testID2 = "This is a test ID for comment2";
		Comment comment2 = new Comment(testID2);
		
		
		activity.simulateAddComment(comment); // not implemented in this branch but is in the master
		activity.simulateAddComment(comment2);
		Thread.sleep(2000);
		// i do not know how to get this index 
		activity.simulateSaveClick(topComment.index);//we need a saveButton click
		Thread.sleep(2000);
		
		activity.simulateDisconnectFromServer();
		Thread.sleep(2000);
		
		activity.simulateBrowseClick(topComment.index);// this should load the offline saved comment and i believe set it as current comment
		Thread.sleep(2000);
		
		assertTrue("both the comment we save and the comment we loaded should be the same",
				topComment.equals(activity.getCurrentComment()));
		
		activity.simulateBrowseClick(0);
		Thread.sleep(2000);
		// we dont save children so our current comment should still be the same
		assertTrue("both the comment we save and the comment we loaded should be the same",
				topComment.equals(activity.getCurrentComment()));

	
		
		activity.finish();
	}
	
	//Use Case 4.1
	public void testSetAsFavorite() {
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
		activity.simulateConnectToServer(); // need to be connected to server
		assertNotNull(activity.getCurrentComment());
		assertEquals("default comment is a root", true, activity.getCurrentComment() instanceof Root);
		
		String testID = "This is a test ID for topComment";
		Topic topComment = new Topic(testID);
		activity.simulateAddComment(topComment);
		activity.simulateBrowseClick(0);
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("first layer is a Topic", true, activity.getCurrentComment() instanceof Topic);
		assertEquals("The Topic should have the correct testID",
				testID.equals(activity.getCurrentComment().getID()));
		
		
		String testID = "This is a test ID for comment";
		Comment comment = new Comment(testID);
		String testID2 = "This is a test ID for comment2";
		Comment comment2 = new Comment(testID2);
		
		//add to child comments to topComment
		activity.simulateAddComment(comment); // not implemented in this branch but is in the master
		activity.simulateAddComment(comment2);
		Thread.sleep(2000);
		
		// i do not know how to get this index 
		activity.simulateSaveClick(topComment.index);//we need a saveButton click
		Thread.sleep(2000);
		
		activity.simulateDisconnectFromServer();
		Thread.sleep(2000);
		
		activity.simulateBrowseClick(topComment.index);// this should load the offline saved comment and i believe set it as current comment
		Thread.sleep(2000);
		
		assertTrue("both the comment we save and the comment we loaded should be the same",
				topComment.equals(activity.getCurrentComment()));
		
		activity.simulateBrowseClick(0);
		Thread.sleep(2000);
		// we save child comments  as well
		assertTrue("first child comment should be loaded",
				comment.equals(activity.getCurrentComment()));

	
		
		activity.finish();
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();
		assertNotNull(activity);
		
		activity.simulateConnectToServer(); // need to be connected to server
		assertNotNull(activity.getCurrentComment());
		assertEquals("default comment is a root", true, activity.getCurrentComment() instanceof Root);
		
		String testID = "This is a test ID for topComment";
		Topic topComment = new Topic(testID);
		activity.simulateAddComment(topComment);
		activity.simulateBrowseClick(0);
		Thread.sleep(2000);
		
		assertNotNull(activity.getCurrentComment());
		assertEquals("first layer is a Topic", true, activity.getCurrentComment() instanceof Topic);
		assertEquals("The Topic should have the correct testID",
				testID.equals(activity.getCurrentComment().getID()));
		
		
		String testID = "This is a test ID for comment";
		Comment comment = new Comment(testID);
		String testID2 = "This is a test ID for comment2";
		Comment comment2 = new Comment(testID2);
		
		//add to child comments to topComment
		activity.simulateAddComment(comment); // not implemented in this branch but is in the master
		Thread.sleep(2000);
		
		// i do not know how to get this index 
		activity.simulateSaveClick(topComment.index);//we need a saveButton click
		Thread.sleep(2000);
		
		activity.simulateDisconnectFromServer();
		Thread.sleep(2000);
		
		activity.simulateBrowseClick(topComment.index);// this should load the offline saved comment and i believe set it as current comment
		Thread.sleep(2000);
		
		assertTrue("both the comment we save and the comment we loaded should be the same",
				topComment.equals(activity.getCurrentComment()));
		
		activity.simulateBrowseClick(0);
		Thread.sleep(2000);
		// we save child comments  as well
		assertTrue("first child comment should be loaded",
				comment.equals(activity.getCurrentComment()));
		
		activity.simulateConnectToServer();
		Thread.sleep(2000);
		activity.simulateAddComment(comment2);
		activity.simulateDisconnectFromServer();
		Thread.sleep(2000);
		assertTrue("topComment should have two children ",activity.getCurrentComment().getC().size() == 2);
		//do not know the correct index
		activity.simulateBrowseClick(comment2.index);
		Thread.sleep(2000);
		asserTrue("currently viewing comment2", comment2.equals(activity.getCurrentComment()));
		
		
	
		
		activity.finish();
	}
	
	//TODO: rewrite tests so that they go through the clientController
}
