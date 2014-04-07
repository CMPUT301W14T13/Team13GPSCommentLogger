package cmput301w14t13.project.controllers.submissions;

import android.util.Log;
import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;
/**
 * Extends TopicSubmissionController
 * Responsible for setting the layout for the user, extracting the proper
 * information from the text fields and processing the submission to send
 * to the server, delegates checking the submission to TopicSubmissionController
 * 
 * @author nsd
 *
 */
public class CreateTopicSubmissionController extends TopicSubmissionController {
	/**
	 * initializes the CreateTopicSubmissionController with a view
	 * the user uses to input information for topic creation 
	 * @param view CreateTopicSubmission view where the user
	 *  inputs information for the topic
	 */
	public CreateTopicSubmissionController(TopicSubmissionView view) {
		super(view);
	}
	/**
	 * Creates CommentTreeElementSubmission which is used for 
	 * the user to create a topic
	 */
	@Override
	protected void constructSubmissionData() {
		Log.w("Test constructor","constructSubmissionData reached");
		submission.setSubmission(new Topic());
	}
	/**
	 * initializes the Correct layout for the 
	 * user to input their information as well 
	 * as setting the username field with whatever
	 * global usernam is set as the current username
	 */
	@Override
	protected void initializeLayout() {
		view.setContentView(R.layout.create_topic); //creating a topic
		view.getActionBar().setDisplayHomeAsUpEnabled(true);
		EditText text = (EditText) view.findViewById(R.id.setTopicUsername);
		text.setText(CommentTree.getInstance().getCurrentUsername());
	}
	/**
	 * adds the topic to the CommentTree and also 
	 * submits a post request to the server
	 * 
	 * Used when the user has clicked submit and
	 * the program has made sure all required fields
	 * were filled
	 */
	@Override
	protected void processSubmit() {
		CommentTree cl = CommentTree.getInstance();
		DataStorageService dss = DataStorageService.getInstance();
		TaskFactory factory = new TaskFactory(dss);
		cl.addElementToCurrent(submission.getSubmission());
		factory.requestPost(cl.getElement(view).getID(), submission.getSubmission());
	}
	
	/**
	 * Extracts the information from the view to construct 
	 * the Topic, things like Title, text, and username
	 * 
	 * Used when the user clicks the submit button 
	 */
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
