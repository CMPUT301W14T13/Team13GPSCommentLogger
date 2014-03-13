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
	private Topic topic;
	private ArrayList<Viewable> commentList;
	
	
	public CommentModelList(Topic topic) {
		this.commentList = new ArrayList<Viewable>();
		this.topic = topic;
	}
	
	
	public void addPicPost(Comment comment) {
		//PicPostModel picPost = new PicPostModel(pic, text, timestamp);
		
		this.commentList.add(comment);
		//this.adapter.notifyDataSetChanged();
		
		//ElasticSearchOperations.pushPicPostModel(picPost);
	}
	
	
	public void clearComments(){
		this.commentList.clear();
	}
	
	public ArrayList<Viewable> getList() {
		return commentList;
	}
	
	public Viewable getComment(int row){
		return commentList.get(row);
	}
	
	//creates a list to display in the comments section of the topic
	public void update(){
		this.commentList.clear();
		
		for (int i = 0; i < topic.getChildren().size(); i++){

			fillTopicChildren(topic.getChildren().get(i));
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
