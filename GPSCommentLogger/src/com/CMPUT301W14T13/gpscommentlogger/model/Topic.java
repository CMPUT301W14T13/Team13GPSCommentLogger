package com.CMPUT301W14T13.gpscommentlogger.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcelable;

import java.util.Collection;



public class Topic implements Viewable, Serializable
{

	
	private static final long serialVersionUID = 1L;
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
		username = "Anonymous";
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
	
	public Location getGPSLocation() {
		return GPSLocation;
	}

	public void setGPSLocation(Location gPSLocation) {
		GPSLocation = gPSLocation;
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

		
	public void setRootComment(Comment comment){
		this.rootComment = comment;
	}
	
	public Comment getRootComment(){
		return rootComment;
	}

	public void setChildren(ArrayList<Viewable> threadComments) {
		// TODO Auto-generated method stub
		comments = threadComments;
	}


	public void addChild(Viewable post) {
		comments.add(post);
		
	}
}
