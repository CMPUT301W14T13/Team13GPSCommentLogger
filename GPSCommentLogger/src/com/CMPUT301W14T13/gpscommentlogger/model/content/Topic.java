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
	
	private static final long serialVersionUID = 1L;

	private String title = "default title";
	private String ID = "default";
	private String username = "Anonymous";
	private Bitmap image = null;
	private Date timestamp = new Date();
	private Date freshness = new Date();
	private Comment rootComment = new Comment();
	private String commentText = ""; 
	private ArrayList<Viewable> comments = new ArrayList<Viewable>();
	private Location GPSLocation = new Location("default");
	private boolean hasImage;
	private int commentCount = 0;
	
	public Topic()
	{
		//TODO: create automatic ID generation system
		ID = "default";
		title = "initial title";
		username = "Anonymous";
		timestamp = new Date();
		comments = new ArrayList<Viewable>();

	}
	
	public Topic(String ID)
	{
		this.ID = ID;
		title = "initial title";
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}
	
	public Topic(String username, boolean cheatingOverloadSignature)
	{
		//TODO: automatic ID gen
		this.ID = "default";
		title = "initial title";
		this.username = username;
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}
	
	public Topic(String ID, String username)
	{
		this.ID = ID;
		title = "initial title";
		this.username = username;
		timestamp = new Date();
		comments = new ArrayList<Viewable>();
	}
	

	public Topic(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		this.ID = ID;
		this.username = username;
		this.image = picture;
		this.timestamp = timestamp;
		this.commentText = commentText;
		comments = new ArrayList<Viewable>();
		this.hasImage = true;
	}
	
	public Location getGPSLocation() {
		return GPSLocation;
	}


	public void setGPSLocation(Location gPSLocation) {
		GPSLocation = gPSLocation;
	}
	
	/* return GPS coordinates as a string */
	public String locationString() {
	    return Location.convert(GPSLocation.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(GPSLocation.getLongitude(), Location.FORMAT_DEGREES);
	}

	
	/* increment the vote count (remember users can only vote once) */
	public void upVote(String phoneID){
		
	}
	
	/* decrement the vote count (remember users can only vote once)*/
	public void downVote(String phoneID){
		
	}
	
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username){
		this.username = username;
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
		this.hasImage = true;
		
	}

	@Override
	public Bitmap getImage() {
		
		return image;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}


	

	public void setLocation(Location location){
		this.GPSLocation = location;
	}
	
	public Location getLocation(){
		return GPSLocation;
	}
	
	public void setAnonymous() {
		// TODO Auto-generated method stub
		this.username = anonymous;
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
	
	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}

	public void incrementCommentCount(){
		commentCount++;
	}
	
	public int getCommentCount(){
		return commentCount;
	}
	
	
	/* gets the difference between two dates and corrects for time resolution */
	public String getDateDiff(Date previous, Date current) {
	    long diffInMillies = current.getTime() - previous.getTime();
	    
	    if (diffInMillies >= 0 && diffInMillies < 60000)
	    	return String.valueOf(TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" seconds ago");
	    else if (diffInMillies >= 60000 && diffInMillies < 3600000)
	    	return String.valueOf(TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" minutes ago");
	    else if (diffInMillies >= 3600000 && diffInMillies < 24*3600000)
	    	return String.valueOf(TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" hours ago");
	    else if (diffInMillies >= 24*3600000 && diffInMillies < 24*30*3600000)
	    	return String.valueOf(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" days ago");
	    else if (diffInMillies >= 24*30*3600000 && diffInMillies < 24*30*12*3600000)
	    	return String.valueOf((long) Math.ceil(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS)/31)).concat(" months ago");
	    else
	    	return String.valueOf((long) Math.ceil(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS)/365)).concat(" years ago");
	    
	}
}
