package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.ClientResult;

public class ServerListener extends Thread
{
	private ClientController client;
	private ClientResult result;

	public ServerListener(ClientController clientController)
	{
		client = clientController;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try{
				checkResult();
				passResult();
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public synchronized void addResult(ClientResult result)
	{
		this.result = result;
		notify();
	}
	
	private synchronized void checkResult() throws InterruptedException
	{
		if (result == null) wait();
	}
	
	private void passResult(){
		client.registerResult(result);
		result = null;
	}

}
