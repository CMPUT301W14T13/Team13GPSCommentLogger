package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;

public class PostMockTask extends MockTask {

	public PostMockTask(DataEntityMockup mock) {
		super(mock);
	}

	@Override
	protected void doMockTask() {
		this.mock.postRequest(this.searchTerm,(Comment)this.obj);
	}

}
