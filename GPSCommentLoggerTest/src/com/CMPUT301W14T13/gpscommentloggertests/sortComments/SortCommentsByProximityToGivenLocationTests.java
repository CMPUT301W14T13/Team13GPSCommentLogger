package com.CMPUT301W14T13.gpscommentloggertests.sortComments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeView;
import com.CMPUT301W14T13.gpscommentlogger.view.TopicView;

@SuppressLint("NewApi")
public class SortCommentsByProximityToGivenLocationTests extends ActivityInstrumentationTestCase2<DebugActivity> {

	public SortCommentsByProximityToGivenLocationTests() {
		super(DebugActivity.class);
	}


	/* Test the sorting of comments within a thread by distance from cur location */
	public void testSortCommentsByProximityToGivenLocation()
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);

		/* Get the current location */
		LocationManager locationManager;
		Context mContext;	
		
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	
		/* Test from Jamaica 17°59′N 76°48′W */
		Location given_gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		given_gps.setLatitude(17.59);
		given_gps.setLongitude(76.48);

		/* Make a thread to contain the comments */
		Topic thread = new Topic();

		/* make two comments and set their locations */
		Comment comment_1 = new Comment();
		Comment comment_2 = new Comment();
		Comment comment_3 = new Comment();

		/* set tokyo GPS: 35°41′22.22″N 139°41′30.12″E */
		Location tokyo = new Location(LocationManager.GPS_PROVIDER);
		tokyo.setLatitude(35.412222);
		tokyo.setLongitude(139.413012);

		/* set chicago GPS:41°52′55″N 087°37′40″W */
		Location chicago = new Location(LocationManager.GPS_PROVIDER);
		chicago.setLatitude(41.5255);
		chicago.setLongitude(87.3740);

		/* set the locations of the comments */
		comment_1.setGPSLocation(given_gps); /* this should be bang on to my location */
		comment_2.setGPSLocation(tokyo);
		comment_3.setGPSLocation(chicago);

		/* add the comments to the thread */
		thread.addChild(comment_1);
		thread.addChild(comment_2);
		thread.addChild(comment_3);
		
		TopicView topicView= new TopicView(thread);
		
		topicView.sortBy("proximity");
		
		boolean sorted = true;
		Float prev_count = Float.MIN_VALUE;

		/* check the ordering of the comments */
		for(Viewable comment : thread.getChildren()){
			if( prev_count > given_gps.distanceTo(comment.getGPSLocation())){
				sorted = false;
				break;
			}
			prev_count = given_gps.distanceTo(comment.getGPSLocation());
		}

		/* check the comment order */
		assertTrue("failure - comments not sorted by ascending distance", sorted);

	}
	/* Test the sorting of threads distance from cur location */
	public void testSortThreadsByProximityToGivenLocation()
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);

		/* Test from Jamaica 17°59′N 76°48′W */
		/* Get the current location */
		LocationManager locationManager;
		Context mContext;	
		
		locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	
		/* Test from Jamaica 17°59′N 76°48′W */
		Location given_gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		given_gps.setLatitude(17.59);
		given_gps.setLongitude(76.48);

		/* Make a thread to contain the comments */
		Root root = new Root();

		/* make two comments and set their locations */
		Topic thread_1 = new Topic();
		Topic thread_2 = new Topic();
		Topic thread_3 = new Topic();

		/* set tokyo GPS: 35°41′22.22″N 139°41′30.12″E */
		Location tokyo = new Location(LocationManager.GPS_PROVIDER);
		tokyo.setLatitude(35.412222);
		tokyo.setLongitude(139.413012);

		/* set chicago GPS:41°52′55″N 087°37′40″W */
		Location chicago = new Location(LocationManager.GPS_PROVIDER);
		chicago.setLatitude(41.5255);
		chicago.setLongitude(87.3740);

		/* set the locations of the comments */
		thread_1.setGPSLocation(given_gps); /* this should be bang on to my location */
		thread_2.setGPSLocation(tokyo);
		thread_3.setGPSLocation(chicago);

		/* add the comments to the thread */
		root.addChild(thread_1);
		root.addChild(thread_2);
		root.addChild(thread_3);

		HomeView rootView= new HomeView(root);
		
		/* sort by closest comments */
		rootView.sortBy("proximity");
		
		boolean sorted = true;
		Float prev_count = Float.MIN_VALUE;

		/* check the ordering of the comments */
		for(Viewable thread : root.getChildren()){
			if( prev_count > given_gps.distanceTo(thread.getGPSLocation())){
				sorted = false;
				break;
			}
			prev_count = given_gps.distanceTo(thread.getGPSLocation());

		}

		/* check the comment order */
		assertTrue("failure - comments not sorted by ascending distance", sorted);

	}

}