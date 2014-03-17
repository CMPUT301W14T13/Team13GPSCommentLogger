package com.CMPUT301W14T13.gpscommentlogger.model;

import android.app.Application;

/**
 * Modified from Abram Hindle's FillerCreep: https://github.com/abramhindle/FillerCreepForAndroid/blob/master/src/es/softwareprocess/fillercreep/FillerCreepApplication.java
 * 
 *
 */
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
