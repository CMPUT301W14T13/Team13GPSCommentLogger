package com.CMPUT301W14T13.gpscommentloggertests;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentRoot;

import android.location.Location;
import android.location.LocationManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

@SuppressLint("NewApi")
    public class SortCommentsByProximityToCurrentLocationTests extends ActivityInstrumentationTestCase2<DebugActivity> {

    public SortCommentsByProximityToCurrentLocationTests() {
	super(DebugActivity.class);
    }
    

    /* Test the sorting of comments within a thread by distance from cur location */
    public void testSortCommentsByProximityToCurrent()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Get the current location */
	Context lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	Location my_gps = lm.getLastKnownLocation(GPS_PROVIDER);

	/* Make a thread to contain the comments */
	CommentThread thread = new CommentThread();

	/* make two comments and set their locations */
	Comment comment_1 = new Comment();
	Comment comment_2 = new Comment();
	Comment comment_3 = new Comment();

	/* set tokyo GPS: 35°41′22.22″N 139°41′30.12″E */
	Location tokyo = new Location(GPS_PROVIDER);
	tokyo.setLatitude(35.412222);
	tokyo.setLongitude(139.413012);

	/* set chicago GPS:41°52′55″N 087°37′40″W */
	Location chicago = new Location(GPS_PROVIDER);
	chicago.setLatitude(41.5255);
	chicago.setLongitude(87.3740);

	/* set the locations of the comments */
	comment_1.setLocation(my_gps); /* this should be bang on to my location */
	comment_2.setLocation(tokyo);
	comment_3.setLocation(chicago);

	/* add the comments to the thread */
	thread.addComment(comment_1);
	thread.addComment(comment_2);
	thread.addComment(comment_3);

	thread.sortByProximity();
	boolean sorted = true;
	Float prev_count = MIN_VALUE;

	/* check the ordering of the comments */
	for(Comment comment : thread.getComments()){
	    if( prev_count > my_gps.distanceTo(comment.getLocation())){
		sorted = false;
		break;
	    }
	    prev_count = my_gps.distanceTo(comment.getLocation());
	}
	
	/* check the comment order */
	assertTrue("failure - comments not sorted by ascending distance", sorted);

    }
    /* Test the sorting of threads distance from cur location */
    public void testSortCommentsByProximityToCurrent()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Get the current location */
	Context lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	Location my_gps = lm.getLastKnownLocation(GPS_PROVIDER);

	/* Make a thread to contain the comments */
	CommentRoot root = new CommentRoot();

	/* make two comments and set their locations */
	CommentThread thread_1 = new CommentThread();
	CommentThread thread_2 = new CommentThread();
	CommentThread thread_3 = new CommentThread();

	/* set tokyo GPS: 35°41′22.22″N 139°41′30.12″E */
	Location tokyo = new Location(GPS_PROVIDER);
	tokyo.setLatitude(35.412222);
	tokyo.setLongitude(139.413012);

	/* set chicago GPS:41°52′55″N 087°37′40″W */
	Location chicago = new Location(GPS_PROVIDER);
	chicago.setLatitude(41.5255);
	chicago.setLongitude(87.3740);

	/* set the locations of the comments */
	thread_1.setLocation(my_gps); /* this should be bang on to my location */
	thread_2.setLocation(tokyo);
	thread_3.setLocation(chicago);

	/* add the comments to the thread */
	root.addCommentThread(comment_1);
	root.addCommentThread(comment_2);
	root.addCommentThread(comment_3);

	thread.sortByProximity();
	boolean sorted = true;
	Float prev_count = MIN_VALUE;

	/* check the ordering of the comments */
	for(CommentThread thread : root.getCommentThreads()){
	    if( prev_count > my_gps.distanceTo(thread.getLocation())){
		sorted = false;
		break;
	    }
	    prev_count = my_gps.distanceTo(thread.getLocation());
	    
	}
	
	/* check the comment order */
	assertTrue("failure - comments not sorted by ascending distance", sorted);

    }

}