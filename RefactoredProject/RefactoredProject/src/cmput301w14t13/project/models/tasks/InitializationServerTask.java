package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.elasticsearch.ElasticSearchOperations;

/**
 * Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
 * This class's method reinitializes the server with a new root
 */

public class InitializationServerTask extends Task {

	public InitializationServerTask(DataStorageService esc) {
		super(esc, null,  new Root());
	}

	@Override
	public String doTask() throws InterruptedException {
		//first we delete everything that already exists
		ElasticSearchOperations.deleteAll(esc.getURL());
		
		//next, we add the new root
		return ElasticSearchOperations.postNewViewable(this, esc.getURL());	
	}

}
