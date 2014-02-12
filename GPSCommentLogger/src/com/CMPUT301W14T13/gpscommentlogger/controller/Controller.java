package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.Task;


public abstract class Controller implements Runnable
{
	//store if controller has been initialized
	protected boolean isInit;

	public abstract void init();

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

	protected abstract void checkTasks() throws InterruptedException;
	
	public abstract void addTask(Task task);
	
	protected abstract Result doTask() throws InterruptedException;
	protected abstract void processResult(Result result);

}
