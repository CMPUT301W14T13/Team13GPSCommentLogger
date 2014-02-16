package com.CMPUT301W14T13.gpscommentloggertests;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import java.util.Date;

@SuppressLint("NewApi")
    public class SortCommentsByTimeTests extends ActivityInstrumentationTestCase2<DebugActivity> {

    public SortCommentsByTimeTests() {
	super(DebugActivity.class);
    }
    

    /* Test the sorting of comments within a thread by distance from cur location */
    public void testSortCommentsByTime()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Make a thread to contain the comments */
	Topic thread = new Topic();

	/* make two comments and set their locations */
	Comment comment_1 = new Comment();
	Comment comment_2 = new Comment();
	Comment comment_3 = new Comment();

	/* set the locations of the comments */
	comment_1.setTimestamp(new Date()); /* current time*/
	comment_2.setTimestamp(new Date(0)); /* epoch */
	comment_3.setTimestamp(new Date(Long.MAX_VALUE)); /* maximum possible time*/

	/* add the comments to the thread */
	thread.addComment(comment_1);
	thread.addComment(comment_2);
	thread.addComment(comment_3);

	/* sort by newest comments */
	thread.sortByTimeFresh();
	boolean sorted = true;
	Date prev_date = new Date(0);

	/* check the ordering of the comments */
	for(Comment comment : thread.getComments()){
	    if( prev_date.after(comment.getTimestamp()) ){
		sorted = false;
		break;
	    }
	    prev_date = comment.getTimestamp();
	}
	
	/* check the comment order */
	assertTrue("failure - comments not from newest first", sorted);

	/* sort by oldest comments */
	thread.sortByTimeOldest();
	sorted = true;
	prev_date = new Date(Long.MAX_VALUE);

	/* check the ordering of the comments */
	for(Comment comment : thread.getComments()){
	    if( prev_date.before(comment.getTimestamp()) ){
		sorted = false;
		break;
	    }
	    prev_date = comment.getTimestamp();
	}
	
	/* check the comment order */
	assertTrue("failure - comments not from oldest first", sorted);


    }
    /* Test the sorting of threads distance from cur location */
    public void testSortThreadsByTime()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Make a thread to contain the comments */
	Root root = new Root();

	/* make two comments and set their locations */
	Topic thread_1 = new Topic();
	Topic thread_2 = new Topic();
	Topic thread_3 = new Topic();

	/* set the locations of the comments */
	thread_1.setTimestamp(new Date()); /* current time*/
	thread_2.setTimestamp(new Date(0)); /* epoch */
	thread_3.setTimestamp(new Date(Long.MAX_VALUE)); /* maximum possible time*/

	/* add the comments to the thread */
	root.addCommentThread(comment_1);
	root.addCommentThread(comment_2);
	root.addCommentThread(comment_3);

	/* sort by newest comments */
	thread.sortByTimeFresh();
	boolean sorted = true;
	Date prev_date = new Date(0);

	/* check the ordering of the comments */
	for(Topic thread : root.getCommentThreads()){
	    if( prev_date.after(thread.getTimestamp())){
		sorted = false;
		break;
	    }
	    prev_date = thread.getTimestamp();
	}
	
	/* check the comment order */
	assertTrue("failure - comments not from newest first", sorted);

	/* sort by oldest comments */
	thread.sortByTimeOldest();
	sorted = true;
	prev_date = new Date(Long.MAX_VALUE);

	/* check the ordering of the comments */
	for(Topic thread : root.getCommentThreads()){
	    if( prev_date.before(thread.getTimestamp()) ){
		sorted = false;
		break;
	    }
	    prev_date = thread.getTimestamp();
	}
	
	/* check the comment order */
	assertTrue("failure - comments not from oldest first", sorted);

    }

}