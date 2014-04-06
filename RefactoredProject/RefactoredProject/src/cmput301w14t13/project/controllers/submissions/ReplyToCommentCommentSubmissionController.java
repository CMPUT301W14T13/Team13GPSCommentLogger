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
			factory.requestPost(parent.getID(), submission.getSubmission(),view);
		}
	}

	@Override
	protected void extractTextFields() {
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.set_comment_text);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
