package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;




public class Comment
{

	private int ID;
	private String username;
	private Bitmap image;
	private boolean hasImage;
	private List<Integer>child_ID;
	private Date timestamp;
	private String commentText;
	
	private HashMap<String, Vote> votes;
		
	public Comment(){
		
	}
	
	
	
	
}
