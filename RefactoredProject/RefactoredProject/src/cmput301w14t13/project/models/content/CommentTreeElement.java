package cmput301w14t13.project.models.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cmput301w14t13.project.services.BitmapSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


public abstract class CommentTreeElement {
	

	protected String title;
	
	protected String ID;
	
	protected String commentText = "";

	protected Bitmap image;
	
	protected boolean hasImage;

	protected Date timestamp;

	protected Date freshness;

	
	protected Location GPSLocation;
	protected ArrayList<CommentTreeElement> childPosts;
	
	protected String username;
	
	private int indentLevel = 0;
	
	protected static final String Anonymous = "Anonymous";
	protected static final String NewTitle = "New Title";
	
	public CommentTreeElement()
	{
		ID = UUID.randomUUID().toString();
		title = NewTitle;
		username = Anonymous;
		timestamp = new Date();
		childPosts = new ArrayList<CommentTreeElement>();
	}
	
	public CommentTreeElement(String ID)
	{
		this.ID =ID;
		title = NewTitle;
		username = Anonymous;
		timestamp = new Date();
		childPosts = new ArrayList<CommentTreeElement>();
	}

	public CommentTreeElement(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		this.ID = ID;
		this.username = username;
		this.image = picture;
		this.timestamp = timestamp;
		this.commentText = commentText;
		childPosts = new ArrayList<CommentTreeElement>();
		this.hasImage = picture != null;
	}
	

	
	public void setAnonymous() {
		this.username = Anonymous;
	}
	
	/**
	 * Obtains the post ID.
	 * @return  the unique post ID of the Viewable object.
	 * 
	 */
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Obtains the name of the content poster.
	 * @return  the name of the user that posted the Viewable object.
	 * 
	 */
	public String getUsername(){
		return this.username;
	}
	
	/**
	 * Set the username of the Viewable object.
	 * @param newUsername  the new username to use.
	 * 
	 */
	public void setUsername(String newName){
		this.username = newName;
	}
	
	/**
	 * Provides an array that contains all the replies to the parent.
	 * @return Returns an ArrayList containing all replies to the parent object.
	 */
	public ArrayList<CommentTreeElement> getChildren() {
		return this.childPosts;
	};
	
	/**
	 * Sets the children of the parent Viewable object.
	 * @param childViewables the children to add to the parent Viewable.
	 */
	public void setChildren(ArrayList<CommentTreeElement> childViewables) {
		this.childPosts = childViewables;
	}
	
	/**
	 * Get the title of the Viewable object
	 * @return  the Viewable's title
	 * 
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * Sets the title for the viewable object.
	 * @param newTitle  The new title for the Viewable object
	 * 
	 */
	public void setTitle(String newTitle){
		this.title = newTitle;
	}
	

	/**
	 * Sets the text within the Viewable object.
	 * @param commentText  The new body text of the Viewable object.
	 * 
	 */
	public void setCommentText(String commentText){
		this.commentText = commentText;
	}
	
	/**
	 * Gets the comment text within the object.
	 * @return  The comment text within the Viewable object.
	 * 
	 */
	public String getCommentText(){
		return this.commentText;
	}

	/**
	 * Sets the Viewable to store the given picture.
	 * @param picture  the picture to be displayed.
	 * 
	 */
	public void setImage(Bitmap picture){
		this.image = picture;
		hasImage = true;
	}
	
	/**
	 * Return the image contained within the Viewable.
	 * @return  the image contained within the Viewable.
	 * 
	 */
	public Bitmap getImage(){
		return this.image;
	}
	
	/**
	 * Gets the time that the Viewable was created.
	 * @return  the timestamp of the Viewable.
	 * 
	 */
	public Date getTimestamp(){
		return this.timestamp;
	}
	
	/**
	 * Obtains the Viewable object's GPS location.
	 * @return  the GPS location of the viewable
	 * 
	 */
	public Location getGPSLocation(){
		return this.GPSLocation;
	}
	
	/**
	 * Sets the GPS location of the Viewable. 
	 * @param location  The GPS location that the Viewable is associated with.
	 * 
	 */
	public void setGPSLocation(Location location){
		this.GPSLocation = location;
	}
	
	/**
	 * Adds a child within the Viewable object.
	 * @param The Viewable to add as a child.
	 */
	public void addChild(CommentTreeElement post) {
		childPosts.add(post);
	}
		
	/**
	 * Tells us if the Viewable has an associated image.
	 * @return  A boolean of true if the Viewable has an image and false if it does not.
	 * 
	 */
	public boolean getHasImage(){
		return this.hasImage;
	}
	
	/**
	 * Returns a human readable GPS string location.
	 * @return The GPS location in string form.
	 */
	public String locationString() {
	    return Location.convert(GPSLocation.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(GPSLocation.getLongitude(), Location.FORMAT_DEGREES);
	    // TODO: Perhaps move this to the view
	}
	
	/* gets the difference between two dates and corrects for time resolution */
	public String getDateDiff(Date previous, Date current) {
	    // TODO: Perhaps move this to the view
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
	
	public void setLocation(Location gpsLocation2)
	{
		GPSLocation = gpsLocation2;
	}
	
	public Location getLocation()
	{
		return this.GPSLocation;
	}
	
	/**
	 * @return
	 * 
	 */
	public Date getFreshness()
	{
	
		return freshness;
	}

	
	/**
	 * @param freshness
	 * 
	 */
	public void setFreshness(Date freshness)
	{
	
		this.freshness = freshness;
	}
	
	public int getNumberOfChildren()
	{
		int out = 0;
		
		for(CommentTreeElement each : this.childPosts)
		{
			out += 1 + each.getNumberOfChildren();
		}
		
		return out;
	}

	/**
	 * @return
	 * 
	 */
	public int getIndentLevel() {
		
		return indentLevel;
	}

	/**
	 * This sets the indent variable. This value is used in CommentAdapter when displaying a topic's comments and is used to determine how many indent lines will be displayed beside the comment, indicating a reply.
	 * @param indent  is how far the comment is to be indented when displayed
	 * 
	 */
	public void setIndentLevel(int indent) {
		this.indentLevel = indent;
	}
	
	
}