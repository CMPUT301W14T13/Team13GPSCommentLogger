package com.CMPUT301W14T13.gpscommentlogger.model.content;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is the root view that will display
 * all top level comments.
 */
public class Root extends Viewable {
	private String title;
	private final String username = "default";
	private String ID;
	private String commentText;
	private Bitmap image;
	private boolean hasImage;
	private Date timestamp;
	private Date freshness;
	private Location GPSLocation;
	private ArrayList<Viewable> comments;
	
	public Root()
	{
		//TODO: create automatic ID generation system
		ID = "ROOT";
		title = "initial title";
		comments = new ArrayList<Viewable>();
	}
	

	public Root(String ID)
	{
		this.ID = ID;
		title = "initial title";
		comments = new ArrayList<Viewable>();
	}
	
	public String getUsername() {
		return username;
	}

	@Override
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Override
	public String getCommentText() {
		return commentText;
	}

	@Override
	public void setImage(Bitmap picture) {
		this.image = picture;
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Function to compare top level
	 * comment to other.
	 */
	@Override
	public boolean equals(Object other)
	{
	    if (other == null) return false;
	    if (other == this) return true;
		if (!(other instanceof Root))return false;
		Root o = (Root)other; 
		
		return ID.equals(o.ID)
				&& title.equals(o.title)
				&& username.equals(o.username)
				&& image.equals(o.image)
				&& timestamp.equals(o.timestamp)
				&& commentText.equals(o.commentText)
				&& comments.equals(o.comments);
	}

	@Override
	public void setAnonymous() {
		// TODO Auto-generated method stub
		
	}
	public Location getGPSLocation() {
		return GPSLocation;
	}

	public void setGPSLocation(Location gPSLocation) {
		GPSLocation = gPSLocation;
	}




	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUsername(String newUsername) {
		// TODO Auto-generated method stub
		
	}


	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}



	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}



	public String locationString() {
		// TODO Auto-generated method stub
		return null;
	}

}
