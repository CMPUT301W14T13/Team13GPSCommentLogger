package cmput301w14t13.project.views.submissions;

import android.os.Bundle;
import android.view.View;
import cmput301w14t13.project.controllers.submissions.CreateTopicSubmissionController;

public class CreateTopicSubmissionView extends TopicSubmissionView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		controller = new CreateTopicSubmissionController(this);
		
		controller.initialize();
	}
}
