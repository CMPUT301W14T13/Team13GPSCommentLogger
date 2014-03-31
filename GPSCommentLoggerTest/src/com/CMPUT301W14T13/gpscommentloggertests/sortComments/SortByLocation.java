package com.CMPUT301W14T13.gpscommentloggertests.sortComments;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.SortFunctions;
import com.CMPUT301W14T13.gpscommentlogger.model.LocationSelection;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeViewActivity;

@SuppressLint("NewApi")
public class SortByLocation extends ActivityInstrumentationTestCase2<HomeViewActivity> {

	HomeViewActivity activity;
	
	public SortByLocation() {
		super(HomeViewActivity.class);
	}

	@Before
	public void setUp(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();
		
	}

	public void testSortByLocation() throws InterruptedException{


		ArrayList<Viewable> topics = new ArrayList<Viewable>();
		Topic topic = new Topic();
		Location originalLocation = LocationSelection.getLocation();
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

		topics = SortFunctions.sortByCurrentLocation(topics);

		for (int i = 0; i < topics.size(); i++){
			System.out.println(topics.get(i).getTimestamp() + " " + topics.get(i).getGPSLocation().getLatitude() + " " + topics.get(i).getGPSLocation().getLongitude());
		}



		//Every topic should be more closer than the ones after it

		for (int j = 0; j < topics.size(); j++){

			if (j != topics.size() - 1){
				double distance = originalLocation.distanceTo(topics.get(j).getGPSLocation());
				assertTrue("Comments should be closer than one after it", distance <= originalLocation.distanceTo(topics.get(j+1).getGPSLocation()));

			}

		}

		
		Location givenLocation = new Location("default");
		topics = sortByGivenLocation(topics, givenLocation);
		for (int j = 0; j < topics.size(); j++){

			if (j != topics.size() - 1){
				double distance = givenLocation.distanceTo(topics.get(j).getGPSLocation());
				assertTrue("Comments should be closer than one after it", distance <= givenLocation.distanceTo(topics.get(j+1).getGPSLocation()));

			}

		}
		
	}

	public void testSortByGivenLocation() throws InterruptedException{


		ArrayList<Viewable> topics = new ArrayList<Viewable>();
		Topic topic = new Topic();
		Location givenLocation = new Location("default");
		Location location = new Location(givenLocation);

		double latitude = 5;
		double longitude = 57;


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

		topics = SortFunctions.sortByGivenLocation(topics, givenLocation);

		for (int i = 0; i < topics.size(); i++){
			System.out.println(topics.get(i).getTimestamp() + " " + topics.get(i).getGPSLocation().getLatitude() + " " + topics.get(i).getGPSLocation().getLongitude());
		}



		//Every topic should be more closer than the ones after it

		for (int j = 0; j < topics.size(); j++){

			if (j != topics.size() - 1){
				double distance = givenLocation.distanceTo(topics.get(j).getGPSLocation());
				assertTrue("Comments should be closer than one after it", distance <= givenLocation.distanceTo(topics.get(j+1).getGPSLocation()));

			}

		}

		
	}


}
