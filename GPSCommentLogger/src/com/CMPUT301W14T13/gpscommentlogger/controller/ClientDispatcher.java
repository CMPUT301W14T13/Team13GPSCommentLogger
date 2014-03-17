package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;


public class ClientDispatcher
{

	private ServerListener client;

	public ClientDispatcher(ServerListener client)
	{
		this.client = client;
	}
	
	public void dispatch(ServerResult result)
	{
		client.addResult(result);
	}

}
