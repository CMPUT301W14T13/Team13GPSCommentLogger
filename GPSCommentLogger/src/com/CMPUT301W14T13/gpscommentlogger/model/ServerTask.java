package com.CMPUT301W14T13.gpscommentlogger.model;


public class ServerTask implements Task
{
	private ServerTaskCode code;
	private Viewable obj;

	public ServerTaskCode getCode() {
		return code;
	}

	public void setCode(ServerTaskCode code) {
		this.code = code;
	}

	public Viewable getObj() {
		return obj;
	}

	public void setObj(Viewable obj) {
		this.obj = obj;
	}
}
