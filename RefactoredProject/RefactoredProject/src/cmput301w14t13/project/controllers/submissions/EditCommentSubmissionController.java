package cmput301w14t13.project.controllers.submissions;

import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.EditCommentSubmissionView;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;
/**
 * Extends CommentSubmissionController, initializes the proper 
 * layout for the user to input their information, extracts the 
 * text information from the fields and processes the submission
 * to send it to the server, delegates checking if submission is 
 * valid to CommentSubmissionController
 * 
 * @author nsd
 *
 */
public class EditCommentSubmissionController extends
		CommentSubmissionController {

	private int rowNumber;

	public EditCommentSubmissionController(EditCommentSubmissionView view, int rowNumber) {
		super(view);
		this.rowNumber = rowNumber;
	}

	@Override
	protected void constructSubmissionData() {
		submission.setSubmission(CommentTree.getInstance().getCommentList(view).get(rowNumber));
	}
	/**
	 * Sets up the view with the proper layout 
	 * grabs the comment to be edited and fills 
	 * the fields with the information that is 
	 * going to be edited.
	 */
	@Override
	protected void initializeLayout() {
		view.setContentView(R.layout.create_comment); //editing a comment/topic (uses same layout as creating one)
		
		
		CommentTree cl = CommentTree.getInstance();
		submission = new CommentTreeElementSubmission(cl.getCommentList(view).get(rowNumber), view);
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
	 * Updates local copy of Comment with edited information then
	 *  send update requests to server to change the server copy
	 *  of the Topic
	 */
	@Override
	protected void processSubmit() {
		CommentTree cl = CommentTree.getInstance();
		DataStorageService dss = DataStorageService.getInstance();
		TaskFactory factory = new TaskFactory(dss);
		
		//TODO: no username update support at the moment
		cl.getCommentList(view).get(rowNumber).setUsername(submission.getSubmission().getUsername());
		cl.getCommentList(view).get(rowNumber).setCommentText(submission.getSubmission().getCommentText());
		cl.getCommentList(view).get(rowNumber).setGPSLocation(submission.getSubmission().getGPSLocation());
		cl.getCommentList(view).get(rowNumber).setImage(submission.getSubmission().getImage());
		
		//TODO: it wasn't anticipated that more than one field would be set at a time
		factory.requestImageUpdate(submission.getSubmission());
		factory.requestLocationUpdate(submission.getSubmission());
		factory.requestTextUpdate(submission.getSubmission());
	}
	
	/**
	 * Grabs the information that the user has inputted 
	 * into the EditText fields and sets the corresponding 
	 * submission fields with that information
	 * Used when the user has clicked submit after editing the
	 * Comment
	 * 
	 */
	@Override
	protected void extractTextFields() {
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.set_comment_text);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
