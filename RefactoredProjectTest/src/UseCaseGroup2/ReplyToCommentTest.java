package UseCaseGroup2;

import org.junit.Before;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class ReplyToCommentTest extends ActivityInstrumentationTestCase2<HomeView> {

	CommentTree ct;
	
	public ReplyToCommentTest() {
		super(HomeView.class);
	}
	
	@Before
	public void setUp() {
		ct = CommentTree.getInstance();
	}
	
	public void testSetReply() {
		// set up two comments, one will be parents and one will be child
		CommentTreeElement comment = new Comment();
		CommentTreeElement commentReply = new Comment();
		
		// check comment has no children
		assertEquals("Comment already has children.", 0, comment.getChildren().size());
		
		// add reply
		comment.addChild(commentReply);
		
		// check if first child is equal to commentReply
		assertEquals("Reply is different than commentReply", commentReply, comment.getChildren().get(0));
		
		
	}
	

}
