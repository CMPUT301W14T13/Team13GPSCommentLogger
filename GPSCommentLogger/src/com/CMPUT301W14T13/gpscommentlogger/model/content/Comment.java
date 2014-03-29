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

	private int indentLevel = 0;

	/**
	 * Comment constructors
	 */
	public Comment(){
		super();

	}

	public Comment(String ID) {
		super(ID);
	}


	public Comment(String username, boolean cheatingOverloadSignature)
	{
		super(username, cheatingOverloadSignature);
	}


	public Comment(String ID, String username)
	{
		super(ID, username);
	}


	public Comment(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		super(ID, username, picture, timestamp, commentText);
	}

	public int getNumberOfReplies(){
		return childPosts.size();
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
		Log.w("Comment Equals", "childID: " + childPosts.equals(o.childPosts));
		Log.w("Comment Equals", "timestamp: " + timestamp.getTime() + " " + o.timestamp.getTime());
		Log.w("Comment Equals", "commentText: " + commentText.equals(o.commentText));
		
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
				&& timestamp.toString().equals(o.timestamp.toString())
				&& commentText.equals(o.commentText)
				&& childPosts.equals(o.childPosts)
				&& GPSLocation.toString().equals(o.GPSLocation.toString());
	}
	
	public int getIndentLevel(){
		
		return indentLevel;
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
