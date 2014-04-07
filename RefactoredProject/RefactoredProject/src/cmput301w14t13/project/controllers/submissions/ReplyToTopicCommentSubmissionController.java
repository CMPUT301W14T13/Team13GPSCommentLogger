package cmput301w14t13.project.controllers.submissions;

import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;
/**
 * Extends Comment SubmissionController, responsible for handling
 * creating of Comments in reply to Topics
 * initializes the proper fields for user input, extracts
 * user inputed information from the text fields and processes
 * submission to be sent to the server, delegates checking
 * if the submission is valid to the CommentSubmissionController
 * 
 * @author nsd
 *
 */
public class ReplyToTopicCommentSubmissionController extends
		CommentSubmissionController {

	public ReplyToTopicCommentSubmissionController(ReplyToTopicCommentSubmissionView view) {
		super(view);
	}

	@Override
	protected void constructSubmissionData() {
		submission.setSubmission(new Comment());
	}
	/**
	 * sets the correct layout for the user to input 
	 * information and sets the username field
	 * with whatever the currently selected username is
	 */
	@Override
	protected void initializeLayout() {
		view.setContentView(R.layout.create_comment); //creating a comment
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		text.setText(CommentTree.getInstance().getCurrentUsername());
	}
	/**
	 * adds the Comment to the current Topic and also 
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
	 * the Comment, things like Title, text, and username
	 * 
	 * Used when the user clicks the submit button 
	 */
	@Override
	protected void extractTextFields() {
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.set_comment_text);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
