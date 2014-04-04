package cmput301w14t13.project.models.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.location.Location;

import android.os.Parcel;
import android.os.Parcelable;

import android.util.Log;


import java.util.Collection;
import java.util.concurrent.TimeUnit;


/**
 *  * This class is for top level comments, a subclass of CommentTreeElement.
 * Used by CreateSubmissionControler to create and edit a Topic
 * and by HomeView and TopicView to display Topics
 * @author nsd
 *
 */
public class Topic extends CommentTreeElement

{
	public Topic()
	{
		super();
	}
	
	public Topic(String ID)
	{
		super(ID);
	}
	
	public Topic(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		super(ID, username, picture, timestamp, commentText);
	}

}
