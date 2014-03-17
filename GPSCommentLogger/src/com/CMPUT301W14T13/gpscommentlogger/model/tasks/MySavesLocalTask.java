package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;

/**
 * Task for testing local saved
 * topic listings.
 *
 */
public class MySavesLocalTask extends LocalTask {

	public MySavesLocalTask(DataManager manager) {
		super(manager);
	}

	@Override
	public void doLocalTask() {
		this.manager.getData(this.searchTerm);
	}

}
