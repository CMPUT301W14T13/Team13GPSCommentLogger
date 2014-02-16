package com.CMPUT301W14T13.gpscommentloggertests.DownloadComments;

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
		
		ArrayList<Comment> fromFile = loadSavedFromFile();// not implemented in code
		
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
		
		//now check if file AND children have been saved locally
		//make reply
		//set top level comment as favorite
		//check file for comment and reply
		
	}
	
	//Use Case 4.1.1
	public void testUpdateFavorite() {
		CommentThread topComment = new CommentThread();
		Comment reply = new Comment();
		topComment.setC(reply);
		//now set as favorite
		SetFavorite(topComment); // saves comment and children into file
		//check to see if comment and children have been saved
		Comment secondReply = new Comment();
		topComment.setC(secondReply);
		// manually call update favorite which would normally operate
		// automatically while device connected
		
		//check to see if new comment saved 
		
	}
}
