package com.CMPUT301W14T13.gpscommentloggertests;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

@SuppressLint("NewApi")
    public class SortCommentsByProximityToCurrentLocationTests extends ActivityInstrumentationTestCase2<DebugActivity> {

    public SortCommentsByProximityToCurrentLocationTests() {
	super(DebugActivity.class);
    }
    

    /* need to make a comment thread containing my comments 
     then check to ensure that the index of the comments within
     the array list are ordered correctly
     */
    public void testSortCommentsByProximityToCurrent()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Make a thread to contain the comments */
	CommentThread thread = new CommentThread();

	/* make two comments and set their locations */
	Comment comment_1 = new Comment();
	Comment comment_2 = new Comment();

	/* add the comments to the thread */
	thread.addComment(comment_1);
	thread.addComment(comment_2);

	thread.sortByProximity();

	/* finish the rest of the test */
	

    }
}