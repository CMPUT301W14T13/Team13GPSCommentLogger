package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;


public class ClientDispatcher
{

	private ServerListener client;

	public ClientDispatcher(ServerListener client)
	{
		this.client = client;
	}
	
	public void dispatch(ClientTask task)
	{
		client.addTask(task);
	}

}
