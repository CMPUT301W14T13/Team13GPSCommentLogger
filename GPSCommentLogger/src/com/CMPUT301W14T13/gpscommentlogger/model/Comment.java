package com.CMPUT301W14T13.gpscommentlogger.model;


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



public class Comment implements Viewable, Parcelable
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
		this.hasImage = true;
	}


	public Comment(Parcel in){
		readFromParcel(in);
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

	
	public void setChildren(ArrayList<Viewable> comments) {
		// TODO Auto-generated method stub
		children = comments;
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


	public void setGPSLocation(Location location) {
		GPSLocation = location;
	}


	
	public boolean getHasImage() {
		/* return image != null; */
		return hasImage;
	}
	

	public int getNumberOfReplies(){
		return childID.size();
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


	/* Interface for
	 * Parcelable is
	 * handled in the 
	 * methods below
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		//GPSLocation.writeToParcel(dest, flags);
		dest.writeString(ID);
		dest.writeString(username);
		dest.writeString(commentText);
		dest.writeValue(children);
		dest.writeLong(timestamp.getTime()); //convert Date to long and then convert back when reading
		dest.writeValue(image);
		dest.writeInt(indentLevel);
		
	}

	//Must read in the order they were written
	@SuppressWarnings("unchecked") //Fix later if possible. For the meantime, we know that every comment has an array list of children
	public void readFromParcel(Parcel in){
		//GPSLocation = Location.CREATOR.createFromParcel(in);
		ID = in.readString();
		username = in.readString();
		commentText = in.readString();
		children = (ArrayList<Viewable>) in.readValue(Viewable.class.getClassLoader());
		timestamp = new Date(in.readLong());
		image = in.readParcelable(Bitmap.class.getClassLoader());
		indentLevel = in.readInt();
	}
	
	public static final Parcelable.Creator<Comment> CREATOR =
			new Parcelable.Creator<Comment>(){
		public Comment createFromParcel(Parcel in){
			return new Comment(in);
		}
		
		public Comment[] newArray(int size){
			return new Comment[size];
			
		}
	};


	


	@Override
	public void addChild(Viewable post) {
		children.add(post);
		
	}

	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getIndentLevel(){
		
		return indentLevel;
	}

	
	public void setIndentLevel(int indent){
		this.indentLevel = indent;
	}
	
	
}
