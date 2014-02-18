package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.location.Location;

public class Root implements Viewable {
	private String title;
	private final String username = "default";
	private String ID;
	private String commentText = "";
	private Bitmap image = Bitmap.createBitmap(1,1, Config.ARGB_8888);
	private Date timestamp = new Date();
	private ArrayList<Viewable> comments;
	
	public Root()
	{
		//TODO: create sutomatic ID generation system
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
	public ArrayList<Viewable> getC()
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
	
	@Override
	public boolean equals(Object other)
	{
	    if (other == null) return false;
	    if (other == this) return true;
		if (!(other instanceof Root))return false;
		Root o = (Root)other; 
		
		return ID.equals(o.ID)
				&& title.equals(o.title)
				&& username.equals(o.username)
				&& image.equals(o.image)
				&& timestamp.equals(o.timestamp)
				&& commentText.equals(o.commentText)
				&& comments.equals(o.comments);
	}

}
