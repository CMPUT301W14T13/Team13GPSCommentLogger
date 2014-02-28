package com.CMPUT301W14T13.gpscommentlogger.model;


public class ClientTask implements Task
{	
	private ClientTaskTaskCode taskCode;
	private boolean isTaskCodeSet = false;
	
	private ClientTaskSourceCode sourceCode;
	private boolean isSourceCodeSet = false;
	
	private Object obj;
	
	public ClientTask()
	{
		
	}
	
	public boolean setTaskCode(ClientTaskTaskCode code)
	{
		if(isTaskCodeSet)return false;
		
		this.taskCode = code;
		isTaskCodeSet = true;
		return true;
	}
	
	public ClientTaskTaskCode getTaskCode()
	{
		return taskCode;
	}
	
	public boolean setSourceCode(ClientTaskSourceCode code)
	{
		if(isSourceCodeSet)return false;
		
		this.sourceCode = code;
		isSourceCodeSet = true;
		return true;
	}
	
	public ClientTaskSourceCode getSourceCode()
	{
		return sourceCode;
	}
	
	public void setObj(Object obj)
	{
		this.obj = obj;
	}
	
	public Object getObj()
	{
		return obj;
	}
}
