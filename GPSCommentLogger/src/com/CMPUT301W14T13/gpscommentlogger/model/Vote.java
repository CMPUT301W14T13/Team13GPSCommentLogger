package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.Date;



public class Vote
{
	private int value;
	private Date timestamp;
	private String androidID;
	/** 
	 * @uml.property name="t"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="v:com.CMPUT301W14T13.gpscommentlogger.model.Thread"
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
	/** 
	 * Setter of the property <tt>t</tt>
	 * @param t  The t to set.
	 * @uml.property  name="t"
	 */
	public void setT(CommentThread t)
	
	
	
	
	{
		this.t = t;
	}
	/** 
	 * @uml.property name="c"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="v:com.CMPUT301W14T13.gpscommentlogger.model.Comment"
	 */
	private Comment c = new com.CMPUT301W14T13.gpscommentlogger.model.Comment();
	/** 
	 * Getter of the property <tt>c</tt>
	 * @return  Returns the c.
	 * @uml.property  name="c"
	 */
	public Comment getC()
	
	
	
	{
		return c;
	}
	/** 
	 * Setter of the property <tt>c</tt>
	 * @param c  The c to set.
	 * @uml.property  name="c"
	 */
	public void setC(Comment c)
	
	
	
	{
		this.c = c;
	}
	
	
	
	
	
	
}
