package com.CMPUT301W14T13.gpscommentlogger;

import java.util.Date;

import android.graphics.Bitmap;




public class Comment
{

	private int ID;
	private String username;
	private Bitmap image;
	private boolean hasImage;
	private List<int>child_ID;
	private Date timestamp;
	private String commentText;
	
	private HashSet<String, Vote> votes;
		
	public Comment(){
		
	}
	
	
	
	
}
