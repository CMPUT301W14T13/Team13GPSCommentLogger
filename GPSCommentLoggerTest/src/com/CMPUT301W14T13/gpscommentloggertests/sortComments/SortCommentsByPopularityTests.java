package com.CMPUT301W14T13.gpscommentloggertests;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentRoot;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentThread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

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
	CommentThread thread = new CommentThread();

	/* make two comments and set their vote counts */
	Comment comment_1 = new Comment();
	Comment comment_2 = new Comment();
	Comment comment_3 = new Comment();

	/* set the vote count of each */
	comment_1.setVoteCount(1);
	comment_2.setVoteCount(0);
	comment_3.setVoteCount(87);

	/* add the comments to the thread */
	thread.addComment(comment_1);
	thread.addComment(comment_2);
	thread.addComment(comment_3);

	thread.sortByPopularity();
	boolean sorted = true;
	Integer prev_count = Integer.MAX_VALUE;

	/* check the ordering of the comments */
	for(Comment comment : thread.getComments()){
	    if( prev_count < comment.getVoteCount()){
		sorted = false;
		break;
	    }
	    prev_count = comment.getVoteCount();
	}

	/* check the comment order */
	assertTrue("failure - comments not sorted by descending popularity", sorted);

	/* test using negative popularities */
	thread.getComments().get(0).setVoteCount(-10);
	thread.getComments().get(1).setVoteCount(-99);

	thread.sortByPopularity();

	sorted = true;
	prev_count = Integer.MAX_VALUE;

	/* check comment ordering */
	for(Comment comment : thread.getComments()){
	    if( prev_count < comment.getVoteCount()){
		sorted = false;
		break;
	    }
	    prev_count = comment.getVoteCount();
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
	CommentRoot root = new CommentRoot();

	/* make two comments and set their vote counts */
	CommentThread thread_1 = new CommentThread();
	CommentThread thread_2 = new CommentThread();
	CommentThread thread_3 = new CommentThread();

	/* set the vote count of each */
	thread_1.setVoteCount(1);
	thread_2.setVoteCount(0);
	thread_3.setVoteCount(101);

	/* add the comments to the thread */
	root.addCommentThread(thread_1);
	root.addCommentThread(thread_2);
	root.addCommentThread(thread_3);

	root.sortByPopularity();
	boolean sorted = true;
	Integer prev_count = Integer.MAX_VALUE;

	/* check the ordering of the comments */
	for(CommentThread thread : root.getCommentThreads()){
	    if( prev_count < thread.getVoteCount()){
		sorted = false;
		break;
	    }
	    prev_count = thread.getVoteCount();
	}

	/* check the comment order */
	assertTrue("failure - comments not sorted by descending popularity", sorted);

	/* test using negative popularities */
	thread_1.setVoteCount(-10);
	thread_2.setVoteCount(-99);

	thread.sortByPopularity();
	sorted = true;
	prev_count = Integer.MAX_VALUE;

	/* check the ordering of the comments */
	for(CommentThread thread : root.getThreads()){
	    if( prev_count < thread.getVoteCount()){
		sorted = false;
		break;
	    }
	    prev_count = thread.getVoteCount();
	}
	/* check the comment order */
	assertTrue("failure - comments not sorted by descending popularity", sorted);

    }

}