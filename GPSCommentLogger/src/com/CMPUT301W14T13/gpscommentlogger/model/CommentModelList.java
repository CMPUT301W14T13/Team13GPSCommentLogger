package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.widget.ArrayAdapter;


/*
 * Model for the list of comments being displayed in a topic
 */
public class CommentModelList
{
	private static Topic currentTopic;
	private static ArrayList<Viewable> commentList;
	private boolean changed = false;
	
	public CommentModelList(Topic topic) {
		commentList = new ArrayList<Viewable>();
		currentTopic = topic;
	}
	
	
	public static void addTopicChild(Comment comment) {
		
		
		currentTopic.getChildren().add(comment);
		//this.adapter.notifyDataSetChanged();
		
		//ElasticSearchOperations.pushPicPostModel(picPost);
	}
	
	
	public void clearComments(){
		commentList.clear();
	}
	
	public static ArrayList<Viewable> getList() {
		return commentList;
	}
	
	public Viewable getComment(int row){
		return commentList.get(row);
	}
	
	public boolean isChanged(){
		return changed;
	}
	
	public void flipChanged(){
		this.changed = !changed;
	}
	
	//creates a list to display in the comments section of the topic
	public void update(){
		commentList.clear();
		
		for (int i = 0; i < currentTopic.getChildren().size(); i++){

			fillTopicChildren(currentTopic.getChildren().get(i));
		}
			
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
	
	
	/*public void setAdapter(ArrayAdapter<PicPostModel> adapter) {
		this.adapter = adapter;
	}*/
	
	
	
	
	
	
}
