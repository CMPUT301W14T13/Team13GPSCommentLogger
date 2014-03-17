package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;


import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;

/**
 * First screen of app, displays
 * sorted topics
 */
public class RootView {

	public RootView(Root root){
	
	}
	
	private ArrayList<Topic> topics = new ArrayList<Topic>();
	private Comment comment;

	          
    private void viewFavourites(){
    	
    }
    
    public void sortBy(SortParameter sort){
    	
    }
    

    //View comments that have been marked as "read later"
    private void viewReadLater(){
    	
    }
	
	
}
