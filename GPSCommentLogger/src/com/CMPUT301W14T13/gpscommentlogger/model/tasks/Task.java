package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.model.TransferBundle;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;

/**
 * Tasks contain server execution commands
 * (eg. server object request)
 * that are passed to controllers.
 */

public abstract class Task implements TransferBundle
{
	protected Viewable obj;
	protected String searchTerm;
	private boolean active = true;
	
	protected String id;
	private boolean isIDSet = false;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) throws InterruptedException {
		if (isIDSet)throw new InterruptedException("Error: attempt to alter Task id");
		this.id = id;
	}
}
