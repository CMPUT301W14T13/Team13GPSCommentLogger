package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import android.graphics.Bitmap;
import android.location.Location;
import java.util.Collection;



public class Topic implements Viewable
{

	private String title;
	private String ID;
	private String username;
	private Bitmap image;
	private Date timestamp;
	private Date freshness;
	private Comment rootComment;
	private ArrayList<Viewable> comments;
	private Location GPSLocation;
	private String commentText;
	
	public Topic()
	{
		//TODO: create automatic ID generation system
		ID = "default";
		title = "initial title";
		
	}
	
	public Topic(String ID)
	{
		this.ID = ID;
		title = "initial title";
	}
	
	public Topic(String username, boolean cheatingOverloadSignature)
	{
		//TODO: automatic ID gen
		this.ID = "default";
		title = "initial title";
		this.username = username;
	}
	
	public Topic(String ID, String username)
	{
		this.ID = ID;
		title = "initial title";
		this.username = username;
	}
	


	public ArrayList<Viewable> getChildren()
	
	
	{
		return comments;
	}
	
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

	
	public void setLocation(Location location){
		this.GPSLocation = location;
	}
	
	public Location getLocation(){
		return GPSLocation;
	}
	
	public void setRootComment(Comment comment){
		this.rootComment = comment;
	}
	
	public Comment getRootComment(){
		return rootComment;
	}
}
