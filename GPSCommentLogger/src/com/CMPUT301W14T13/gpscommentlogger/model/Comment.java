package com.CMPUT301W14T13.gpscommentlogger.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import android.graphics.Bitmap;
import java.util.Collection;


public class Comment implements Viewable
{


	private String ID;
	private String title;
	private String username;
	private Bitmap image;
	private boolean hasImage;
	private List<String> childID;
	private Date timestamp;
	private String commentText;

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
	}


	/** 
	 * @uml.property name="t"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="c:com.CMPUT301W14T13.gpscommentlogger.model.Thread"
	 */
	private Topic t = new com.CMPUT301W14T13.gpscommentlogger.model.Topic();


	/** 
	 * Getter of the property <tt>t</tt>
	 * @return  Returns the t.
	 * @uml.property  name="t"
	 */
	public Topic getT()








	{
		return t;
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

	public int getNumberOfReplies(){
		return childID.size();
	}

	

	
	
	
	

	
	
	
	
	
}
