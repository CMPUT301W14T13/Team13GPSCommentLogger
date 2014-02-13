package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.Collection;


/**
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.CommentView"
 */
public class ThreadView
{

	/** 
	 * @uml.property name="c"
	 * @uml.associationEnd aggregation="composite" inverse="t:com.CMPUT301W14T13.gpscommentlogger.view.CommentMakingView"
	 */
	private CommentMakingView c;

	/** 
	 * Getter of the property <tt>c</tt>
	 * @return  Returns the c.
	 * @uml.property  name="c"
	 */
	public CommentMakingView getC()
	
	
	
	
	{
		return c;
	}

	/** 
	 * Setter of the property <tt>c</tt>
	 * @param c  The c to set.
	 * @uml.property  name="c"
	 */
	public void setC(CommentMakingView c)
	
	
	
	
	{
		this.c = c;
	}

	/** 
	 * @uml.property name="cc"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="composite" inverse="t:com.CMPUT301W14T13.gpscommentlogger.view.CommentView"
	 */
	private Collection<CommentView> cc;

	/** 
	 * Getter of the property <tt>cc</tt>
	 * @return  Returns the cc.
	 * @uml.property  name="cc"
	 */
	public Collection<CommentView> getCc()
	
	
	{
		return cc;
	}

	/** 
	 * Setter of the property <tt>cc</tt>
	 * @param cc  The cc to set.
	 * @uml.property  name="cc"
	 */
	public void setCc(Collection<CommentView> cc)
	
	
	{
		this.cc = cc;
	}

}
