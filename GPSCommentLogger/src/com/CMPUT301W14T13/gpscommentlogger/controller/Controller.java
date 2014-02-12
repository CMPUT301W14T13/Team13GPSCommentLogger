package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.Task;


public abstract class Controller extends Thread
{
	//store if controller has been initialized
	protected boolean isInit;

	public abstract void init();

	protected abstract void checkTasks() throws InterruptedException;
	
	public abstract void addTask(Task task);
	
	protected abstract Result doTask() throws InterruptedException;
	protected abstract void processResult(Result result);

}
