package cmput301w14t13.project.models.content;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.Collection;

import android.graphics.Bitmap.Config;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Collection;


/**
 * 
 * @author arweber
 *
 * This is the model for the comments that are not top level. It 
 * implements the viewable interface and the parcelable interface so
 * that comments can be passed between activities properly.
 */
public class Comment extends CommentTreeElement
{

	private static final long serialVersionUID = 2L;

	public Comment(){
		super();
	}

	public Comment(String ID) {
		super(ID);
	}

	public Comment(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		super(ID, username, picture, timestamp, commentText);
	}
	
	
}
