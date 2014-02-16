package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import android.graphics.Bitmap;

public interface Viewable {

	String getID();
	String getUsername();

	ArrayList<Viewable> getC();
	String getTitle();

	void setTitle(String newTitle);
	void setCommentText(String commentText);
	String getCommentText();
	void setImage(Bitmap picture);
	Bitmap getImage();
	Date getTimestamp();
}
