package cmput301w14t13.project.auxilliary.tools;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;


import android.location.Location;


/**
 * This class contains all the sorting functions that are
 * used to sort both topics and comments within a topic.
 * 
 * These functions are called when a selection is made
 * in the dropdown menu containing sort options inside
 * home view and topic view.
 * 
 * All methods take an ArrayList of topics or comments,
 * carry out required task of sorting, and return the
 * same ArrayList type. 
 */
public class SortFunctions
{

	/**
	 * This function sorts topics or comments in order
	 * of newest to latest. Sorting is done by having 
	 * two lists: a list of unsorted CommentTreeElements,
	 * and an ordered list to be populated with the same elements.
	 * 
	 * The algorithm takes an item from the unsortetd list, compares
	 * it to the rest of the list, if its date is newer than the
	 * others then it's added to the sorted list, else it's dropped
	 * and the next element in the unsorted list is examined.
	 * 
	 * @param ArrayList<CommentTreeElement>
	 * @return ArrayList<CommentTreeElement>
	 */
	public static ArrayList<CommentTreeElement> sortByNewest(ArrayList<CommentTreeElement> viewables){



		Date newest;
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

	/**
	 * This function sorts topics or comments in order
	 * of newest to latest. Sorting is done by having 
	 * two lists: a list of unsorted CommentTreeElements,
	 * and an ordered list to be populated with the same elements.
	 * 
	 * The algorithm takes an item from the unsortetd list, compares
	 * it to the rest of the list, if its date is older than the
	 * others then it's added to the sorted list, else it's dropped
	 * and the next element in the unsorted list is examined.
	 * 
	 * @param ArrayList<CommentTreeElement>
	 * @return ArrayList<CommentTreeElement>
	 */
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
	 * closest to farthest from current location.
	 * @param viewables
	 * @return ArrayList<Viewable>
	 */
	public static ArrayList<CommentTreeElement> sortByCurrentLocation(ArrayList<CommentTreeElement> viewables) {

		Location location = LocationSelection.getInstance().getLocation();

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
		double closest;
		double distance;
		Location location;
		while(viewables.size() != 0){
			position = 0;
			closest = givenLocation.distanceTo(viewables.get(0).getGPSLocation());

			for (int i = 0; i < viewables.size(); i++){
				location = viewables.get(i).getGPSLocation();	
				distance = givenLocation.distanceTo(location);
				if(distance <= closest){

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

		viewables = sortByCurrentLocation(viewables); // get location sorted comments in function
		ArrayList<CommentTreeElement> pictures = new ArrayList<CommentTreeElement>();
		ArrayList<CommentTreeElement> noPictures = new ArrayList<CommentTreeElement>();

		while (viewables.size() != 0){



			for (int i = 0; i < viewables.size();){

				if (viewables.get(i).getHasImage()){

					pictures.add(viewables.remove(i));
				}
				else{
					noPictures.add(viewables.remove(i));
				}
				
				
			}

		}
		
		pictures.addAll(noPictures);
		return pictures;
	}
	
	/**
	 * Method sorts comments or topics by what is
	 * most relevant. Relevance is defined as comments
	 * or topics that are within 50 KM o the user, sorted
	 * by newest.
	 * 
	 * This method checks all elements in a given ArrayList of
	 * comments/topics and filters those that are within 50 KM
	 * of the user's retrieved location into mostRelevant, and 
	 * the others into leastRelevant.
	 * 
	 * The list mostRelevant is then sorted by newest using
	 * the function sortByNewest (documented in the class),
	 * then returned. 
	 * 
	 * @param ArrayList<CommentTreeElement>
	 * @return ArrayList<CommentTreeElement>
	 */
	public static ArrayList<CommentTreeElement> sortByMostRelevant(ArrayList<CommentTreeElement> viewables){
		
		viewables = sortByCurrentLocation(viewables);
		/*for (int i = 0; i < viewables.size(); i++){
			System.out.println(viewables.get(i).getTimestamp() + " " + viewables.get(i).getGPSLocation().getLatitude() + " " + viewables.get(i).getGPSLocation().getLongitude());
		}*/
		
		ArrayList<CommentTreeElement> mostRelevant = new ArrayList<CommentTreeElement>();
		ArrayList<CommentTreeElement> leastRelevant = new ArrayList<CommentTreeElement>();
		Location location; 
		int position;
		Location currentLocation = LocationSelection.getInstance().getLocation();
		
		while (viewables.size() != 0){
			
		

			for (int i = 0; i < viewables.size();){

				location = viewables.get(i).getGPSLocation();
				//System.out.println(currentLocation.distanceTo(location));
				if (currentLocation.distanceTo(location) <= 50000){

					mostRelevant.add(viewables.remove(i));
				}
				else{
					leastRelevant.add(viewables.remove(i));
				}

			}

		}
		
		//System.out.println(mostRelevant);
		mostRelevant = sortByNewest(mostRelevant);
		mostRelevant.addAll(leastRelevant);
		return mostRelevant;

	}

}
