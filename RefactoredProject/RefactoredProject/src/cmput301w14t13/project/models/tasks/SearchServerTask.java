package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.elasticsearch.ElasticSearchOperations;

/**
* Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* A Subclass of Task specifically to search the server for a CommentTreeElement
* using its unique ID
*/

public class SearchServerTask extends Task {

	public SearchServerTask(DataStorageService esc, String searchTerm) {
		super(esc, searchTerm, null);
	}

	@Override
	public String doTask() throws InterruptedException {
		this.obj = ElasticSearchOperations.retrieveViewable(this, esc.getURL());
		if(this.obj == null)
		{
			return "failure";
		}
		else
		{
			return "success";
		}
	}

}
