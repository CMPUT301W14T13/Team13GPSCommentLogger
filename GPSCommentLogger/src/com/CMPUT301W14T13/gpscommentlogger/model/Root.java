package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.location.Location;

public class Root implements Viewable {
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
		ID = "default";
		title = "initial title";
		comments = new ArrayList<Viewable>();
	}
	

	public Root(String ID)
	{
		this.ID = ID;
		title = "initial title";
		comments = new ArrayList<Viewable>();
	}
	
	@Override

	public ArrayList<Viewable> getChildren()
	

	{
		return comments;
	}

	public void setC(ArrayList<Viewable> c)
	{
		comments = c;
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
	public void addChild(Viewable post) {
		comments.add(post);
		
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

}
