package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;

/**
* Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* This class's method adds a given Viewable to server
*/

public class PostNewServerTask extends Task {

	public PostNewServerTask(ElasticSearchController esc, String parentID, Viewable obj) {
		super(esc, parentID, obj);
	}

	@Override
	public String doTask() throws InterruptedException {
		//searchTerm should have the parent's ID
		//we need to get the ESID to process an update request
		String esID = ServerOperations.findESIDByID(this, esc.getURL());
		
		//next, we add the new object
		ServerOperations.postNewViewable(this, esc.getURL());
		
		//finally, we update the parent object
		return ServerOperations.addToList(esID, "childPosts", this, esc.getURL());
	}

}
