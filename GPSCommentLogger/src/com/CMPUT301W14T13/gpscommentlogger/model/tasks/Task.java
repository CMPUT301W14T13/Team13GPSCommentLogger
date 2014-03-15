package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.model.TransferBundle;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;


public abstract class Task implements TransferBundle
{
	protected Viewable obj;
	protected String searchTerm;
	private boolean active;
	
	public Task()
	{

	}
	

	public Viewable getObj() {
		return obj;
	}

	public void setObj(Viewable obj) {
		this.obj = obj;
	}

	public void setSearchTerm(String searchTerm){
		this.searchTerm = searchTerm;
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}
	
	public void execute() throws InterruptedException{
		if(!active) throw new InterruptedException("Error: attempt to reexecute task.");
		active = false;
		doTask();
	}
	
	protected abstract void doTask() throws InterruptedException;
}
