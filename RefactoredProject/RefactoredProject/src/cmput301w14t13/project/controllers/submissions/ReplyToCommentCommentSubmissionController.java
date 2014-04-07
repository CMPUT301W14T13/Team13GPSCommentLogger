package cmput301w14t13.project.controllers.submissions;

import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.ReplyToCommentCommentSubmissionView;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;
/**
 * Extends SubmissionController, responsible for handling
 * creating of Comments in reply to other Comments
 * initializes the proper fields for user input, extracts
 * user inputed information from the text fields and processes
 * submission to be sent to the server, delegates checking
 * if the submission is valid to the CommentSubmissionController
 *  
 * @author nsd
 *
 */
public class ReplyToCommentCommentSubmissionController extends
		CommentSubmissionController {

	private int rowNumber;

	public ReplyToCommentCommentSubmissionController(ReplyToCommentCommentSubmissionView view, int rowNumber) {
		super(view);
		this.rowNumber = rowNumber;
	}

	@Override
	protected void constructSubmissionData() {
		submission.setSubmission(new Comment());
	}

	@Override
	protected void initializeLayout() {
		view.setContentView(R.layout.create_comment); //creating a comment
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		text.setText(CommentTree.getInstance().getCurrentUsername());
	}
	/**
	 * saves a local copy of the comment as a reply to the correct parent
	 * and then sends a post request to the server with the correct parent id
	 * and the submission itself
	 */
	@Override
	protected void processSubmit() {
		CommentTree cl = CommentTree.getInstance();
		DataStorageService dss = DataStorageService.getInstance();
		TaskFactory factory = new TaskFactory(dss);
		
		CommentTreeElement parent = cl.getCommentList(view).get(rowNumber);//get the comment being replied to
		submission.getSubmission().setIndentLevel(parent.getIndentLevel() + 1);//set the indent level of the new comment to be 1 more than the one being replied to
		
		
		//TODO: For the moment, don't add any comments if their indent is beyond what is in comment_view.xml. Can be dealt with later.
		if (submission.getSubmission().getIndentLevel() <= 5){
			parent.addChild(submission.getSubmission());
			factory.requestPost(parent.getID(), submission.getSubmission());
		}
	}
	/**
	 * extracts user inputted information from the text fields 
	 * when user clicks the submit button
	 */
	@Override
	protected void extractTextFields() {
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.set_comment_text);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
