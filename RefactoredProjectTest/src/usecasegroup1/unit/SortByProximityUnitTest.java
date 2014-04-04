package usecasegroup1.unit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class SortByProximityUnitTest extends ActivityInstrumentationTestCase2<HomeView> {


	HomeView activity;
	
	public SortByProximityUnitTest() {
		super(HomeView.class);
	}


	public void testSortByLocation() throws InterruptedException{

		
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

			topic.setGPSLocation(location);
			topics.add(topic);
			latitude += 0.1;
		}

		

	
		//sort by current location
		topics = SortFunctions.sortByCurrentLocation(topics);
		
		for (int j = 0; j < topics.size(); j++){

			if (j != topics.size() - 1){
				double distance = originalLocation.distanceTo(topics.get(j).getGPSLocation());
				assertTrue("Comments should be closer than one after it", distance <= originalLocation.distanceTo(topics.get(j+1).getGPSLocation()));

			}

		}

		
		//sort by given location
		Location givenLocation = new Location("default");
		givenLocation.setLatitude(39);
		givenLocation.setLongitude(104);
		topics = SortFunctions.sortByGivenLocation(topics, givenLocation);
		
		for (int j = 0; j < topics.size(); j++){

			if (j != topics.size() - 1){
				double distance = givenLocation.distanceTo(topics.get(j).getGPSLocation());
				assertTrue("Comments should be closer than one after it", distance <= givenLocation.distanceTo(topics.get(j+1).getGPSLocation()));

			}

		}
		
	}



}
