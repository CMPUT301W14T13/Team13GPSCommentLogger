package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.ServerTask;


public class ServerDispatcher
{
	private ServerController server;

	public ServerDispatcher(ServerController server)
	{
		this.server = server;
	}
	
	public void dispatch(ServerTask task) throws InterruptedException
	{
		server.addTask(task);
	}

}
