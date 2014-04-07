package cmput301w14t13.project.views.submissions;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.controllers.submissions.SubmissionController;
import cmput301w14t13.project.controllers.submissions.TopicSubmissionController;
import cmput301w14t13.project.services.LocationSelection;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public abstract class SubmissionView extends RankedHierarchicalActivity implements UpdateInterface{

	protected SubmissionController controller;

	@Override
	public void update()
	{
		if(controller.getSubmission().getSubmission().getHasImage())
		{
			Log.d("Image Attach", "Image received: " + controller.getSubmission().getSubmission().getImage().toString());
			ImageButton attachButton = (ImageButton) findViewById(R.id.imageButton1); // set attach button to image selected
			Log.d("Image Attach", Boolean.toString(attachButton == null));
			attachButton.setImageBitmap(controller.getSubmission().getSubmission().getImage());
		}
	}
	
	public void attachImage(View v)
	{
		controller.attachImage(v);
	}
	
	public void openMap(View v)
	{
		controller.openMap(v);
	}
	
	public void submit(View v) throws InterruptedException
	{
		controller.submit(v);
	}

	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 1){
			if (resultCode == RESULT_OK){
				double latitude = data.getDoubleExtra("lat", LocationSelection.getInstance().getLocation().getLatitude());
				double longitude = data.getDoubleExtra("lon", LocationSelection.getInstance().getLocation().getLongitude());
				Location location = new Location("default");
				location.setLongitude(longitude);
				location.setLatitude(latitude);
				controller.getSubmission().setGpsLocation(location);
			}
		}
	}
}
