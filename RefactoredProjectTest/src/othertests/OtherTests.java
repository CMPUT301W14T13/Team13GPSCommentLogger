package othertests;

import static org.junit.Assert.*;

import org.junit.Test;

import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.views.HomeView;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;


public class OtherTests extends ActivityInstrumentationTestCase2<HomeView>
{

	public OtherTests()
	{
		super(HomeView.class);
	}

	public void testResume() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		HomeView activity = getActivity();
		
		Thread.sleep(10000);
		
		assertNotNull(CommentTree.getInstance().getCommentList(activity));
		assertFalse(CommentTree.getInstance().getCommentList(activity).isEmpty());
	}
	
	public void testUpdateInHomeView() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		final HomeView activity = getActivity();
		
		Thread.sleep(10000);
		
		assertNotNull(CommentTree.getInstance().getCommentList(activity));
		assertFalse(CommentTree.getInstance().getCommentList(activity).isEmpty());
		activity.runOnUiThread(
					new Runnable(){
						@Override
						public void run(){
							activity.update();
						}
					}
				);
		Thread.sleep(1000);
		assertNotNull(CommentTree.getInstance().getCommentList(activity));
		assertFalse(CommentTree.getInstance().getCommentList(activity).isEmpty());
	}
	
	public void testUpdateInHomeView() throws InterruptedException
	{
		Intent intent = new Intent();
		setActivityIntent(intent);
		final HomeView activity = getActivity();
		
		Thread.sleep(10000);
		
		assertNotNull(CommentTree.getInstance().getCommentList(activity));
		assertFalse(CommentTree.getInstance().getCommentList(activity).isEmpty());
		activity.runOnUiThread(
					new Runnable(){
						@Override
						public void run(){
							activity.update();
						}
					}
				);
		Thread.sleep(1000);
		assertNotNull(CommentTree.getInstance().getCommentList(activity));
		assertFalse(CommentTree.getInstance().getCommentList(activity).isEmpty());
	}

}
