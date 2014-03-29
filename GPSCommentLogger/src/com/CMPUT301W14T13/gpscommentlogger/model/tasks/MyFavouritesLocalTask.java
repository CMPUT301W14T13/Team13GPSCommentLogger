package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;

/**
 * Task for testing local favorites
 * topic listings.
 *
 */
public class MyFavouritesLocalTask extends Task {

	public MyFavouritesLocalTask(ElasticSearchController esc, String searchTerm) {
		super(esc, searchTerm, null);
	}

	@Override
	public String doTask() {
		this.obj = this.esc.getDataManager().getFavourite(this.searchTerm);
		if(this.obj == null)
		{
			return "failure";
		}
		else
		{
			return "success";
		}
	}

}
