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
/**
 * The abstract class for Topic, Comment, and Root. 
 * It is used when Creating or manipulating any of its subclasses,
 * or displayed by HomeView or TopicView
 * 
 * @author nsd
 *
 */

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
	
	/**
	 * Used to construct a CommentTreeElement with no fields initially filled in
	 * Used When the user clicks the create new Topic button in HomeView
	 * or the "Reply" button in TopicView
	 *  
	 */
	public CommentTreeElement()
	{
		ID = UUID.randomUUID().toString();
		title = NewTitle;
		username = Anonymous;
		timestamp = new Date();
		childPosts = new ArrayList<CommentTreeElement>();
	}
	/**
	 * Used to construct a CommentTreeElement with a specific ID
	 * 
	 * 
	 * @param ID
	 */
	public CommentTreeElement(String ID)
	{
		this.ID =ID;
		title = NewTitle;
		username = Anonymous;
		timestamp = new Date();
		childPosts = new ArrayList<CommentTreeElement>();
	}
	
	/**
	 * Constructs a CommentTreeELement with all of its Fields filled in.
	 * Used when loading Topics or Comments from local saves or the server
	 * when all the fields are already filled.
	 * 
	 * @param ID A unique identifier different for all CommentTreeElements
	 * @param username
	 * @param picture
	 * @param timestamp 
	 * @param commentText
	 */

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


	// Getters and Setters
	public void setAnonymous() {
		this.username = Anonymous;
	}
	public String getID(){
		return this.ID;
	}
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String newName){
		this.username = newName;
	}
	public ArrayList<CommentTreeElement> getChildren() {
		return this.childPosts;
	};
	public void setChildren(ArrayList<CommentTreeElement> childViewables) {
		this.childPosts = childViewables;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String newTitle){
		this.title = newTitle;
	}
	public void setCommentText(String commentText){
		this.commentText = commentText;
	}
	public String getCommentText(){
		return this.commentText;
	}
	public void setImage(Bitmap picture){
		this.image = picture;
		hasImage = true;
	}
	public Bitmap getImage(){
		return this.image;
	}
	public Date getTimestamp(){
		return this.timestamp;
	}
	public Location getGPSLocation(){
		return this.GPSLocation;
	}

	public void setGPSLocation(Location location){
		this.GPSLocation = location;
	}
	public boolean getHasImage(){
		return this.hasImage;
	}
	public void setLocation(Location gpsLocation2){
		GPSLocation = gpsLocation2;
	}
	public Location getLocation(){
		return this.GPSLocation;
	}
	public Date getFreshness(){	
		return freshness;
	}
	public void setFreshness(Date freshness){
		this.freshness = freshness;
	}
	
	/**
	 * Goes through all CommentTreeElements children and recursively checks
	 * the number of children each chid has, then sums them all up. 
	 * 
	 * @return the total amount of children this CommentTreeElement has
	 */
	public int getNumberOfChildren(){
		int out = 0;
		for(CommentTreeElement each : this.childPosts){
			out += 1 + each.getNumberOfChildren();
		}
		return out;
	}
	public int getIndentLevel() {		
		return indentLevel;
	}
	public void setIndentLevel(int indent) {
		this.indentLevel = indent;
	}



	// Methods


	/**
	 * Adds a child CommentTreeElement to this CommentTreeElement's childPost list
	 * The CommentTreeElement added is a reply to this CommentTreeElement
	 *  
	 * Used by CreateSubmissionController when adding a reply to a Comment
	 * Used by CommentTree in Method addElementToCurrent when adding a reply to a Topic
	 *  
	 * @param CommentTreeElement that replies to this CommentTreeElement.
	 */
	public void addChild(CommentTreeElement post) {
		childPosts.add(post);
	}


	/**
	 * Returns a human readable GPS string location.
	 * Used by 
	 * 
	 * @return The GPS location in string form.
	 */
	public String locationString() {
		return Location.convert(GPSLocation.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(GPSLocation.getLongitude(), Location.FORMAT_DEGREES);
		// TODO: Perhaps move this to the view
	}

	/**
	 * Returns the difference between two times in a readable manner using the 
	 * most relevant format E.g. seconds, minutes, hours, days
	 * Used inside TopicView and HomeView to show the user how old a Topic or 
	 * Comment is.
	 * 
	 * @param previous. the date the Topic or Comment was Created 
	 * @param current date the date when method is called
	 * @return
	 */
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

}
