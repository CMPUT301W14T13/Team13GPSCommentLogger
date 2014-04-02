package cmput301w14t13.project.auxilliary.tools;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;


public class SortFunctions
{


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
	 *
	 * @param viewables
	 * @return ArrayList<Viewable>
	 */
	public static ArrayList<CommentTreeElement> sortByCurrentLocation(ArrayList<CommentTreeElement> viewables) {
		Location location = LocationSelection.getLocation();
		//LocationSelection.startLocationSelection();
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


	public static ArrayList<CommentTreeElement> sortByMostRelevant(ArrayList<CommentTreeElement> viewables){

		viewables = sortByCurrentLocation(viewables);
		/*for (int i = 0; i < viewables.size(); i++){
System.out.println(viewables.get(i).getTimestamp() + " " + viewables.get(i).getGPSLocation().getLatitude() + " " + viewables.get(i).getGPSLocation().getLongitude());
}*/

		ArrayList<CommentTreeElement> mostRelevant = new ArrayList<CommentTreeElement>();
		ArrayList<CommentTreeElement> leastRelevant = new ArrayList<CommentTreeElement>();
		Location location;
		int position;
		Location currentLocation = LocationSelection.getLocation();
		LocationSelection.startLocationSelection();

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