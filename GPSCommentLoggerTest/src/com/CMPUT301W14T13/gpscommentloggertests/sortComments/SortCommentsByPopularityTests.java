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
import com.CMPUT301W14T13.gpscommentlogger.view.SortParameter;
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

		String[] phone_hashes = new String[3];
		phone_hashes[0] = "49f0bad299687c62334182178bfd75d8";
		phone_hashes[1] = "026f70ac4899ae2e829d0538281960ff";
		phone_hashes[2] = "aa42b4ad1657c7d912db1cdaa88a30d4";
		
		/* set the vote count of each */
		for (int i = 0; i < phone_hashes.length ; i++){ 
			comment_1.upVote(phone_hashes[i]);
			comment_3.downVote(phone_hashes[i]);
		}
		/* add the comments to the thread */
		thread.addChild(comment_1);
		thread.addChild(comment_2);
		thread.addChild(comment_3);

		TopicView topicView= new TopicView(thread);

		topicView.sortBy(SortParameter.POPULARITY);
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

		String[] phone_hashes = new String[3];
		phone_hashes[0] = "49f0bad299687c62334182178bfd75d8";
		phone_hashes[1] = "026f70ac4899ae2e829d0538281960ff";
		phone_hashes[2] = "aa42b4ad1657c7d912db1cdaa88a30d4";
		
		/* set the vote count of each */
		for (int i = 0; i < phone_hashes.length ; i++){ 
			thread_1.upVote(phone_hashes[i]);
			thread_3.downVote(phone_hashes[i]);
		}

		/* add the comments to the thread */
		root.addChild(thread_1);
		root.addChild(thread_2);
		root.addChild(thread_3);

		RootView rootView= new RootView(root);

		rootView.sortBy(SortParameter.POPULARITY);
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

	}
}