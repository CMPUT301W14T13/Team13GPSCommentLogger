package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;

/**
 * Task for testing local favorites
 * topic listings.
 *
 */
public class MyFavouritesLocalTask extends LocalTask {

	public MyFavouritesLocalTask(DataManager manager) {
		super(manager);
	}

	@Override
	public void doLocalTask() {
		this.manager.getFavourite(this.searchTerm);
	}

}
