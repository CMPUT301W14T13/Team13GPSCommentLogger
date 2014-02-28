package com.CMPUT301W14T13.gpscommentlogger.model;


public class ServerResult implements Result
{
	private String content;

	public void setContent(String output) {
		content = output;
	}
	
	public String getContent(){
		return content;
	}

}
