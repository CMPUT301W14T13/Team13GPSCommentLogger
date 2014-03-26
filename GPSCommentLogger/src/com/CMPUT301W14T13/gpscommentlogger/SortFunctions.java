package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;


public class SortFunctions
{

	
	public static ArrayList<Viewable> sortByNewest(ArrayList<Viewable> viewables){



		Date newest = viewables.get(0).getTimestamp();
		int position = 0;
		Date date;
		ArrayList<Viewable> orderedArray = new ArrayList<Viewable>();

		while (viewables.size() != 0){
			position = 0;
			newest = viewables.get(0).getTimestamp();
			
			for (int i = 0; i < viewables.size(); i++){

				date = viewables.get(i).getTimestamp();

				if (date.after(newest)){

					newest = date;
					position = i;
				}

			}

			orderedArray.add(viewables.get(position));
			viewables.remove(position);

		}



		return orderedArray;
	}
	
	public static ArrayList<Viewable> sortByOldest(ArrayList<Viewable> viewables){



		Date oldest = new Date();
		int position = 0;
		Date date;
		ArrayList<Viewable> orderedArray = new ArrayList<Viewable>();

		while (viewables.size() != 0){
			position = 0;
			oldest = new Date();
			
			for (int i = 0; i < viewables.size(); i++){

				date = viewables.get(i).getTimestamp();

				if (date.before(oldest)){

					oldest = date;
					position = i;
				}

			}

			orderedArray.add(viewables.get(position));
			viewables.remove(position);

		}



		return orderedArray;
	}
	/**
	 * This method takes in an array list of viewables (Topics or Comments) and a location, then sorts
	 * the array list by how close the Topic or comment is to the givenLocation. It returns an ordered
	 * array of Topics or Comments from closest to furthest from the givenLocation. 
	 * @param ArrayList<Viewables> viewables
	 * @param Location givenLocation
	 * @return ArrayList<Viewable> orderedArray 
	 */
	public static ArrayList<Viewable> sortByGivenLocation(ArrayList<Viewable> viewables, Location givenLocation){
		
		ArrayList<Viewable> orderedArray = new ArrayList<Viewable>();
		int position = 0;
		double closest = 99999999;
		double distance;
		Location location;
		
		while(viewables.size() != 0){
			for (int i = 0; i < viewables.size(); i++){
				location = viewables.get(i).getGPSLocation();	
				distance = givenLocation.distanceTo(location);
				if(distance < closest){
					closest = distance;
					position = i;
				}
			}
			orderedArray.add(viewables.get(position));
			viewables.remove(position);
		}
		return orderedArray;
	}

	
}
