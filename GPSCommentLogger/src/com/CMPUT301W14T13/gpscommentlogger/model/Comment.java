package com.CMPUT301W14T13.gpscommentlogger.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;

import java.util.Collection;


public class Comment implements Viewable
{


	private String ID = "default";
	private String title = "default title";
	private String username = "Anonymous";
	private Bitmap image = null;
	private boolean hasImage = false;;
	private List<String> childID = new ArrayList<String>();
	private Date timestamp = new Date();
	private String commentText = "default";

	private ArrayList<Viewable> children = new ArrayList<Viewable>();
	private HashMap<String, Vote> votes = new HashMap<String, Vote>();


	public Comment(){
		//TODO: automatic ID gen
		this.ID = "default";
		username = "Anonymous";
		title = "initial title";
		timestamp = new Date();
		children = new ArrayList<Viewable>();
	}


	public Comment(String ID) {
		this.ID = ID;
		username = "Anonymous";
		title = "initial title";
		timestamp = new Date();
		children = new ArrayList<Viewable>();
	}


	public Comment(String username, boolean cheatingOverloadSignature)
	{
		//TODO: automatic ID gen
		this.ID = "default";
		this.username = username;
		title = "initial title";
		timestamp = new Date();
		children = new ArrayList<Viewable>();
	}


	public Comment(String ID, String username)
	{
		this.ID = ID;
		this.username = username;
		title = "initial title";
		timestamp = new Date();
		children = new ArrayList<Viewable>();
	}


	public Comment(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		this.ID = ID;
		this.username = username;
		this.image = picture;
		this.timestamp = timestamp;
		this.commentText = commentText;
		children = new ArrayList<Viewable>();
	}


	@Override
	public String getID() {
		return ID;
	}


	/** 
	 * @uml.property name="v"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="c:com.CMPUT301W14T13.gpscommentlogger.model.Vote"
	 */
	private Collection<Vote> v;


	@Override
	public ArrayList<Viewable> getC() {
		return children;
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
	public void setImage(Bitmap image) {
		this.image = image;
		
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


	public boolean getHasPicture() {
		return image != null;
	}

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
		Log.w("Comment Equals", "children: " + children.equals(o.children));
		Log.w("Comment Equals", "votes: " + votes.equals(o.votes));
		
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
				&& children.equals(o.children)
				&& votes.equals(votes);
	}
	
}
