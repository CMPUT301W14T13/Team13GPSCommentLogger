package cmput301w14t13.project.services;

import cmput301w14t13.project.models.content.CommentTreeElementSubmission;

public class SubmissionMediator {
	
	private static CommentTreeElementSubmission submission;

	public SubmissionMediator() {
	}
	
	public static CommentTreeElementSubmission getSubmission()
	{
		return submission;
	}
	
	public static void setSubmission(CommentTreeElementSubmission submission)
	{
		SubmissionMediator.submission = submission;
	}

}
