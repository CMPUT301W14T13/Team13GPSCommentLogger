package cmput301w14t13.project.controllers.submissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cmput301w14t13.project.R;
import cmput301w14t13.project.controllers.ImageAttachmentController;
import cmput301w14t13.project.controllers.MapViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.CommentTreeElementSubmission;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.services.SubmissionMediator;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;
/**
 * Extends SubmissionController for Creating and editting our Topics
 * Checks submission if the user input is valid and the topic is ready to be 
 * submitted, used when Creating and editing Topics
 * @author nsd
 *
 */
public abstract class TopicSubmissionController extends SubmissionController{
	
	public TopicSubmissionController(TopicSubmissionView view) {
		super(view);
	}

	/**
	 * The function to check if a submission is valid. For a topic it checks
	 * that the title and comment text aren't empty, and for a comment it
	 * checks that the comment text isn't empty. If at any point something
	 * is empty, it will display an appropriate toast notifying the user
	 * what the problem is.
	 * 
	 * @param submission  the topic or comment being checked
	 * @return  a boolean, true if it's a valid submission, false otherwise
	 */
	protected boolean checkSubmission(CommentTreeElement submission){

		boolean submission_ok = true;
		Toast toast = null;
		Context context = view.getApplicationContext();
		String text = ""; 
		int duration = Toast.LENGTH_LONG;

		//check title
		if (submission.getTitle().length() == 0){
			text += "Title cannot be blank";
			submission_ok = false;
		}

		//check comment text
		if (submission.getCommentText().length() == 0){
			if (!submission_ok){
				text += "\n";
			} 
			text += "Comment cannot be blank";
			submission_ok = false;
		}

		//display toast if submission invalid
		if (!submission_ok){
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
		}
		return submission_ok;

	}
}