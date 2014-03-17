package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;

/**
 * Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
 * This class's method reinitializes the server with a new root
 */

public class InitializationServerTask extends ServerTask {

	public InitializationServerTask(ServerDispatcher dispatcher) {
		super(dispatcher);
		this.obj = new Root();
	}

	@Override
	public Result executeOnServer(ServerContext context) throws InterruptedException {
		ServerResult out = new ServerResult();
		out.setId(this.id);
		
		//first we delete everything that already exists
		ServerOperations.deleteAll(out, context.getURL());
		
		//next, we add the new root
		ServerOperations.postNewViewable(this, out, context.getURL());	
		
		return out;
	}

}
