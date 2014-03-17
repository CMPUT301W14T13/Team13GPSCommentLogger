package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTask;


public class ServerDispatcher
{
	private ClientListener server;

	public ServerDispatcher(ClientListener server)
	{
		this.server = server;
	}
	
	public void dispatch(ServerTask task) throws InterruptedException
	{
		server.addTask(task);
	}

}
