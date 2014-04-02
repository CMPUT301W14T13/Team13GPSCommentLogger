package cmput301w14t13.project.auxilliary.tools;

import java.util.ArrayList;
import java.util.Date;

import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;
import android.location.Location;

public class SortFunctions
{

	
	public static ArrayList<CommentTreeElement> sortByNewest(ArrayList<CommentTreeElement> viewables){



		Date newest = viewables.get(0).getTimestamp();
		int position = 0;
		Date date;
		ArrayList<CommentTreeElement> orderedArray = new ArrayList<CommentTreeElement>();

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
	
	public static ArrayList<CommentTreeElement> sortByOldest(ArrayList<CommentTreeElement> viewables){



		Date oldest = new Date();
		int position = 0;
		Date date;
		ArrayList<CommentTreeElement> orderedArray = new ArrayList<CommentTreeElement>();

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
	public static ArrayList<CommentTreeElement> sortByCurrentLocation(ArrayList<CommentTreeElement> viewables) {
		Location location = LocationSelection.getLocation();
		return (sortByGivenLocation(viewables, location));
	}

	/**
	 * This method takes in an array list of viewables (Topics or Comments) and a location, then sorts
	 * the array list by how close the Topic or comment is to the givenLocation. It returns an ordered
	 * array of Topics or Comments from closest to furthest from the givenLocation. 
	 * @param ArrayList<Viewables> viewables
	 * @param Location givenLocation
	 * @return ArrayList<Viewable> orderedArray 
	 */
	public static ArrayList<CommentTreeElement> sortByGivenLocation(ArrayList<CommentTreeElement> viewables, Location givenLocation){
		
		ArrayList<CommentTreeElement> orderedArray = new ArrayList<CommentTreeElement>();
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
	public static ArrayList<CommentTreeElement> sortByPicture(ArrayList<CommentTreeElement> viewables) {
		ArrayList<CommentTreeElement> pictureSortedList = sortByCurrentLocation(viewables); // get location sorted comments in function  
		for (int i = 0; i < pictureSortedList.size(); i++) {
			if (pictureSortedList.get(i).getHasImage() == false) {
				pictureSortedList.remove(i);
			}
		}
		
		return pictureSortedList;
		}
	}
