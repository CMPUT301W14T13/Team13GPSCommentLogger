package com.CMPUT301W14T13.gpscommentlogger.model;


public class ClientTask implements Task
{	
	private ClientTaskCode taskCode;
	private boolean isTaskCodeSet = false;
	
	private ClientTaskCode sourceCode;
	private boolean isSourceCodeSet = false;
	
	private String obj;
	
	public ClientTask()
	{
		
	}
	
	public boolean setTaskCode(ClientTaskCode code)
	{
		if(isTaskCodeSet)return false;
		
		this.taskCode = code;
		isTaskCodeSet = true;
		return true;
	}
	
	public ClientTaskCode getTaskCode()
	{
		return taskCode;
	}
	
	public boolean setSourceCode(ClientTaskCode code)
	{
		if(isSourceCodeSet)return false;
		
		this.sourceCode = code;
		isSourceCodeSet = true;
		return true;
	}
	
	public ClientTaskCode getSourceCode()
	{
		return sourceCode;
	}
	
	public void setObj(String obj)
	{
		this.obj = obj;
	}
	
	public String getObj()
	{
		return obj;
	}
}
