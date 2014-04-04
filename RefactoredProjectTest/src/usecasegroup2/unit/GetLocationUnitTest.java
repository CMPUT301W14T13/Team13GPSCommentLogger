package usecasegroup2.unit;

import android.annotation.SuppressLint;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class GetLocationUnitTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public GetLocationUnitTest() {
		super(HomeView.class);
	}
	
	public void testGetLocationTopic(){
		/* Initialize the topic */
		CommentTreeElement topic = new Topic();
		Location location = new Location("test");
	    double latitude=0;
	    double longitude=0;
	    location.setLatitude(latitude);
	    location.setLongitude(longitude);
		topic.setGPSLocation(location);
		
		assertEquals("The GPS coordinates do not match",location,topic.getGPSLocation());
		
	}
	
	public void testGetLocationComment(){
		/* Initialize the comment */
		CommentTreeElement comment = new Comment();
		Location location = new Location("test");
	    double latitude=0;
	    double longitude=0;
	    location.setLatitude(latitude);
	    location.setLongitude(longitude);
		comment.setGPSLocation(location);
		assertEquals("The GPS coordinates do not match",location,comment.getGPSLocation());
		
	}
		
}
