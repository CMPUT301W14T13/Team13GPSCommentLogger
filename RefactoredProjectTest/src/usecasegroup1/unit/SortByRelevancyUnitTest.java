package usecasegroup1.unit;

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
public class SortByRelevancyUnitTest extends ActivityInstrumentationTestCase2<HomeView> {

	public SortByRelevancyUnitTest() {
		super(HomeView.class);
	}

	
	
	public void testSortByMostRelevant() throws InterruptedException{
		
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

		

		double range = 50000;
		boolean pastRange = false;
		
		//Every topic should be more relevant (newer and closer) than the ones after it
		for (int i = 0; i < topics.size(); i++){

			location = topics.get(i).getGPSLocation();
			
			if (originalLocation.distanceTo(location) > range){
				pastRange = true;
			}
			
			if (i != topics.size() - 1 && !pastRange){
				if(originalLocation.distanceTo(topics.get(i+1).getGPSLocation()) <= range){
					assertTrue("Comments should be newer", topics.get(i).getTimestamp().after(topics.get(i+1).getTimestamp()));
				}
			}

			else if (pastRange){
				double distance = originalLocation.distanceTo(location);
				assertFalse("There should be no more topics found within range", distance <= range);
			} 

		}
		
	}

}
