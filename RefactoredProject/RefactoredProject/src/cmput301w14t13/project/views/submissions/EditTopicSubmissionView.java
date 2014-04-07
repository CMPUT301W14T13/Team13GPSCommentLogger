package cmput301w14t13.project.views.submissions;

import android.os.Bundle;
import cmput301w14t13.project.R;
import cmput301w14t13.project.controllers.submissions.EditTopicSubmissionController;

public class EditTopicSubmissionView extends TopicSubmissionView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		controller = new EditTopicSubmissionController(this);

		controller.initialize();
	}
}
