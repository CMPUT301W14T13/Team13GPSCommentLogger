package cmput301w14t13.project.models.content;

import cmput301w14t13.project.controllers.CreateSubmissionView;
import android.graphics.Bitmap;
import android.location.Location;

/**
 * This class is the same as CommentTreeElement. The difference being
 * that this one is designed to hold a View which it updates as the
 * comment object is mutated.
 * 
 * This can be used in activities where comments are created or edited,
 * where the view will be referenced by this class, and the view is
 * updated when an attribute in this class is modified.
 * **/
public class CommentTreeElementSubmission{

	private CreateSubmissionView view;
	private Location gpsLocation;
	private Location userLocation;
	
	private CommentTreeElement submission;
	
	public CommentTreeElementSubmission(CommentTreeElement ele, CreateSubmissionView view)
	{
		this.submission = ele;
		this.view = view;
	}
	
	public void setBitmap(Bitmap bitmap)
	{
		this.submission.setImage(bitmap);
		this.view.update();
	}
	
	public CommentTreeElement getSubmission()
	{
		return submission;
	}

	public Location getGpsLocation()
	{

		return gpsLocation;
	}

	public void setGpsLocation(Location gpsLocation)
	{

		this.gpsLocation = gpsLocation;
	}

	public Location getUserLocation()
	{

		return userLocation;
	}

	public void setUserLocation(Location userLocation)
	{

		this.userLocation = userLocation;
	}
	

}
