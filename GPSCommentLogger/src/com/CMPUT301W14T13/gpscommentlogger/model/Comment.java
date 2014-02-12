package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import java.util.Collection;




public class Comment
{

	private int ID;
	private String username;
	private Bitmap image;
	private boolean hasImage;
	private List<Integer>child_ID;
	private Date timestamp;
	private String commentText;
	
	private HashMap<String, Vote> votes;
		
	public Comment(){
		
	}

	/** 
	 * @uml.property name="t"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="c:com.CMPUT301W14T13.gpscommentlogger.model.Thread"
	 */
	private Thread t = new com.CMPUT301W14T13.gpscommentlogger.model.Thread();

	/** 
	 * Getter of the property <tt>t</tt>
	 * @return  Returns the t.
	 * @uml.property  name="t"
	 */
	public Thread getT()
	
	
	
	
	{
		return t;
	}

	/** 
	 * Setter of the property <tt>t</tt>
	 * @param t  The t to set.
	 * @uml.property  name="t"
	 */
	public void setT(Thread t)
	
	
	
	
	{
		this.t = t;
	}

	/** 
	 * @uml.property name="v"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="c:com.CMPUT301W14T13.gpscommentlogger.model.Vote"
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
	 * Setter of the property <tt>v</tt>
	 * @param v  The v to set.
	 * @uml.property  name="v"
	 */
	public void setV(Collection<Vote> v)
	
	
	{
		this.v = v;
	}

	
	
	
	

	
	
	
	
	
}
