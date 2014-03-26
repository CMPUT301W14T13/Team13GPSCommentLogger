package com.CMPUT301W14T13.gpscommentlogger.model.content;

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

public class Topic extends Viewable

{

	private Comment rootComment = new Comment();
	private ArrayList<Viewable> comments;
	private int commentCount = 0;
	
	public Topic()
	{
		super();
	}
	
	public Topic(String ID)
	{
		super(ID);
	}
	
	public Topic(String username, boolean cheatingOverloadSignature)
	{
		super(username, cheatingOverloadSignature);
	}
	
	public Topic(String ID, String username)
	{
		super(ID, username);
	}
	

	public Topic(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		super(ID, username, picture, timestamp, commentText);
	}


	
	/* return GPS coordinates as a string */
	public String locationString() {
	    return Location.convert(GPSLocation.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(GPSLocation.getLongitude(), Location.FORMAT_DEGREES);
	}


	
	
	@Override
	public boolean equals(Object other)
	{
	    if (other == null) return false;
	    if (other == this) return true;
		if (!(other instanceof Topic))return false;
		Topic o = (Topic)other; 
		
		Log.w("Topic Equals", "ID: " + ID.equals(o.ID));
		Log.w("Topic Equals", "title: " + title.equals(o.title));
		Log.w("Topic Equals", "username: " + username.equals(o.username));
		Log.w("Topic Equals", "timestamp: " + timestamp.getTime() + " " + o.timestamp.getTime());
		Log.w("Topic Equals", "freshness: " + freshness.getTime() + " " + o.freshness.getTime());
		Log.w("Topic Equals", "commentText: " + commentText.equals(o.commentText));
		Log.w("Topic Equals", "rootComment: " + rootComment.equals(o.rootComment));
		Log.w("Topic Equals", "comments: " + comments.equals(o.comments));

		
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
				&& timestamp.equals(o.timestamp)
				&& commentText.equals(o.commentText)
				&& freshness.equals(o.freshness)
				&& rootComment.equals(o.rootComment)
				&& comments.equals(comments)
				&& GPSLocation.equals(GPSLocation);

	}
	

	public void setRootComment(Comment comment){
		this.rootComment = comment;
	}
	
	public Comment getRootComment(){
		return rootComment;
	}

	public void insertChild(Viewable post, int position){
		comments.add(position, post);
	}

	public void incrementCommentCount(){
		commentCount++;
	}
	
	public int getCommentCount(){
		return commentCount;
	}
	
	

}
