package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import android.location.Location;
import java.util.Collection;



public class CommentThread implements Viewable
{

	private String title;
	private String ID;
	private Date freshness;
	private Comment rootComment;
	private ArrayList<Viewable> comments;
	private Location GPSLocation;
	
	public CommentThread()
	{
		//TODO: create sutomatic ID generation system
		ID = "default";
	}
	
	public CommentThread(String ID)
	{
		this.ID = ID;
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
	public String getID() {
		return ID;
	}

	
	
	
	
}
