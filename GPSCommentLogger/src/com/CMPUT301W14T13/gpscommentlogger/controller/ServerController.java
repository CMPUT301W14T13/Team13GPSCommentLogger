package com.CMPUT301W14T13.gpscommentlogger.controller;


import java.util.ArrayList;

import android.widget.TextView;

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
	
	//store reference to output window for debugging
	protected TextView debuggingWindow;

	public ServerController(TextView debuggingWindow)
	{
		isInit = false;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public void init()
	{
		if (isInit) return;
		
		dispatcher = new ClientDispatcher(client);
		listener = new ClientListener(this);
		listener.start();
		isInit = true;
	}

	@Override
	public void run()
	{
		writeDebuggingMessage("Server Running");
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
		writeDebuggingMessage("Task added to Server");
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
	
	private void writeDebuggingMessage(String message)
	{
		if (debuggingWindow == null) return;
		
		debuggingWindow.setText(message);
	}
	

}
