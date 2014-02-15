package com.CMPUT301W14T13.gpscommentlogger.view;


/**
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.ThreadView"
 */
public class CommentMakingView
{

	/**
	 * @uml.property   name="i"
	 * @uml.associationEnd   aggregation="composite" inverse="c:com.CMPUT301W14T13.gpscommentlogger.view.PictureAttachmentView"
	 */
	private PictureAttachmentView i;

	/** 
	 * Getter of the property <tt>i</tt>
	 * @return  Returns the i.
	 * @uml.property  name="i"
	 */
	public PictureAttachmentView getI()
	
	
	
	
	
	
	
	
	
	
	{
		return i;
	}

	/** 
	 * Setter of the property <tt>i</tt>
	 * @param i  The i to set.
	 * @uml.property  name="i"
	 */
	public void setI(PictureAttachmentView i)
	
	
	
	
	
	
	
	
	
	
	{
		this.i = i;
	}

	/**
	 * @uml.property   name="l"
	 * @uml.associationEnd   aggregation="composite" inverse="c:com.CMPUT301W14T13.gpscommentlogger.view.LocationSelectionView"
	 */
	private LocationSelectionView l;

	/** 
	 * Getter of the property <tt>l</tt>
	 * @return  Returns the l.
	 * @uml.property  name="l"
	 */
	public LocationSelectionView getL()
	
	
	
	
	
	
	
	{
		return l;
	}

	/** 
	 * Setter of the property <tt>l</tt>
	 * @param l  The l to set.
	 * @uml.property  name="l"
	 */
	public void setL(LocationSelectionView l)
	
	
	
	
	
	
	
	{
		this.l = l;
	}

	/**
	 * @uml.property   name="h"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="c:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView h = new com.CMPUT301W14T13.gpscommentlogger.view.HomeView();

	/** 
	 * Getter of the property <tt>h</tt>
	 * @return  Returns the h.
	 * @uml.property  name="h"
	 */
	public HomeView getH()
	
	
	
	
	
	
	{
		return h;
	}

	/** 
	 * Setter of the property <tt>h</tt>
	 * @param h  The h to set.
	 * @uml.property  name="h"
	 */
	public void setH(HomeView h)
	
	
	
	
	
	
	{
		this.h = h;
	}

	/**
	 * @uml.property   name="t"
	 * @uml.associationEnd   multiplicity="(1 1)" inverse="c:com.CMPUT301W14T13.gpscommentlogger.view.ThreadView"
	 */
	private ThreadView t = new com.CMPUT301W14T13.gpscommentlogger.view.ThreadView();

	/** 
	 * Getter of the property <tt>t</tt>
	 * @return  Returns the t.
	 * @uml.property  name="t"
	 */
	public ThreadView getT()
	
	
	
	
	
	{
		return t;
	}

	/** 
	 * Setter of the property <tt>t</tt>
	 * @param t  The t to set.
	 * @uml.property  name="t"
	 */
	public void setT(ThreadView t)
	
	
	
	
	
	{
		this.t = t;
	}

		
		/**
		 */
	private void attachPicture(){
			
	}

	private void selectUsername(){
		
	}

	private void editLocation(){
		
	}
	
	private void commentText(){
		
	}
}
