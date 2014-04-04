package usecasegroup3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.controllers.TopicViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;

@SuppressLint("NewApi")
public class BrowseCommentRepliesTest extends
ActivityInstrumentationTestCase2<HomeView> {
	
	TopicView topicView;
	Intent intent;
	TopicViewController edit;
	CommentTree ct = CommentTree.getInstance();
	
	Comment parent = new Comment();
	Comment child = new Comment();

	public BrowseCommentRepliesTest() {
		super(HomeView.class);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		intent = new Intent();
		setActivityIntent(intent);
		
		// add child as reply to parent
		parent.addChild(child);		
	}
	
	
	
	
	

}
