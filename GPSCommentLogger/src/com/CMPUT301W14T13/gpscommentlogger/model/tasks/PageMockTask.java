package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;

public class PageMockTask extends MockTask {

	public PageMockTask(DataEntityMockup mock) {
		super(mock);
	}

	@Override
	protected void doMockTask() {
		mock.pageRequest(this.searchTerm);
	}

}
