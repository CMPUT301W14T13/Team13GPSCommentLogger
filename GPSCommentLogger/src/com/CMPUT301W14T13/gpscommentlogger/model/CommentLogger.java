package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;

/**
 * The model for the entire app to modify. It holds the root which contains the list
 * of topics, a comment list which displays every comment made in a topic, the user's
 * global username, a number which is used to grab topics from root, and adapters for updating the lists.
 * 
 * @author arweber
 *
 */
public class CommentLogger extends FModel<FView>
{
	private Root root = new Root();
	private ArrayList<Viewable> commentList = new ArrayList<Viewable>(); //the comment list to be displayed in a topic
	private int currentTopic;
	private boolean changed = false;
	private String currentUsername = "Anonymous";
	
	
	public void setCurrentUsername(String username){
		currentUsername = username;
	}
	
	public String getCurrentUsername(){
		return currentUsername;
	}
	
	
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
		notifyViews();
	}
	
	
	public void updateTopicChildren(ArrayList<Viewable> comments){
		commentList = comments;
		
	}
	
	/**
	 * updates the commentList to be displayed in a topic. For each topic child,
	 * it gets the children all the way down and adds them to the comment list.
	 */
	public void update(){
		commentList.clear();
		
		for (int i = 0; i < root.getChildren().get(currentTopic).getChildren().size(); i++){

			fillTopicChildren(root.getChildren().get(currentTopic).getChildren().get(i));
		}
		
		
	}
	
	
	/**
	 *
	 * This function takes in a topic child and then recursively goes down the child comment
	 * trees to fill a list containing every comment that can then be displayed. It first 
	 * gets the children of this comment and adds the comment that was last passed into the function.
	 * If the comment has children then it recursively calls itself to add them. If a comment
	 * has no children then it doesn't do anything more and moves on to the next comment.
	 * 
	 * @param comment  the comment whose children are being added
	 */
	public void fillTopicChildren(Viewable comment){

		ArrayList<Viewable> children = comment.getChildren();
		commentList.add(comment);
	
		if (children.size() != 0){
			
		
			for (int i = 0; i < children.size(); i++){
				
				fillTopicChildren(children.get(i));
				
			}
		
		}
		
	}
	
	public void flipChanged(){
		changed = !changed;
	}
	
	public boolean isChanged(){
		return changed;
	}
	
}
