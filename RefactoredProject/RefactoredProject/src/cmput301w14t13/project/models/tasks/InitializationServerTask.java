package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.ElasticSearchOperations;

/**
* Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* A Subclass of Task specifically to initialize the server
* it clears the server of what was previously on it then 
* adds a new root to the server
* 
 */

public class InitializationServerTask extends Task {

	public InitializationServerTask(DataStorageService esc) {
		super(esc, null,  new Root());
	}
	/**
	 * Deletes everything in the current server directory 
	 * then adds a new root to the server
	 * Used whenever the server needs to be started or restarted
	 */
	@Override
	public String doTask() throws InterruptedException {
		//first we delete everything that already exists
		ElasticSearchOperations.deleteAll(esc.getURL());
		
		//next, we add the new root
		return ElasticSearchOperations.postNewViewable(this, esc.getURL());	
	}

}
