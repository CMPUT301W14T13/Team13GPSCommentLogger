package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;

/**
 * Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
 * This class's method reinitializes the server with a new root
 */

public class InitializationServerTask extends Task {

	public InitializationServerTask(ElasticSearchController esc) {
		super(esc, null,  new Root());
	}

	@Override
	public String doTask() throws InterruptedException {
		//first we delete everything that already exists
		ServerOperations.deleteAll(esc.getURL());
		
		//next, we add the new root
		return ServerOperations.postNewViewable(this, esc.getURL());	
	}

}
