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
	private HomeViewActivity homeView;

	/**
	 * Getter of the property <tt>homeView</tt>
	 * @return  Returns the homeView.
	 * @uml.property  name="homeView"
	 */
	public HomeViewActivity getHomeView()
	
	
	
	
	
	
	
	
	
	{
	
		return homeView;
	}

	/**
	 * Setter of the property <tt>homeView</tt>
	 * @param homeView  The homeView to set.
	 * @uml.property  name="homeView"
	 */
	public void setHomeView(HomeViewActivity homeView)
	
	
	
	
	
	
	
	
	
	{
	
		this.homeView = homeView;
	}

	/**
	 * @uml.property  name="homeView1"
	 * @uml.associationEnd  inverse="commentView1:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeViewActivity homeView1;

	/**
	 * Getter of the property <tt>homeView1</tt>
	 * @return  Returns the homeView1.
	 * @uml.property  name="homeView1"
	 */
	public HomeViewActivity getHomeView1()
	
	
	
	
	
	
	
	
	{
	
		return homeView1;
	}

	/**
	 * Setter of the property <tt>homeView1</tt>
	 * @param homeView1  The homeView1 to set.
	 * @uml.property  name="homeView1"
	 */
	public void setHomeView1(HomeViewActivity homeView1)
	
	
	
	
	
	
	
	
	{
	
		this.homeView1 = homeView1;
	}

	/**
	 * @uml.property  name="homeView2"
	 * @uml.associationEnd  aggregation="composite" inverse="commentView2:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeViewActivity homeView2;

	/**
	 * Getter of the property <tt>homeView2</tt>
	 * @return  Returns the homeView2.
	 * @uml.property  name="homeView2"
	 */
	public HomeViewActivity getHomeView2()
	
	
	
	
	
	
	
	{
	
		return homeView2;
	}

	/**
	 * Setter of the property <tt>homeView2</tt>
	 * @param homeView2  The homeView2 to set.
	 * @uml.property  name="homeView2"
	 */
	public void setHomeView2(HomeViewActivity homeView2)
	
	
	
	
	
	
	
	{
	
		this.homeView2 = homeView2;
	}

	/**
	 * @uml.property  name="homeView3"
	 * @uml.associationEnd  aggregation="composite" inverse="commentView3:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeViewActivity homeView3;

	/**
	 * Getter of the property <tt>homeView3</tt>
	 * @return  Returns the homeView3.
	 * @uml.property  name="homeView3"
	 */
	public HomeViewActivity getHomeView3()
	
	
	
	
	
	
	{
	
		return homeView3;
	}

	/**
	 * Setter of the property <tt>homeView3</tt>
	 * @param homeView3  The homeView3 to set.
	 * @uml.property  name="homeView3"
	 */
	public void setHomeView3(HomeViewActivity homeView3)
	
	
	
	
	
	
	{
	
		this.homeView3 = homeView3;
	}

	/**
	 * @uml.property  name="homeView4"
	 * @uml.associationEnd  aggregation="composite" inverse="commentView4:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeViewActivity homeView4;

	/**
	 * Getter of the property <tt>homeView4</tt>
	 * @return  Returns the homeView4.
	 * @uml.property  name="homeView4"
	 */
	public HomeViewActivity getHomeView4()
	
	
	
	
	
	{
	
		return homeView4;
	}

	/**
	 * Setter of the property <tt>homeView4</tt>
	 * @param homeView4  The homeView4 to set.
	 * @uml.property  name="homeView4"
	 */
	public void setHomeView4(HomeViewActivity homeView4)
	
	
	
	
	
	{
	
		this.homeView4 = homeView4;
	}

	/**
	 * @uml.property  name="homeView5"
	 * @uml.associationEnd  inverse="commentView5:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeViewActivity homeView5;

	/**
	 * Getter of the property <tt>homeView5</tt>
	 * @return  Returns the homeView5.
	 * @uml.property  name="homeView5"
	 */
	public HomeViewActivity getHomeView5()
	
	
	
	
	{
	
		return homeView5;
	}

	/**
	 * Setter of the property <tt>homeView5</tt>
	 * @param homeView5  The homeView5 to set.
	 * @uml.property  name="homeView5"
	 */
	public void setHomeView5(HomeViewActivity homeView5)
	
	
	
	
	{
	
		this.homeView5 = homeView5;
	}

	/**
	 * @uml.property  name="h"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="cc:com.CMPUT301W14T13.gpscommentlogger.view.HomeView"
	 */
	private HomeViewActivity h = new com.CMPUT301W14T13.gpscommentlogger.view.HomeViewActivity();

	/**
	 * Getter of the property <tt>h</tt>
	 * @return  Returns the h.
	 * @uml.property  name="h"
	 */
	public HomeViewActivity getH()
	
	
	
	{
	
		return h;
	}

	/**
	 * Setter of the property <tt>h</tt>
	 * @param h  The h to set.
	 * @uml.property  name="h"
	 */
	public void setH(HomeViewActivity h)
	
	
	
	{
	
		this.h = h;
	}

	/** 
	 * @uml.property name="t"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="cc:com.CMPUT301W14T13.gpscommentlogger.view.ThreadView"
	 */
	private TopicViewActivity t = new com.CMPUT301W14T13.gpscommentlogger.view.TopicViewActivity();

	/** 
	 * Getter of the property <tt>t</tt>
	 * @return  Returns the t.
	 * @uml.property  name="t"
	 */
	public TopicViewActivity getT()
	
	
	{
		return t;
	}

	/** 
	 * Setter of the property <tt>t</tt>
	 * @param t  The t to set.
	 * @uml.property  name="t"
	 */
	public void setT(TopicViewActivity t)
	
	
	{
		this.t = t;
	}


	
}
