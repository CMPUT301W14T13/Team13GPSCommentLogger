package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.ElasticSearchOperations;

/**
* Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* A Subclass of Task specifically to Post a new CommentTreeElement
* to the server
*/

public class PostNewServerTask extends Task {

	public PostNewServerTask(DataStorageService esc, String parentID, CommentTreeElement obj) {
		super(esc, parentID, obj);
	}
	/**
	 * We add the new CommentTreeElement to the server 
	 * then update its parent with the new object, for
	 * Topics the parent is Root, for Comments the parent
	 * is Topic, the User cannot post a Root 
	 */
	@Override
	public String doTask() throws InterruptedException {
		//searchTerm should have the parent's ID
		//we need to get the ESID to process an update request
		String esID = ElasticSearchOperations.findESIDByID(this, esc.getURL());
		
		//next, we add the new object
		ElasticSearchOperations.postNewViewable(this, esc.getURL());
		
		//finally, we update the parent object
		return ElasticSearchOperations.addToList(esID, "childPosts", this, esc.getURL());
	}

}
