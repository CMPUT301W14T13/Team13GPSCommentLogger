package com.CMPUT301W14T13.gpscommentlogger.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.Collection;


public class Comment implements Viewable, Serializable
{
	
	private static final long serialVersionUID = 2L;
	private String ID;
	private String title;
	private String username;
	private Bitmap image;
	private boolean hasImage;
	private List<String> childID;
	private Date timestamp;
	private String commentText;
	private Location GPSLocation;
	
	private ArrayList<Viewable> children;
	private HashMap<String, Vote> votes;


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
		this.hasImage = true;
	}

	/* increment the vote count (remember users can only vote once) */
	public void upVote(String phoneID){
		
	}
	
	/* decrement the vote count (remember users can only vote once)*/
	public void downVote(String phoneID){
		
	}
	
	@Override
	public String getID() {
		return ID;
	}



	@Override
	public ArrayList<Viewable> getChildren() {
		return children;
	}


	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username){
		this.username = username;
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

	public Location getGPSLocation() {
		return GPSLocation;
	}


	public void setGPSLocation(Location gPSLocation) {
		GPSLocation = gPSLocation;
	}

	
	public boolean getHasImage() {
		/* return image != null; */
		return hasImage;
	}
	
	public int getNumberOfReplies(){
		return childID.size();
	}


	@Override
	public void addChild(Viewable post) {
		children.add(post);
		
	}

	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}

}
