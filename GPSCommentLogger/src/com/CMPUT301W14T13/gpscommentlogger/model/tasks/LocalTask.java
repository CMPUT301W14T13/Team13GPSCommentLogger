package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;

public abstract class LocalTask extends Task {

	protected DataManager manager;
	
	public LocalTask(DataManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	protected void doTask() {
		doLocalTask();
	}
	
	public abstract void doLocalTask();

}
