package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;

//Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
// This method adds a given Viewable to server

public class PostNewServerTask extends ServerTask {

	public PostNewServerTask(ServerDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public Result executeOnServer(ServerContext context) throws InterruptedException {
		ServerResult out = new ServerResult();
		out.setId(this.id);

		//searchTerm should have the parent's ID
		//we need to get the ESID to process an update request
		String esID = ServerOperations.findESIDByID(this, context.getURL());
		
		//next, we add the new object
		ServerOperations.postNewViewable(this, out, context.getURL());
		
		//finally, we update the parent object
		ServerOperations.addToList(esID, "comments", this, out, context.getURL());

		return out;
	}

}
