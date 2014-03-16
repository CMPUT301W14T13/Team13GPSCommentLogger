package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;


/*
 * controller that changes the list of comments being displayed for a topic
 */
public class CommentModelList
{
	//private Topic currentTopic;
	private ArrayList<Viewable> commentList;
	private boolean changed = false;
	private CommentAdapter adapter;
	private CommentLogger cl = null;
	
	
	public CommentModelList(CommentLogger cl) {
		commentList = new ArrayList<Viewable>();
		this.cl = cl;
		
	}
	
	
	public void addTopicChild(Comment comment) {
		
		
		cl.getCurrentTopic().addChild(comment);
		
		//ElasticSearchOperations.pushPicPostModel(picPost);
	}
	
	public void addTopic(Topic topic) {
		
		
		cl.getRoot().addChild(topic);
		
		//ElasticSearchOperations.pushPicPostModel(picPost);
	}

	public boolean isChanged(){
		return changed;
	}
	
	public void flipChanged(){
		changed = !changed;
	}
	
	//creates a list to display in the comments section of the topic
	
	
	
	public void setAdapter(CommentAdapter adapter) {
		cl.setAdapter(adapter);
	}
	
	/*public void updateCurrentTopic(){
		cl.setCurrentTopic(topic);
	}*/
	
	public void updateCommentList(){
		cl.update();
	}
	
	
}
