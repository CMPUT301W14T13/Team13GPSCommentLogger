package cmput301w14t13.project.views.submissions;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.controllers.submissions.SubmissionController;
import cmput301w14t13.project.controllers.submissions.TopicSubmissionController;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageButton;

public abstract class SubmissionView extends RankedHierarchicalActivity implements UpdateInterface{

	protected SubmissionController controller;

	@Override
	public void update()
	{
		Log.d("Image Attach", "Image received: " + controller.getSubmission().getSubmission().getImage().toString());
		ImageButton attachButton = (ImageButton) findViewById(R.id.imageButton1); // set attach button to image selected
		attachButton.setImageBitmap(controller.getSubmission().getSubmission().getImage());
	}

}
