package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;

public abstract class MockTask extends Task {

	protected DataEntityMockup mock;
	
	public MockTask(DataEntityMockup mock) {
		super();
		this.mock = mock;
	}

	@Override
	protected void doTask() {
		mock.pageRequest(this.searchTerm);
	}

}
