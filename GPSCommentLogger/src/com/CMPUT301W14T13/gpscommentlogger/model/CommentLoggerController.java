package com.CMPUT301W14T13.gpscommentlogger.model;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
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


	public CommentLoggerController(CommentLogger cl) {
		this.cl = cl;

	}


	public void addTopicChild(Comment comment) {


		cl.getCurrentTopic().addChild(comment);

	}

	public void addTopic(Topic topic) {


		cl.getRoot().addChild(topic);
		cl.notifyCustomAdapter();

	}


	public void setCommentAdapter(CommentAdapter adapter) {
		cl.setCommentAdapter(adapter);
	}

	public void setCustomAdapter(CustomAdapter adapter) {
		cl.setCustomAdapter(adapter);
	}

	public void updateCommentList(){
		cl.update();
	}

	public void updateCurrentTopic(int position){
		cl.setCurrentTopic(position);
	}

}
