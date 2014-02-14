package com.CMPUT301W14T13.gpscommentlogger.view;


/**
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.ThreadView"
 */
public class CommentView
{

	//I want to reply to this comment
	private void reply(){
		
	}
	
	//I want to add this comment to my favourites list
	private void addToFavourites(){
		
		
	}
	
	//I want to read this comment and/or its replies later
	private void readLater(){
		
	}
	
	//I want to edit my comment
	private void editComment(){
		
	}
	/**
	 * @uml.property  name="homeView"
	 * @uml.associationEnd  inverse="commentView:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView homeView;

	/**
	 * Getter of the property <tt>homeView</tt>
	 * @return  Returns the homeView.
	 * @uml.property  name="homeView"
	 */
	public HomeView getHomeView()
	
	
	
	
	
	
	
	
	
	{
	
		return homeView;
	}

	/**
	 * Setter of the property <tt>homeView</tt>
	 * @param homeView  The homeView to set.
	 * @uml.property  name="homeView"
	 */
	public void setHomeView(HomeView homeView)
	
	
	
	
	
	
	
	
	
	{
	
		this.homeView = homeView;
	}

	/**
	 * @uml.property  name="homeView1"
	 * @uml.associationEnd  inverse="commentView1:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView homeView1;

	/**
	 * Getter of the property <tt>homeView1</tt>
	 * @return  Returns the homeView1.
	 * @uml.property  name="homeView1"
	 */
	public HomeView getHomeView1()
	
	
	
	
	
	
	
	
	{
	
		return homeView1;
	}

	/**
	 * Setter of the property <tt>homeView1</tt>
	 * @param homeView1  The homeView1 to set.
	 * @uml.property  name="homeView1"
	 */
	public void setHomeView1(HomeView homeView1)
	
	
	
	
	
	
	
	
	{
	
		this.homeView1 = homeView1;
	}

	/**
	 * @uml.property  name="homeView2"
	 * @uml.associationEnd  aggregation="composite" inverse="commentView2:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView homeView2;

	/**
	 * Getter of the property <tt>homeView2</tt>
	 * @return  Returns the homeView2.
	 * @uml.property  name="homeView2"
	 */
	public HomeView getHomeView2()
	
	
	
	
	
	
	
	{
	
		return homeView2;
	}

	/**
	 * Setter of the property <tt>homeView2</tt>
	 * @param homeView2  The homeView2 to set.
	 * @uml.property  name="homeView2"
	 */
	public void setHomeView2(HomeView homeView2)
	
	
	
	
	
	
	
	{
	
		this.homeView2 = homeView2;
	}

	/**
	 * @uml.property  name="homeView3"
	 * @uml.associationEnd  aggregation="composite" inverse="commentView3:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView homeView3;

	/**
	 * Getter of the property <tt>homeView3</tt>
	 * @return  Returns the homeView3.
	 * @uml.property  name="homeView3"
	 */
	public HomeView getHomeView3()
	
	
	
	
	
	
	{
	
		return homeView3;
	}

	/**
	 * Setter of the property <tt>homeView3</tt>
	 * @param homeView3  The homeView3 to set.
	 * @uml.property  name="homeView3"
	 */
	public void setHomeView3(HomeView homeView3)
	
	
	
	
	
	
	{
	
		this.homeView3 = homeView3;
	}

	/**
	 * @uml.property  name="homeView4"
	 * @uml.associationEnd  aggregation="composite" inverse="commentView4:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView homeView4;

	/**
	 * Getter of the property <tt>homeView4</tt>
	 * @return  Returns the homeView4.
	 * @uml.property  name="homeView4"
	 */
	public HomeView getHomeView4()
	
	
	
	
	
	{
	
		return homeView4;
	}

	/**
	 * Setter of the property <tt>homeView4</tt>
	 * @param homeView4  The homeView4 to set.
	 * @uml.property  name="homeView4"
	 */
	public void setHomeView4(HomeView homeView4)
	
	
	
	
	
	{
	
		this.homeView4 = homeView4;
	}

	/**
	 * @uml.property  name="homeView5"
	 * @uml.associationEnd  inverse="commentView5:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeView homeView5;

	/**
	 * Getter of the property <tt>homeView5</tt>
	 * @return  Returns the homeView5.
	 * @uml.property  name="homeView5"
	 */
	public HomeView getHomeView5()
	
	
	
	
	{
	
		return homeView5;
	}

	/**
	 * Setter of the property <tt>homeView5</tt>
	 * @param homeView5  The homeView5 to set.
	 * @uml.property  name="homeView5"
	 */
	public void setHomeView5(HomeView homeView5)
	
	
	
	
	{
	
		this.homeView5 = homeView5;
	}

	/**
	 * @uml.property  name="h"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="cc:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
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
	 * @uml.property name="t"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="cc:com.CMPUT301W14T13.gpscommentlogger.view.ThreadView"
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

	
}
