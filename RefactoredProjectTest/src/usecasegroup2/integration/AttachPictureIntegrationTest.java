package usecasegroup2.integration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class AttachPictureIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public AttachPictureIntegrationTest() {
		super(HomeView.class);
	}

	private Bitmap picture = Bitmap.createBitmap(1,1, Config.ARGB_8888); //must add arguments

	public void testAttachCommentPicture(){
		
		/* make a new comment */
		Comment comment = new Comment();
		assertFalse("The comment should not initially have a picture", comment.getHasImage());

		/*add a picture to the comment and topic */
		comment.setImage(picture);
		assertTrue("The comment should have a picture", comment.getHasImage());	
	}

	public void testAttachTopicPicture(){
		
		/* make a new topic */
		Topic topic = new Topic();
		assertFalse("The topic should not initially have a picture", topic.getHasImage());		
		topic.setImage(picture);
		assertTrue("The topic should have a picture", topic.getHasImage());

	}
	
}
