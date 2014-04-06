package cmput301w14t13.project.controllers.submissions;

import android.widget.EditText;
import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.submissions.ReplyToCommentCommentSubmissionView;
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;

public class ReplyToTopicCommentSubmissionController extends
		CommentSubmissionController {

	public ReplyToTopicCommentSubmissionController(ReplyToTopicCommentSubmissionView view) {
		super(view);
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
		cl.addElementToCurrent(submission.getSubmission());
		factory.requestPost(cl.getElement(view).getID(), submission.getSubmission(), view);
	}

	@Override
	protected void extractTextFields() {
		EditText text = (EditText) view.findViewById(R.id.set_comment_username);
		submission.getSubmission().setUsername(text.getText().toString().trim());

		text = (EditText) view.findViewById(R.id.set_comment_text);
		submission.getSubmission().setCommentText(text.getText().toString().trim());
	}

}
