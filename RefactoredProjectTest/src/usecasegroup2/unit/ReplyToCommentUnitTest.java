package usecasegroup2.unit;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class ReplyToCommentUnitTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public ReplyToCommentUnitTest() {
		super(HomeView.class);
	}

	private String reply1 = "301 is lots of work!";
	private String reply2 = "301 is super fun...";
	
	public void testReplyToTopic(){
		
		CommentTreeElement topic = new Topic();
		topic.setCommentText("Hey you!");
		
		assertTrue("There are no replies to our topic.", topic.getNumberOfChildren() == 0);
		
		CommentTreeElement comment1 = new Comment();
		comment1.setCommentText(reply1);
		
		CommentTreeElement comment2 = new Comment();
		comment2.setCommentText(reply2);
		
		topic.addChild(comment1);
		topic.addChild(comment2);
		
		assertTrue("There are two replies to our topic.", topic.getNumberOfChildren() == 2);
		assertEquals("The first comment that replied to the topic matches what has been put there", 
				comment1.getCommentText(), topic.getChildren().get(0).getCommentText());
		assertEquals("The second comment that replied to the topic matches what has been put there", 
				comment2.getCommentText(), topic.getChildren().get(1).getCommentText());

	}
	
	public void testReplyToComment(){
		
		CommentTreeElement top_comment = new Comment();
		top_comment.setCommentText("Hey you!");
		
		assertTrue("There are no replies to our topic.", top_comment.getNumberOfChildren() == 0);
		
		CommentTreeElement comment1 = new Comment();
		comment1.setCommentText(reply1);
		
		CommentTreeElement comment2 = new Comment();
		comment2.setCommentText(reply2);
		
		top_comment.addChild(comment1);
		top_comment.addChild(comment2);
		
		assertTrue("There are two replies to our topic.", top_comment.getNumberOfChildren() == 2);
		assertEquals("The first comment that replied to the topic matches what has been put there", 
				comment1.getCommentText(), top_comment.getChildren().get(0).getCommentText());
		assertEquals("The second comment that replied to the topic matches what has been put there", 
				comment2.getCommentText(), top_comment.getChildren().get(1).getCommentText());
	
	}
}
