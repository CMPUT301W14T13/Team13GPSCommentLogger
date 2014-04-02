package UseCaseGroup1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class SortCommentsByRelevancyTest extends ActivityInstrumentationTestCase2<HomeView> {

	public SortCommentsByRelevancyTest() {
		super(HomeView.class);
	}

	
	
	public void testSortByMostRelevant() throws InterruptedException{
		
		HomeView activity;
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();
		ArrayList<CommentTreeElement> topics = new ArrayList<CommentTreeElement>();
		Topic topic = new Topic();
		Location originalLocation = new Location("default");
		Location location = new Location(originalLocation);
		
		double latitude = 53.4;
		double longitude = -113;
		
		
		for (int i = 0; i <= 5; i++){
			topic = new Topic();
			location = new Location(location);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			
			topic.setGPSLocation(location);
			topics.add(topic);
			Thread.sleep(1000);
			latitude += 0.1;
		}
	
		topics = SortFunctions.sortByMostRelevant(topics);
		
		for (int i = 0; i < topics.size(); i++){
			System.out.println(topics.get(i).getTimestamp() + " " + topics.get(i).getGPSLocation().getLatitude() + " " + topics.get(i).getGPSLocation().getLongitude());
		}

		

		//Every topic should be more relevant (newer and closer) than the ones after it
		for (int i = 0; i < topics.size(); i++){
			for (int j = i; j < topics.size(); j++){

				if (j != topics.size() - 1 && j != i && originalLocation.distanceTo(location) <= 50000){
					double distance = topics.get(i).getGPSLocation().distanceTo(topics.get(j).getGPSLocation());
					assertTrue("Comments should be newer", topics.get(i).getTimestamp().after(topics.get(j).getTimestamp()));
					assertTrue("Comments should be closer than ones after it", distance <= 50000);
					
				}

			}
		}
		
	}

}
