package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;

/**
 * Tasks contain server execution commands
 * (eg. server object request)
 * that are passed to controllers.
 */

public abstract class Task
{
	protected Viewable obj;
	protected String searchTerm;
	
	protected ElasticSearchController esc;
	
	public Task(ElasticSearchController esc, String searchTerm, Viewable obj)
	{
		this.searchTerm = searchTerm;
		this.obj = obj;
		this.esc = esc;
	}
	
	public Viewable getObj() {
		return obj;
	}
	
	public String getSearchTerm() {
		return searchTerm;
	}
	
	public abstract String doTask() throws InterruptedException;

}
