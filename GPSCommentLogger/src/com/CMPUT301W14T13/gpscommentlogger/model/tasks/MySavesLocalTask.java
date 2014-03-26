package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;

/**
 * Task for testing local saved
 * topic listings.
 *
 */
public class MySavesLocalTask extends Task {

	public MySavesLocalTask(ElasticSearchController esc, String searchTerm) {
		super(esc, searchTerm, null);
	}

	@Override
	public String doTask() {
		this.obj = this.esc.getDataManager().getData(this.searchTerm);
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
