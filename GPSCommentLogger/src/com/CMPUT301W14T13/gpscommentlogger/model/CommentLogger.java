package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;

/**
 * The model for the entire app to modify
 * 
 * @author arweber
 *
 */
public class CommentLogger extends FModel<FView>
{
	private Root root = new Root();
	//private ArrayList<Viewable> topicList = new ArrayList<Viewable>(); //the list of topics to display
	private ArrayList<Viewable> commentList = new ArrayList<Viewable>(); //the comment list to be displayed in a topic
	private int currentTopic;
	private CommentAdapter commentAdapter;
	private CustomAdapter customAdapter;
	private boolean changed = false;
	
	
	
	
	public ArrayList<Viewable> getCommentList(){
		
		update();
		return commentList;
	}
	
	
	public ArrayList<Viewable> getTopicChildren(){
		return getCurrentTopic().getChildren();
		
	}
	
	
	public Root getRoot(){
		return root;
	}
	
	
	public void setCurrentTopic(int num){
		currentTopic = num;
	}
	
	
	public ArrayList<Viewable> getTopics(){
		return root.getChildren();
	}
	
	
	public Topic getCurrentTopic(){
		return (Topic) root.getChildren().get(currentTopic);
	}
	
	
	public void addComment(Comment comment){
		root.getChildren().get(currentTopic).addChild(comment);
		this.commentAdapter.notifyDataSetChanged();
	}
	
	
	public void updateTopicChildren(ArrayList<Viewable> comments){
		commentList = comments;
		//((Topic) root.getChildren().get(currentTopic)).setChildren(comments);
	}
	
	
	public void update(){
		commentList.clear();
		
		for (int i = 0; i < root.getChildren().get(currentTopic).getChildren().size(); i++){

			fillTopicChildren(root.getChildren().get(currentTopic).getChildren().get(i));
		}
		
		this.commentAdapter.notifyDataSetChanged();
	}
	
	
	/*
	 * This function takes in a topic child and then recursively goes down the child comment
	 * trees to fill a list containing every comment that can then be displayed
	 */
	public void fillTopicChildren(Viewable comment){

		//ArrayList<Viewable> comments = list;
		ArrayList<Viewable> children = comment.getChildren();
		//Comment child = (Comment) comment;
		
		commentList.add(comment);
		//System.out.println(comment.getCommentText() + "  " + child.getIndentLevel());
		if (children.size() != 0){
			
		
			for (int i = 0; i < children.size(); i++){
				
				fillTopicChildren(children.get(i));
				
			}
		
		}
		
	}
	
	
	public void setCommentAdapter(CommentAdapter adapter) {
		commentAdapter = adapter;
	}
	
	public void setCustomAdapter(CustomAdapter adapter) {
		customAdapter = adapter;
	}
	
	public void notifyCustomAdapter(){
		customAdapter.notifyDataSetChanged();
	}
	
	public void flipChanged(){
		changed = !changed;
	}
	
	public boolean isChanged(){
		return changed;
	}
	
}
