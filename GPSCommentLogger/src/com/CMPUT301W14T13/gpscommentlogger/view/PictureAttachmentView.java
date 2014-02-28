package com.CMPUT301W14T13.gpscommentlogger.view;


/**
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.CommentMakingView"
 */
public class PictureAttachmentView
{

	private void getPicture(){
		
	}
	/** 
	 * @uml.property name="c"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="i:com.CMPUT301W14T13.gpscommentlogger.view.CommentMakingView"
	 */
	private CreateCommentActivity c = new com.CMPUT301W14T13.gpscommentlogger.view.CreateCommentActivity();

	/** 
	 * Getter of the property <tt>c</tt>
	 * @return  Returns the c.
	 * @uml.property  name="c"
	 */
	public CreateCommentActivity getC()
	
	
	{
		return c;
	}

	/** 
	 * Setter of the property <tt>c</tt>
	 * @param c  The c to set.
	 * @uml.property  name="c"
	 */
	public void setC(CreateCommentActivity c)
	
	
	{
		this.c = c;
	}

}
