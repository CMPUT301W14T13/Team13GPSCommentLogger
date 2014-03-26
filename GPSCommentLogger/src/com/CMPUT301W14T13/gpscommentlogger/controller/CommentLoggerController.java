package com.CMPUT301W14T13.gpscommentlogger.controller;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;


/**
 * Controller to modify our model
 * 
 * @author Austin
 *
 */
public class CommentLoggerController
{

	private CommentLogger cl = null;

	/**
	 * Initializes CommentLoggerController
	 * with default c1
	 * 
	 * @param cl
	 */
	public CommentLoggerController(CommentLogger cl) {
		this.cl = cl;

	}

	/**
	 * Function adds a comment to a topic
	 * 
	 * @param comment
	 */
	public void addTopicChild(Comment comment) {


		cl.getCurrentTopic().addChild(comment);
		cl.notifyViews();
	}

	/**
	 * Function adds topic to
	 * list of topics in root
	 * 
	 * @param topic
	 */
	public void addTopic(Topic topic) {

		cl.addTopic(topic);
	}


	public void updateCommentList(){
		cl.update();
		cl.notifyViews();
	}

	public void updateCurrentTopic(int position){
		cl.setCurrentTopic(position);
	}

	public void updateCurrentUsername(String username){
		cl.setCurrentUsername(username);
	}
	
	public void update(){
		cl.notifyViews();
	}
	
	
}
