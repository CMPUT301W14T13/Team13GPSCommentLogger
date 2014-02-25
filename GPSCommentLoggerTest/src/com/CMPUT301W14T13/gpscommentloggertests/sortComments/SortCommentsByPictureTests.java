package com.CMPUT301W14T13.gpscommentloggertests.sortComments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;

@SuppressLint("NewApi")
    public class SortCommentsByPictureTests extends ActivityInstrumentationTestCase2<DebugActivity> {

    public SortCommentsByPictureTests() {
	super(DebugActivity.class);
    }
    

    /* Test the sorting of comments within a thread by picture proximity */
    public void testSortCommentsByPictures()
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
	comment_1.setHasImage(true); 
	comment_2.setHasImage(false);
	comment_3.setHasImage(true);

	/* add the comments to the thread */
	thread.addComment(comment_1);
	thread.addComment(comment_2);
	thread.addComment(comment_3);

	/* sort by comments with pictures */
	thread.sortByImage();
	boolean sorted = true;
	boolean prev_has_img = true;

	/* check the ordering of the comments */
	for(Comment comment : thread.getComments()){
	    /* if it switches from false to true then the comments have mixing of pictures and no pictures*/
	    if( prev_has_image != comment.getHasImage() && prev_has_image == false ){
		sorted = false;
		break;
	    }
	    prev_has_img = comment.getHasImage();
	}
	
	/* check the comment order */
	assertTrue("failure - comments with and without images are mixed", sorted);

    }
    /* Test the sorting of comments within a thread by picture proximity */
    public void testSortThreadsByPictures()
    {
	Intent intent = new Intent();
	setActivityIntent(intent);
	DebugActivity activity = getActivity();

	assertNotNull(activity);

	/* Make a thread to contain the comments */
	CommentRoot root = new CommentRoot();

	/* make two comments and set their locations */
	CommentThread thread_1 = new CommentThread();
	CommentThread thread_2 = new CommentThread();
	CommentThread thread_3 = new CommentThread();

	/* set the image properties */
	thread_1.setHasImage(true); 
	thread_2.setHasImage(false);
	thread_3.setHasImage(true);

	/* add the comments to the thread */
	root.addCommentThread(comment_1);
	root.addCommentThread(comment_2);
	root.addCommentThread(comment_3);

	/* sort by comments with pictures */
	thread.sortByImage();
	boolean sorted = true;
	boolean prev_has_img = true;

	/* check the ordering of the comments */
	for(CommentThread thread : root.getCommentThreads()){
	    /* if it switches from false to true then the comments have mixing of pictures and no pictures*/
	    if( prev_has_image != thread.getHasImage() && prev_has_image == false ){
		sorted = false;
		break;
	    }
	    prev_has_img = thread.getHasImage();
	}
	
	/* check the comment order */
	assertTrue("failure - comments with and without images are mixed", sorted);

    }