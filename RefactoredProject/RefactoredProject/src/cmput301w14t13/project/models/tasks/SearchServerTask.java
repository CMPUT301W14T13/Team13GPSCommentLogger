package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.ElasticSearchOperations;

/**
* Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* This class's method searches for a Viewable based on its ID field
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
