package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;

import com.CMPUT301W14T13.gpscommentlogger.model.LocationSelection;
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
	 * This function retrieves current location from 
	 * the LocationSelection class, and calls sortByGivenLocation,
	 * passing to it the user's current location and a list of
	 * comments.
	 * 
	 * The function returns a list of ordered comments from
	 * closes to farthest from current location.
	 * 
	 * @param viewables
	 * @return ArrayList<Viewable>
	 */
	public static ArrayList<Viewable> sortByCurrentLocation(ArrayList<Viewable> viewables) {
		Location location = LocationSelection.getLocation();
		return (sortByGivenLocation(location));
	}
	
	/**
	 * This function returns a list of Comment objects, sorted
	 * by current location and image content.
	 * 
	 * The function will call another one that sorts comments by
	 * current location (sortByCurrentLocation) and check the
	 * returned sorted list of comments and removes those ones that
	 * don't contain pictures.
	 * 
	 * This function returns a list of comments closest to the user
	 * that contain images.
	 * 
	 * @param locationSortedCommentes
	 * @return ArrayList<Viewable>
	 */
	public static ArrayList<Viewable> sortByPicture(ArrayList<Viewable> viewables) {
		ArrayList<Viewable> pictureSortedList = sortByCurrentLocation(viewables); // get location sorted comments in function  
		for (int i = 0; i < pictureSortedList.size(); i++) {
			if (pictureSortedList.get(i).getHasImage() == false) {
				pictureSortedList.remove(i);
			}
		}
		
		return pictureSortedList;
		}
	}
