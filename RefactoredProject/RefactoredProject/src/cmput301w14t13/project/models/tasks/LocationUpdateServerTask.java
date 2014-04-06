package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.auxilliary.tools.Escaper;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.elasticsearch.ElasticSearchOperations;
import cmput301w14t13.project.services.serialization.CommentTreeElementServerSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* A Subclass of Task specifically to update a CommentTreeElements Location
* it finds the appropriate CommentTreeElement to update using a unique ID
* then updates the CommentTreeElements location field
*/

public class LocationUpdateServerTask extends Task {

	public LocationUpdateServerTask(DataStorageService esc, CommentTreeElement obj) {
		super(esc, obj.getID(), obj);
	}
	
	/**
	 * Creates a task with the specific purpose of updating a CommentTreeElements
	 * location, locally and/or on the server
	 * 
	 * Used when a user updates a Topic or Comment's location (e.g. adding or changing 
	 * its location) to then update the local and Server versions of the CommentTreeElement
	 * 
	 * @param esc The DataStorage service where the task is run
	 * @param obj The CommentTree Element to be updated
	 */
	@Override
	public String doTask() throws InterruptedException {
		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).create();
		
		//the object of the current serverTask is the viewable to be serialized
		String jsonString = new Escaper(false).escapeJsonString(gson.toJson(this.getObj().getGPSLocation()));
		String fieldName = "GPSLocation";

		//searchTerm should have the viewable's ID
		//we need to get the ESID to process an update request
		String esID = ElasticSearchOperations.findESIDByID(this, esc.getURL());
		
		//next, we update the viewable
		return ElasticSearchOperations.updateField(esID,this,fieldName,jsonString ,esc.getURL());
	}

}
