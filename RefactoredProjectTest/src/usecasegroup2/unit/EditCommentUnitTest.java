package usecasegroup2.unit;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class EditCommentUnitTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public EditCommentUnitTest() {
		super(HomeView.class);
	}

	private String newText = "New text!";
	
	public void testEditTopic(){
		
		Topic topic = new Topic();
		assertTrue("Initial topic text should be blank", topic.getCommentText() == "");
		topic.setCommentText(newText);
		assertEquals("Topic text should read: 'New text!'",newText, topic.getCommentText()); 
		
	}
	
	public void testEditComment(){
		Comment comment = new Comment();
		assertTrue("Initial comment text should be blank", comment.getCommentText() == "");
		comment.setCommentText(newText);
		assertEquals("Topic text should read: 'New text!'",newText, comment.getCommentText()); 
	}
	
}
