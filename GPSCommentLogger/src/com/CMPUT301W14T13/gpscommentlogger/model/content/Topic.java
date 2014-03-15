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




public class Topic implements Viewable, Parcelable

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
	

	public Topic(Parcel in){
		readFromParcel(in);
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

	public ArrayList<Viewable> getChildren()
	
	
	{
		return comments;
	}
	
	/* increment the vote count (remember users can only vote once) */
	public void upVote(String phoneID){
		
	}
	
	/* decrement the vote count (remember users can only vote once)*/
	public void downVote(String phoneID){
		
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

	public void setChildren(ArrayList<Viewable> threadComments) {
		// TODO Auto-generated method stub
		comments = threadComments;
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
		GPSLocation.writeToParcel(dest, flags); //This must go first
		dest.writeString(title);
		dest.writeString(ID);
		dest.writeString(username);
		dest.writeString(commentText);
		dest.writeValue(comments);
		dest.writeLong(timestamp.getTime()); //convert Date to long and then convert back when reading
		dest.writeValue(image);
		
	}
	
	//Must read in the order they were written
	@SuppressWarnings("unchecked") //Fix later if possible. For the meantime, we know that every topic has an array list of children
	private void readFromParcel(Parcel in){
		GPSLocation = Location.CREATOR.createFromParcel(in);
		title = in.readString();
		ID = in.readString();
		username = in.readString();
		commentText = in.readString();
		comments = (ArrayList<Viewable>) in.readValue(Viewable.class.getClassLoader());
		timestamp = new Date(in.readLong());
		image = in.readParcelable(Bitmap.class.getClassLoader());
		
	
	
	}
	
	public static final Parcelable.Creator<Topic> CREATOR =
			new Parcelable.Creator<Topic>(){
		public Topic createFromParcel(Parcel in){
			return new Topic(in);
		}
		
		public Topic[] newArray(int size){
			return new Topic[size];
			
		}
	};


	public void addChild(Viewable post) {
		comments.add(post);
		
	}

	public void insertChild(Viewable post, int position){
		comments.add(position, post);
	}
	
	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public boolean getHasImage() {
		/* return image != null; */
		return hasImage;
	}


}
