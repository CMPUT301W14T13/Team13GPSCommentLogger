package com.CMPUT301W14T13.gpscommentlogger.model.content;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Root implements Viewable, Parcelable {
	private String title;
	private final String username = "default";
	private String ID;
	private String commentText;
	private Bitmap image;
	private boolean hasImage;
	private Date timestamp;
	private Date freshness;
	private Location GPSLocation;
	private ArrayList<Viewable> topics;
	
	public Root()
	{
		//TODO: create automatic ID generation system
		ID = "default";
		title = "initial title";
		topics = new ArrayList<Viewable>();
	}
	

	public Root(String ID)
	{
		this.ID = ID;
		title = "initial title";
		topics = new ArrayList<Viewable>();
	}
	
	@Override

	public ArrayList<Viewable> getChildren()
	

	{
		return topics;
	}

	public void setC(ArrayList<Viewable> c)
	{
		topics = c;
	}
	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String newTitle) {
		title = newTitle;
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
				&& topics.equals(o.topics);
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
	public void addChild(Viewable post) {
		topics.add(post);
		
	}


	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean getHasImage() {
		/* return image != null; */
		return hasImage;

	}


	@Override
	public void setUsername(String newUsername) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String locationString() {
		// TODO Auto-generated method stub
		return null;
	}

}
