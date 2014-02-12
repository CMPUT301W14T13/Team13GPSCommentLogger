package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.Date;
import java.util.HashMap;

import android.location.Location;
import java.util.Collection;



public class Thread
{

	private String title;
	private Date freshness;
	private Comment rootComment;
	private HashMap <Integer,Comment> comments;
	private Location GPSLocation;
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
	public void setC(Collection<Comment> c)
	{

		this.c = c;
	}
	
	
	
	
}
