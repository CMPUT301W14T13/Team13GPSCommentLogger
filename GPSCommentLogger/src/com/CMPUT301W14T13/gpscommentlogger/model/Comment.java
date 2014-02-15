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
	private String username;
	private Bitmap image;
	private boolean hasImage;
	private List<String>childID;
	private Date timestamp;
	private String commentText;
	
	private HashMap<String, Vote> votes;
		
	public Comment(){
		//TODO: automatic ID gen
		this.ID = "default";
	}

	public Comment(String ID) {
		this.ID = ID;
	}

	/** 
	 * @uml.property name="t"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="c:com.CMPUT301W14T13.gpscommentlogger.model.Thread"
	 */
	private CommentThread t = new com.CMPUT301W14T13.gpscommentlogger.model.CommentThread();

	/** 
	 * Getter of the property <tt>t</tt>
	 * @return  Returns the t.
	 * @uml.property  name="t"
	 */
	public CommentThread getT()
	
	
	
	
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
		// TODO Auto-generated method stub
		return null;
	}

	

	

	
	
	
	

	
	
	
	
	
}
