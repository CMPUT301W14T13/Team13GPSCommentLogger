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
 * This class is for top level comments. It implements Viewable and
 * Parcelable to pass/process attributes properly.
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
