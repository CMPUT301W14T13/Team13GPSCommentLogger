package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;

public abstract class ServerTask extends Task {

	protected ServerDispatcher dispatcher;
	
	public ServerTask(ServerDispatcher dispatcher) {
		super();
		this.dispatcher = dispatcher;
	}

	@Override
	protected void doTask() throws InterruptedException {
		this.dispatcher.dispatch(this);
	}
	
	public abstract Result executeOnServer(ServerContext context) throws InterruptedException;

}

