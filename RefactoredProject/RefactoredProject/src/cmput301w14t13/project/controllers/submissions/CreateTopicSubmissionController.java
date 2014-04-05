package cmput301w14t13.project.controllers.submissions;

import android.util.Log;
import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;

public class CreateTopicSubmissionController extends TopicSubmissionController {

	public CreateTopicSubmissionController(TopicSubmissionView view) {
		super(view);
	}

	@Override
	protected void constructSubmissionData() {
		Log.w("Test constructor","constructSubmissionData reached");
		submission.setSubmission(new Topic());
	}

	@Override
	protected void initializeLayout() {
		view.setContentView(R.layout.create_topic); //creating a topic
		view.getActionBar().setDisplayHomeAsUpEnabled(true);
		EditText text = (EditText) view.findViewById(R.id.setTopicUsername);
		text.setText(CommentTree.getInstance().getCurrentUsername());
	}

	@Override
	protected void processSubmit() {
		CommentTree cl = CommentTree.getInstance();
		DataStorageService dss = DataStorageService.getInstance();
		TaskFactory factory = new TaskFactory(dss);
		cl.addElementToCurrent(submission.getSubmission());
		factory.requestPost(cl.getElement(view).getID(), submission.getSubmission());
	}

	@Override
	protected void extractTextFields() {
		//only get the title if it's a topic
		EditText text = (EditText) view.findViewById(R.id.setTitle);
		submission.getSubmission().setTitle(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.setTopicUsername);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.setTopicText);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
