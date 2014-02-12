package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.model.ServerTask;


public class ClientListener implements Runnable
{

	private ServerController server;
	private ServerTask task;

	public ClientListener(ServerController serverController)
	{
		server = serverController;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try{
				checkTask();
				passTask();
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public synchronized void addTask(ServerTask task)
	{
		this.task= task;
		notify();
	}
	
	private synchronized void checkTask() throws InterruptedException
	{
		if (task == null) wait();
	}
	
	private void passTask(){
		server.addTask(task);
		task = null;
	}

}
