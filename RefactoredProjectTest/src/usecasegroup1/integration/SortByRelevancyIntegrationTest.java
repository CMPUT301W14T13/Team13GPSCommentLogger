package usecasegroup1.integration;
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
public class SortByRelevancyIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {

	public SortByRelevancyIntegrationTest() {
		super(HomeView.class);
	}



	public void testSortByMostRelevant(){

		HomeView activity;
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();

		ArrayList<CommentTreeElement> topics = CommentTree.getInstance().getChildren(activity);
		Location originalLocation = LocationSelection.getInstance().getLocation();
		Location location;
		double range = 50000;
		boolean pastRange = false;
		
		topics = SortFunctions.sortByMostRelevant(topics);
		
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
