package com.CMPUT301W14T13.gpscommentlogger.model.results;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;


public class ServerResult implements Result
{
	private String content;
	private Viewable obj;
	
	private String id;
	private boolean isIDSet = false;

	public String getId() {
		return id;
	}

	public void setId(String id) throws InterruptedException {
		if (isIDSet)throw new InterruptedException("Error: attempt to alter Result id");
		this.id = id;
	}
	
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
