package com.CMPUT301W14T13.gpscommentlogger.model.content;

import java.util.ArrayList;

/**
 * This is the root view that will display
 * all top level comments.
 */
public class Root {

	private String ID;
	private ArrayList<Topic> topics;
	
	public Root()
	{
		ID = "ROOT";
		topics = new ArrayList<Topic>();
	}

	public Root(String ID)
	{
		this.ID = ID;
		topics = new ArrayList<Topic>();
	}

	public ArrayList<Topic> getChildren() {
		return this.topics;
	};
	
	/**
	 * Sets the children of the parent Viewable object.
	 * @param childViewables the children to add to the parent Viewable.
	 */
	public void setChildren(ArrayList<Topic> childViewables) {
		this.topics = childViewables;
	}
	
	/**
	 * Adds a child within the Viewable object.
	 * @param The Viewable to add as a child.
	 */
	public void addChild(Topic post) {
		/* Consider adding this in a controller */
		topics.add(post);
	}
	
}
