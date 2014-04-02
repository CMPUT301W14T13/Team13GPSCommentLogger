package UseCaseGroup1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class SortCommentsByPicturesTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public SortCommentsByPicturesTest() {
		super(HomeView.class);
	}

	
	
	public void testSortByPicture() throws InterruptedException{
	
		HomeView activity;
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();
		
		
		ArrayList<CommentTreeElement> topics = new ArrayList<CommentTreeElement>();
		Topic topic = new Topic();
		Location originalLocation = LocationSelection.getInstance().getLocation();
		Location location = new Location(originalLocation);

		double latitude = 53.4;
		double longitude = -113;


		for (int i = 0; i <= 5; i++){
			topic = new Topic();
			location = new Location(location);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			
			if (i % 2 == 0){
				topic.setImage(Bitmap.createBitmap(5,i+1, Config.ARGB_8888));
			}
			
			topic.setGPSLocation(location);
			topics.add(topic);
			latitude += 0.1;
		}
			
			
			topics = SortFunctions.sortByPicture(topics);
			
			
			boolean picture = true;
			for (int j = 0; j < topics.size(); j ++){
				
				if (!topics.get(j).getHasImage()){
					picture = false;
				}
				
				if (!picture){
					assertFalse("There should be no pictures after a topic is found with no image", topics.get(j).getHasImage());
				}
				else{
					assertTrue("There should be a picture", topics.get(j).getHasImage());
				}
			}
		}
}
	
	
