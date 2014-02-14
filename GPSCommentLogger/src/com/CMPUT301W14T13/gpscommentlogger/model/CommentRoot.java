package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.location.Location;

public class CommentRoot implements Viewable {
	private String title;
	private String ID;
	private Date freshness;
	private List<CommentThread> comments;
	private Location GPSLocation;
	
	public CommentRoot()
	{
		//TODO: create sutomatic ID generation system
		ID = "default";
	}
	
	public CommentRoot(String ID)
	{
		this.ID = ID;
	}
	
	public 
	
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
	public Collection<Comment> getC()
	
	
	{
		return c;
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
	public void setC(Collection<CommentThread> c)
	{

		this.c = c;
	}
	public String getID() {
		return ID;
	}

}
