package com.CMPUT301W14T13.gpscommentloggertests.sortComments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.RootView;
import com.CMPUT301W14T13.gpscommentlogger.view.TopicView;

@SuppressLint("NewApi")
public class SortCommentsByPopularityTests extends ActivityInstrumentationTestCase2<DebugActivity> {

	public SortCommentsByPopularityTests() {
		super(DebugActivity.class);
	}


	/* Test the sorting of comments within a thread by popularity */
	public void testSortCommentsByPopularity()
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);

		/* Make a thread to contain the comments */
		Topic thread = new Topic();

		/* make two comments and set their vote counts */
		Comment comment_1 = new Comment();
		Comment comment_2 = new Comment();
		Comment comment_3 = new Comment();

		/* set the vote count of each */
		comment_1.setPopularity(1);
		comment_2.setPopularity(0);
		comment_3.setPopularity(87);

		/* add the comments to the thread */
		thread.addChild(comment_1);
		thread.addChild(comment_2);
		thread.addChild(comment_3);
		
		TopicView topicView= new TopicView(thread);
		
		topicView.sortBy("popularity");
		boolean sorted = true;
		Integer prev_count = Integer.MAX_VALUE;

		/* check the ordering of the comments */
		for(Viewable comment : thread.getChildren()){
			if( prev_count < comment.getPopularity()){
				sorted = false;
				break;
			}
			prev_count = comment.getPopularity();
		}

		/* check the comment order */
		assertTrue("failure - comments not sorted by descending popularity", sorted);

		/* test using negative popularities */
		thread.getChildren().get(0).setPopularity(-10);
		thread.getChildren().get(1).setPopularity(-99);

		topicView.sortBy("popularity");

		sorted = true;
		prev_count = Integer.MAX_VALUE;

		/* check comment ordering */
		for(Viewable comment : thread.getChildren()){
			if( prev_count < comment.getPopularity()){
				sorted = false;
				break;
			}
			prev_count = comment.getPopularity();
		}


		/* check the comment order */
		assertTrue("failure - comments not sorted by descending popularity", sorted);

	}

	/* Test the sorting of entire CommentThreads by popularity */
	public void testSortCommentThreadsByPopularity()
	{

		Intent intent = new Intent();
		setActivityIntent(intent);
		DebugActivity activity = getActivity();

		assertNotNull(activity);

		/* Make a thread to contain the comments */
		Root root = new Root();

		/* make two comments and set their vote counts */
		Topic thread_1 = new Topic();
		Topic thread_2 = new Topic();
		Topic thread_3 = new Topic();

		/* set the vote count of each */
		thread_1.setPopularity(1);
		thread_2.setPopularity(0);
		thread_3.setPopularity(101);

		/* add the comments to the thread */
		root.addChild(thread_1);
		root.addChild(thread_2);
		root.addChild(thread_3);
		
		RootView rootView= new RootView(root);
		
		rootView.sortBy("popularity");
		boolean sorted = true;
		Integer prev_count = Integer.MAX_VALUE;

		/* check the ordering of the comments */
		for(Viewable thread : root.getChildren()){
			if( prev_count < thread.getPopularity()){
				sorted = false;
				break;
			}
			prev_count = thread.getPopularity();
		}

		/* check the comment order */
		assertTrue("failure - comments not sorted by descending popularity", sorted);

		/* test using negative popularities */
		thread_1.setPopularity(-10);
		thread_2.setPopularity(-99);

		rootView.sortBy("popularity");
		sorted = true;
		prev_count = Integer.MAX_VALUE;

		/* check the ordering of the comments */
		for(Viewable thread : root.getChildren()){
			if( prev_count < thread.getPopularity()){
				sorted = false;
				break;
			}
			prev_count = thread.getPopularity();
		}
		/* check the comment order */
		assertTrue("failure - comments not sorted by descending popularity", sorted);

	}

}