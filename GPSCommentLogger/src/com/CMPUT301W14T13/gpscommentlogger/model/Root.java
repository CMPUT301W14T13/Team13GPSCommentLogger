package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.location.Location;

public class Root implements Viewable {
	private String title;
	private final String username = "default";
	private String ID;
	private String commentText;
	private Bitmap image;
	private boolean hasImage;
	private Date timestamp;
	private Date freshness;
	private ArrayList<Viewable> comments;
	private Location GPSLocation;
	
	public Root()
	{
		//TODO: create automatic ID generation system
		ID = "default";
		title = "initial title";
		comments = new ArrayList<Viewable>();
	}
	

	public Root(String ID)
	{
		this.ID = ID;
		title = "initial title";
		comments = new ArrayList<Viewable>();
	}
	
	
	/** 
	 * @uml.property name="v"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="t:com.CMPUT301W14T13.gpscommentlogger.model.Vote"
	 */
	private Collection<Vote> v;
	/** 
	 * Getter of the property <tt>v</tt>
	 * @return  Returns the v.
	 * @uml.property  name="v"
	 */
	public Collection<Vote> getV()
	
	
	
	{
		return v;
	}
	/** 
	 * @uml.property name="c"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="t:com.CMPUT301W14T13.gpscommentlogger.model.Comment"
	 */
	private Collection<Comment> c;
	/** 
	 * Getter of the property <tt>c</tt>
	 * @return  Returns the c.
	 * @uml.property  name="c"
	 */
	@Override
	public ArrayList<Viewable> getChildren()
	
	
	{
		return comments;
	}
	/** 
	 * Setter of the property <tt>v</tt>
	 * @param v  The v to set.
	 * @uml.property  name="v"
	 */
	public void setV(Collection<Vote> v)
	
	{
	
		this.v = v;
	}
	/** 
	 * Setter of the property <tt>c</tt>
	 * @param c  The c to set.
	 * @uml.property  name="c"
	 */
	public void setC(ArrayList<Viewable> c)
	{

		comments = c;
	}
	@Override
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
	public Location getGPSLocation() {
		return GPSLocation;
	}

	public void setGPSLocation(Location gPSLocation) {
		GPSLocation = gPSLocation;
	}


	@Override
	public void addChild(Viewable post) {
		comments.add(post);
		
	}


	@Override
	public Integer getPopularity() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setPopularity(Integer votes) {
		// TODO Auto-generated method stub
		
	}

	public boolean getHasImage() {
		/* return image != null; */
		return hasImage;
	}

	public void setHasImage(boolean state){
		hasImage = state;
	}
}
