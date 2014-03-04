package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;

public interface Viewable {

	final String anonymous = "Anonymous";
	void setAnonymous();
	
	String getID();
	String getUsername();

	ArrayList<Viewable> getChildren();
	String getTitle();

	void setTitle(String newTitle);
	void setUsername(String newUsername);
	void setCommentText(String commentText);
	String getCommentText();
	
	void setImage(Bitmap picture);
	Bitmap getImage();
	
	Date getTimestamp();
	
	Location getGPSLocation();
	void setGPSLocation(Location location);
	
	void addChild(Viewable post);
	
	Integer getPopularity();
		
	boolean getHasImage();
}
