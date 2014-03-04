package com.CMPUT301W14T13.gpscommentloggertests.sortComments;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.RootView;
import com.CMPUT301W14T13.gpscommentlogger.view.SortParameter;
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

	String ID = "4324";
	String username = "Austin";
	Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //must add arguments
	String commentText = "Test comment";
	
	/* make comments with different dates */
	Comment comment_1 = new Comment(ID, username, picture, new Date(), commentText);
	Comment comment_2 = new Comment(ID, username, picture, new Date(0), commentText);
	Comment comment_3 = new Comment(ID, username, picture, new Date(Long.MAX_VALUE), commentText);

	/* add the comments to the thread */
	thread.addChild(comment_1);
	thread.addChild(comment_2);
	thread.addChild(comment_3);

	/* make a topic view object */
	TopicView topicView= new TopicView(thread);
	
	/* sort by newest comments */
	topicView.sortBy(SortParameter.TIME_NEW);
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
	topicView.sortBy(SortParameter.TIME_OLD);
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
	
	String ID = "4324";
	String username = "Austin";
	Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //must add arguments
	String commentText = "Test comment";
	
	/* make topics with different dates */
	Topic thread_1 = new Topic(ID, username, picture, new Date(), commentText);
	Topic thread_2 = new Topic(ID, username, picture, new Date(0), commentText);
	Topic thread_3 = new Topic(ID, username, picture, new Date(Long.MAX_VALUE), commentText);
	
	/* add the comments to the thread */
	root.addChild(thread_1);
	root.addChild(thread_2);
	root.addChild(thread_3);

	RootView rootView= new RootView(root);
	
	/* sort by newest comments */
	rootView.sortBy(SortParameter.TIME_NEW);
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
	rootView.sortBy(SortParameter.TIME_OLD);
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