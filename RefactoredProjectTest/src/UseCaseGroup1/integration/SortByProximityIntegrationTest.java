package UseCaseGroup1.integration;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class SortByProximityIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {


	HomeView activity;
	
	public SortByProximityIntegrationTest() {
		super(HomeView.class);
	}

	
	public void setUp(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();
		
	}

	public void testSortByCurrentLocation(){

		
		ArrayList<CommentTreeElement> topics = CommentTree.getInstance().getChildren(activity);
		Location originalLocation = LocationSelection.getInstance().getLocation();

		topics = SortFunctions.sortByCurrentLocation(topics);
		
		for (int j = 0; j < topics.size(); j++){

			if (j != topics.size() - 1){
				double distance = originalLocation.distanceTo(topics.get(j).getGPSLocation());
				assertTrue("Comments should be closer than one after it", distance <= originalLocation.distanceTo(topics.get(j+1).getGPSLocation()));

			}

		}

	}


	public void sortByGivenLocation(){
		
		//TODO Have this test use the map UI
		
		ArrayList<CommentTreeElement> topics = CommentTree.getInstance().getChildren(activity);	
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
