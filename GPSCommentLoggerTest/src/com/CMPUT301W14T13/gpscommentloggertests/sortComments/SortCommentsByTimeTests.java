package com.CMPUT301W14T13.gpscommentloggertests.sortComments;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeView;
import com.CMPUT301W14T13.gpscommentlogger.view.TopicView;

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
	thread.addChild(comment_1);
	thread.addChild(comment_2);
	thread.addChild(comment_3);

	/* make a topic view object */
	TopicView topicView= new TopicView(thread);
	
	/* sort by newest comments */
	topicView.sortBy("time_fresh");
	boolean sorted = true;
	Date prev_date = new Date(0);

	/* check the ordering of the comments */
	for(Viewable comment : thread.getChildren()){
	    if( prev_date.after(comment.getTimestamp()) ){
		sorted = false;
		break;
	    }
	    prev_date = comment.getTimestamp();
	}
	
	/* check the comment order */
	assertTrue("failure - comments not from newest first", sorted);

	/* sort by oldest comments */
	topicView.sortBy("time_old");
	sorted = true;
	prev_date = new Date(Long.MAX_VALUE);

	/* check the ordering of the comments */
	for(Viewable comment : thread.getChildren()){
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
	root.addChild(thread_1);
	root.addChild(thread_2);
	root.addChild(thread_3);

	HomeView rootView= new HomeView(root);
	
	/* sort by newest comments */
	rootView.sortBy("time_new");
	boolean sorted = true;
	Date prev_date = new Date(0);

	/* check the ordering of the comments */
	for(Viewable thread : root.getChildren()){
	    if( prev_date.after(thread.getTimestamp())){
		sorted = false;
		break;
	    }
	    prev_date = thread.getTimestamp();
	}
	
	/* check the comment order */
	assertTrue("failure - comments not from newest first", sorted);

	/* sort by oldest comments */
	rootView.sortBy("time_old");
	sorted = true;
	prev_date = new Date(Long.MAX_VALUE);

	/* check the ordering of the comments */
	for(Viewable thread : root.getChildren()){
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