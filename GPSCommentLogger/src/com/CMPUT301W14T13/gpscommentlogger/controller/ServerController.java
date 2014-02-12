package com.CMPUT301W14T13.gpscommentlogger.controller;


import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.model.ServerResult;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.Task;


public class ServerController extends Controller
{
	//store references to auxiliary classes
	private ClientDispatcher dispatcher;
	private ClientListener listener;
	
	//store reference to client
	private ClientController client;
	
	//store list of tasks
	private ArrayList<Task> tasks;
	

	public ServerController()
	{
		isInit = false;
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public void init()
	{
		if (isInit) return;
		
		dispatcher = new ClientDispatcher(client);
		listener = new ClientListener(this);
		listener.run();
		isInit = true;
	}

	@Override
	public void run()
	{
		while(true){
			try{
				//First check if there is something to do	
				checkTasks();
				//Do the oldest task in queue
				Result result = doTask();
				//Handle edge cases and debugging info here
				processResult(result);
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	protected synchronized void checkTasks() throws InterruptedException
	{
		if(tasks.isEmpty()) wait();
	}
	
	public synchronized void addTask(Task task)
	{
		tasks.add(task);
		notify();
	}
	
	protected Result doTask()
	{
		ServerTask currentTask = (ServerTask)tasks.remove(0);
		Result out = new ServerResult();
		
		//TODO: code in tasks here
		
		return out;
	}
	
	protected void processResult(Result result)
	{
		//TODO: handle results here
	}

	public void setClient(ClientController client)
	{
		this.client = client;
	}
	
}
