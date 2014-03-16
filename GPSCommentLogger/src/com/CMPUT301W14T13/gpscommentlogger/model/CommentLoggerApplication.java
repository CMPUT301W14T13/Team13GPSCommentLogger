package com.CMPUT301W14T13.gpscommentlogger.model;

import android.app.Application;


public class CommentLoggerApplication extends Application
{

	
	// Singleton
	transient private static CommentLogger commentLogger = null;

	static public CommentLogger getCommentLogger() {
		if (commentLogger == null) {
			commentLogger = new CommentLogger();
		}
		return commentLogger;
	}

	    
	
}
