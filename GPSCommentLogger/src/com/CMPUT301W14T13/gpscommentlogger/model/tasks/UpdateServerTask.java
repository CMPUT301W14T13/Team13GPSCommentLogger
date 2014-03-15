package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;

public abstract class UpdateServerTask extends ServerTask {

	public UpdateServerTask(ServerDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public Result executeOnServer(ServerContext context) {
		return executeUpdateOnServer(context);
	}
	
	public abstract Result executeUpdateOnServer(ServerContext context);

}
