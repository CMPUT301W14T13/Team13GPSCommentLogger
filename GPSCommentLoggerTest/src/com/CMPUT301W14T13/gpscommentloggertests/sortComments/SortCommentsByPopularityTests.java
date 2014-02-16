package com.CMPUT301W14T13.gpscommentloggertests;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

@SuppressLint("NewApi")
    public class SortCommentsByPopularityTests extends ActivityInstrumentationTestCase2<DebugActivity> {

    public SortCommentsByPopularityTests() {
	super(DebugActivity.class);
    }
    

    /* need to make a comment thread containing my comments 
     then check to ensure that the index of the comments within
     the array list are ordered correctly
     */
    public void testSortCommentsByPopularity()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Make a thread to contain the comments */
	CommentThread thread = new CommentThread();

	/* make two comments and set their vote counts */
	Comment comment_1 = new Comment();
	Comment comment_2 = new Comment();

	/* set the vote count of each */
	comment_1.setVoteCount(1);
	comment_2.setVoteCount(0);

	/* add the comments to the thread */
	thread.addComment(comment_1);
	thread.addComment(comment_2);

	thread.sortByPopularity();

	/* get the votes of the top two comments*/
	Integer v0 = thread.get(0).getVoteCount();
	Integer v1 = thread.get(1).getVoteCount();

	/* check the comment order */
	assertTrue("failure - comment 1 should preceed comment 2", v0 >= v1);
  

    }

    /* add this once you figure out how the threads are being stored */
    public void testSortCommentThreadsByPopularity()
    {

    }

}