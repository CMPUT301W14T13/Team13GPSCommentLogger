package com.CMPUT301W14T13.gpscommentlogger.model.content;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcelable;




public abstract class Viewable {
	
	protected String title;
	protected String ID;
	protected String commentText;
	protected Bitmap image;
	protected boolean hasImage;
	protected Date timestamp;
	protected Date freshness;
	protected Location GPSLocation;
	protected ArrayList<Viewable> childPosts;
	
	protected final String anonymous = "anonymous";
	/**
	 * Sets the poster as an anonymous user
	 */
	public abstract void setAnonymous();
	
	/**
	 * Obtains the post ID.
	 * @return the unique post ID of the Viewable object.
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Obtains the name of the content poster.
	 * @return the name of the user that posted the Viewable object.
	 */
	public abstract String getUsername();

	/**
	 * Provides an array that contains all the replies to the parent.
	 * @return Returns an ArrayList containing all replies to the parent object.
	 */
	public ArrayList<Viewable> getChildren() {
		return this.childPosts;
	};
	
	/**
	 * Sets the children of the parent Viewable object.
	 * @param childViewables the children to add to the parent Viewable.
	 */
	public void setChildren(ArrayList<Viewable> childViewables) {
		this.childPosts = childViewables;
	}
	
	/**
	 * Get the title of the Viewable object
	 * @return the Viewable's title
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * Sets the title for the viewable object.
	 * @param newTitle The new title for the Viewable object
	 */
	public void setTitle(String newTitle){
		this.title = newTitle;
	}
	
	/**
	 * Set the username of the Viewable object.
	 * @param newUsername the new username to use.  
	 */
	public abstract void setUsername(String newUsername);
	
	/**
	 * Sets the text within the Viewable object.
	 * @param commentText The new body text of the Viewable object.
	 */
	public abstract void setCommentText(String commentText);
	
	
	/**
	 * Gets the comment text within the object.
	 * @return The comment text within the Viewable object.
	 */
	public abstract String getCommentText();
	
	
	/**
	 * Sets the Viewable to store the given picture.
	 * @param picture the picture to be displayed.
	 */
	public void setImage(Bitmap picture){
		this.image = picture;
	}
	
	/**
	 * Return the image contained within the Viewable.
	 * @return the image contained within the Viewable.
	 */
	public Bitmap getImage(){
		return this.image;
	}
	
	/**
	 * Gets the time that the Viewable was created.
	 * @return the timestamp of the Viewable.
	 */
	public Date getTimestamp(){
		return this.timestamp;
	}
	
	/**
	 * Obtains the Viewable object's GPS location.
	 * @return the GPS location of the viewable
	 */
	public abstract Location getGPSLocation();
	
	/**
	 * Sets the GPS location of the Viewable. 
	 * @param location The GPS location that the Viewable is associated with.
	 */
	public abstract void setGPSLocation(Location location);
	
	/**
	 * Adds a child within the Viewable object.
	 * @param The Viewable to add as a child.
	 */
	public void addChild(Viewable post) {
		childPosts.add(post);
	}
	
	/**
	 * Returns a human readable GPS string location.
	 * @return The GPS location in string form.
	 */
	public abstract String locationString();
	
	/**
	 * Returns the Viewable's popularity. The higher the value the more popular it is.
	 * @return The value representing the Viewable's popularity
	 */
	public abstract Integer getPopularity();
		
	/**
	 * Tells us if the Viewable has an assoicated image.
	 * @return A boolean of true if the Viewable has an image and false if it does not.
	 */
	public boolean getHasImage(){
		return this.hasImage;
	}
}
