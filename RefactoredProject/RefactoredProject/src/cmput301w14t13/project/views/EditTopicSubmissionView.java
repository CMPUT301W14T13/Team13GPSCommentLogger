package cmput301w14t13.project.views;

import android.os.Bundle;
import cmput301w14t13.project.controllers.CreateSubmissionController;

public class EditTopicSubmissionView extends TopicSubmissionView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* create the location stuff up here */
		int rowNumber = getIntent().getIntExtra("row number", -1);
		
		controller = new EditTopicSubmissionController(this);
		
		controller.initializeLocation();
		controller.initializeFields();
	}
}
