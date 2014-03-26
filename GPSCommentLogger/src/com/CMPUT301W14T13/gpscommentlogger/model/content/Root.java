package com.CMPUT301W14T13.gpscommentlogger.model.content;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;

/**
 * This is the root view that will display
 * all top level comments.
 */
public class Root extends Viewable{
	
	public Root()
	{
		super("ROOT");
	}
	
	public Root(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		super(ID, username, picture, timestamp, commentText);
	}
	

	public ArrayList<Viewable> getChildren() {
		return this.childPosts;
	};
	
	/**
	 * Sets the children of the parent Viewable object.
	 * @param childViewables the children to add to the parent Viewable.
	 */
	public void setChildren(ArrayList<Viewable> childViewables) {
		this.childPosts = childViewables;
	}
	
	/**
	 * Adds a child within the Viewable object.
	 * @param The Viewable to add as a child.
	 */
	public void addChild(Viewable post) {
		/* Consider adding this in a controller */
		this.childPosts.add(post);
	}
	
}
