package cmput301w14t13.project.models.tasks;

import android.util.Log;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.CommentTreeElementServerSerializer;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.ElasticSearchOperations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
 * This class's method searches for the ID and updates
 * the version in the elasticSearch versioning system 
 * to the new state provided by the client
 */

public class TextUpdateServerTask extends Task {

	public TextUpdateServerTask(DataStorageService esc, CommentTreeElement obj) {
		super(esc, obj.getID(), obj);
	}

	@Override
	public String doTask() throws InterruptedException {
		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).create();
		
		//the object of the current serverTask is the viewable to be serialized
		String jsonString = gson.toJson(this.getObj().getCommentText());
		String fieldName = "commentText";
		Log.w("UpdateTest", "Test");

		//searchTerm should have the viewable's ID
		//we need to get the ESID to process an update request
		String esID = ElasticSearchOperations.findESIDByID(this, esc.getURL());
		
		//next, we update the viewable
		Log.w("UpdateTest", esID);
		return ElasticSearchOperations.updateField(esID,this, fieldName,jsonString , esc.getURL());
	}

}
