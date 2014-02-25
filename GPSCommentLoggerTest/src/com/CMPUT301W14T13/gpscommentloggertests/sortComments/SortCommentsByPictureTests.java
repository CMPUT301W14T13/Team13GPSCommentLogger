package com.CMPUT301W14T13.gpscommentloggertests.sortComments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

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
		thread.addChild(comment_1);
		thread.addChild(comment_2);
		thread.addChild(comment_3);

		/* sort by comments with pictures */
		thread.sortByImage();
		boolean sorted = true;
		boolean prev_has_img = true;

		/* check the ordering of the comments */
		for(Viewable comment : thread.getChildren()){
			/* if it switches from false to true then the comments have mixing of pictures and no pictures*/
			if( prev_has_img != comment.getHasImage() && prev_has_img == false ){
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
		Root root = new Root();

		/* make two comments and set their locations */
		Topic thread_1 = new Topic();
		Topic thread_2 = new Topic();
		Topic thread_3 = new Topic();

		/* set the image properties */
		thread_1.setHasImage(true); 
		thread_2.setHasImage(false);
		thread_3.setHasImage(true);

		/* add the comments to the thread */
		root.addChild(thread_1);
		root.addChild(thread_2);
		root.addChild(thread_3);

		/* sort by comments with pictures */
		root.sortByImage();
		boolean sorted = true;
		boolean prev_has_img = true;

		/* check the ordering of the comments */
		for(Viewable thread : root.getChildren()){
			/* if it switches from false to true then the comments have mixing of pictures and no pictures*/
			if( prev_has_img != thread.getHasImage() && prev_has_img == false ){
				sorted = false;
				break;
			}
			prev_has_img = thread.getHasImage();
		}

		/* check the comment order */
		assertTrue("failure - comments with and without images are mixed", sorted);

	}
}