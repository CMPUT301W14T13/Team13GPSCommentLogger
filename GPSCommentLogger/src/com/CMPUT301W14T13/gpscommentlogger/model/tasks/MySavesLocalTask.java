package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;

public class MySavesLocalTask extends LocalTask {

	public MySavesLocalTask(DataManager manager) {
		super(manager);
	}

	@Override
	public void doLocalTask() {
		this.manager.getData(this.searchTerm);
	}

}
