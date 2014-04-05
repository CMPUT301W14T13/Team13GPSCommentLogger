package cmput301w14t13.project.views.submissions;

import cmput301w14t13.project.controllers.submissions.EditTopicSubmissionController;
import android.os.Bundle;

public class EditTopicSubmissionView extends TopicSubmissionView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		controller = new EditTopicSubmissionController(this);

	}
}
