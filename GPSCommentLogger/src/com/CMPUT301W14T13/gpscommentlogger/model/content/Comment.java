package com.CMPUT301W14T13.gpscommentlogger.model.content;


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
public class Comment extends Viewable
{

	private static final long serialVersionUID = 2L;
	
	private String ID = "default";
	private String title = "default title";
	private String username = "Anonymous";
	private Bitmap image = null;
	private boolean hasImage = false;
	private List<String> childID = new ArrayList<String>();
	private Date timestamp = new Date();
	private String commentText = "";
	private Location GPSLocation;

	private int indentLevel = 0;
	private ArrayList<Viewable> comments;



	/**
	 * Comment constructors
	 */
	public Comment(){
		//TODO: automatic ID gen
		this.ID = "default";
		username = "Anonymous";
		title = "initial title";
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}

	public Comment(String ID) {
		this.ID = ID;
		username = "Anonymous";
		title = "initial title";
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}


	public Comment(String username, boolean cheatingOverloadSignature)
	{
		//TODO: automatic ID gen
		this.ID = "default";
		this.username = username;
		title = "initial title";
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}


	public Comment(String ID, String username)
	{
		this.ID = ID;
		this.username = username;
		title = "initial title";
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}


	public Comment(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		this.ID = ID;
		this.username = username;
		this.image = picture;
		this.timestamp = timestamp;
		this.commentText = commentText;
		this.comments = new ArrayList<Viewable>();
		this.hasImage = true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	@Override
	public void setImage(Bitmap image) {
		this.image = image;
		this.hasImage = true;
		
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public void setCommentText(String commentText) {
		this.commentText = commentText;
		
	}

	@Override
	public String getCommentText() {
		return commentText;
	}


	public Date getTimestamp() {
		return timestamp;
	}

	public Location getGPSLocation() {
		return GPSLocation;
	}


	public void setGPSLocation(Location location) {
		GPSLocation = location;
	}

	public int getNumberOfReplies(){
		return childID.size();
	}


	public void setAnonymous() {
		this.username = anonymous;
	}

	/**
	 * Checks if two comments are equal by checking
	 * that all of their fields are equal
	 */
	@Override
	public boolean equals(Object other)
	{    
		if (other == null) return false;
    	if (other == this) return true;
		if (!(other instanceof Comment))return false;
		Comment o = (Comment)other; 
		
		Log.w("Comment Equals", "ID: " + ID.equals(o.ID));
		Log.w("Comment Equals", "title: " + title.equals(o.title));
		Log.w("Comment Equals", "username: " + username.equals(o.username));
		Log.w("Comment Equals", "hasImage: " + Boolean.toString(hasImage == o.hasImage));
		Log.w("Comment Equals", "childID: " + childID.equals(o.childID));
		Log.w("Comment Equals", "timestamp: " + timestamp.getTime() + " " + o.timestamp.getTime());
		Log.w("Comment Equals", "commentText: " + commentText.equals(o.commentText));
		Log.w("Comment Equals", "comments: " + comments.equals(o.comments));
		
		boolean imageEquals = true;
		if(image == null)
		{
			if (o.image != null)imageEquals = false;
		}
		else
		{
			imageEquals = image.equals(o.image);
		}
		
		return ID.equals(o.ID)
				&& title.equals(o.title)
				&& username.equals(o.username)
				&& imageEquals
				&& hasImage == o.hasImage
				&& childID.equals(o.childID)
				&& timestamp.equals(o.timestamp)
				&& commentText.equals(o.commentText)
				&& comments.equals(o.comments);
	}

	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}


	
	public int getIndentLevel(){
		
		return indentLevel;
	}

	/* return GPS coordinates as a string */
	public String locationString() {
	    return Location.convert(GPSLocation.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(GPSLocation.getLongitude(), Location.FORMAT_DEGREES);
	}



	/**
	 * This sets the indent variable. This value is used in CommentAdapter
	 * when displaying a topic's comments and is used to determine how many indent lines
	 * will be displayed beside the comment, indicating a reply.
	 * 
	 * 
	 * @param indent is how far the comment is to be indented when displayed
	 */
	public void setIndentLevel(int indent){
		this.indentLevel = indent;
	}
	
	
}
