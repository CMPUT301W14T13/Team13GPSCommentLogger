package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;

/**
* Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* This class's method searches for a Viewable based on its ID field
*/

public class SearchServerTask extends Task {

	public SearchServerTask(ElasticSearchController esc, String searchTerm) {
		super(esc, searchTerm, null);
	}

	@Override
	public String doTask() throws InterruptedException {
		this.obj = ServerOperations.retrieveViewable(this, esc.getURL());
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
