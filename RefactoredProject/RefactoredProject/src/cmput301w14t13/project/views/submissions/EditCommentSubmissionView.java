package cmput301w14t13.project.views.submissions;

import android.os.Bundle;
import cmput301w14t13.project.controllers.submissions.EditCommentSubmissionController;
import cmput301w14t13.project.controllers.submissions.ReplyToCommentCommentSubmissionController;

public class EditCommentSubmissionView extends CommentSubmissionView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int rowNumber = getIntent().getIntExtra("row number", -1);
		controller = new EditCommentSubmissionController(this, rowNumber);
	}

}
