package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;

public interface Viewable {

	String getID();
	String getUsername();

	ArrayList<Viewable> getChildren();
	String getTitle();

	void setTitle(String newTitle);
	
	void setCommentText(String commentText);
	String getCommentText();
	
	void setImage(Bitmap picture);
	Bitmap getImage();
	
	Date getTimestamp();
	
	Location getGPSLocation();
	void setGPSLocation(Location location);
	
	void addChild(Viewable post);
}
