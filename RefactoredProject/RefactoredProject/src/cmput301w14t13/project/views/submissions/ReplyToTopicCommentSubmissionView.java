package cmput301w14t13.project.views.submissions;

import android.os.Bundle;
import cmput301w14t13.project.controllers.submissions.ReplyToCommentCommentSubmissionController;
import cmput301w14t13.project.controllers.submissions.ReplyToTopicCommentSubmissionController;

public class ReplyToTopicCommentSubmissionView extends CommentSubmissionView {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		controller = new ReplyToTopicCommentSubmissionController(this);
		
		controller.initialize();
	}	
	
}
