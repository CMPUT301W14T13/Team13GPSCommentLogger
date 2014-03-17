package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;

//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
//This method searches for a Viewable based on its ID field

public class SearchServerTask extends ServerTask {

	public SearchServerTask(ServerDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public Result executeOnServer(ServerContext context) throws InterruptedException {
		ServerResult out = new ServerResult();
		out.setId(this.id);
			
		ServerOperations.retrieveViewable(this, out, context.getURL());

		return out;
	}

}
