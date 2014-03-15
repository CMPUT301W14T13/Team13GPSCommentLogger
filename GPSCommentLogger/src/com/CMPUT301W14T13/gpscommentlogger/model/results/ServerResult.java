package com.CMPUT301W14T13.gpscommentlogger.model.results;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;


public class ServerResult implements Result
{
	private String content;
	private Viewable obj;

	public void setContent(String output) {
		content = output;
	}
	
	public String getContent(){
		return content;
	}

	public Viewable getObj() {
		return obj;
	}

	public void setObj(Viewable obj) {
		this.obj = obj;
	}

}
