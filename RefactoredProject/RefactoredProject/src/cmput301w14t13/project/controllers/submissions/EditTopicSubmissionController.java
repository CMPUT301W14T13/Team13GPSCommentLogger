package cmput301w14t13.project.controllers.submissions;

import android.util.Log;
import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;
/**
 * Extends TopicSubmissionController, is responsible for initializing the 
 * proper layout constructing a CommentTreeElementSubmission, extracting 
 * user inputted information, and sending a valid editted topic to the server
 * it delegates the checking of the submission to TopicSubmissionController 
 * 
 * @author nsd
 *
 */
public class EditTopicSubmissionController extends TopicSubmissionController {

	public EditTopicSubmissionController(TopicSubmissionView view) {
		super(view);
	}

	@Override
	protected void constructSubmissionData() {
		submission.setSubmission(CommentTree.getInstance().getElement(view));
	}
	/**
	 * sets up the proper layout for the user to input 
	 * information then fills up the fields with the current
	 * information that will be edited 
	 */
	@Override
	protected void initializeLayout() {
		view.setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)
		
		CommentTree cl = CommentTree.getInstance();
		submission = new CommentTreeElementSubmission(cl.getElement(view), view);
		/*
		 * Set various text fields below from the topic so that they are displayed when editing it
		 */
		EditText text = (EditText) view.findViewById(R.id.set_comment_text);
		text.setText(submission.getSubmission().getCommentText());

		text = (EditText) view.findViewById(R.id.set_comment_username);
		text.setText(submission.getSubmission().getUsername());
		extractTextFields();
	}
	/**
	 * Sets the Topic in the CommentTree with the editing information
	 * and then sends update request to the server to update the server copy
	 * of the Topic
	 * called after the checkSubmission method has made sure the Topic is valid
	 */
	@Override
	protected void processSubmit() {
		CommentTree cl = CommentTree.getInstance();
		DataStorageService dss = DataStorageService.getInstance();
		TaskFactory factory = new TaskFactory(dss);
		
		//TODO: no username update support at the moment
		cl.getElement(view).setUsername(submission.getSubmission().getUsername());
		cl.getElement(view).setCommentText(submission.getSubmission().getCommentText());
		cl.getElement(view).setLocation(submission.getSubmission().getGPSLocation());
		cl.getElement(view).setImage(submission.getSubmission().getImage());
		Log.w("UpdateTest", cl.getElement(view).getID());
		
		//TODO: it wasn't anticipated that more than one field would be set at a time
		factory.requestImageUpdate(submission.getSubmission());
		factory.requestLocationUpdate(submission.getSubmission());
		factory.requestTextUpdate(submission.getSubmission());
	}
	/**
	 * extracts user inputed information from the text fields
	 * and updates the Topic with said information
	 * called when the user clicks submit
	 */
	@Override
	protected void extractTextFields() {
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.set_comment_text);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
