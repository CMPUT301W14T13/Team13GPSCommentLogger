package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;

public class MockResult implements Result{

	private Object data;
	private MockResultType type;
	
	public MockResult(Object b, MockResultType type)
	{
		this.data = b;
		this.type = type;
	}
	
	public Object getData()
	{
		return data;
	}
	
	public MockResultType getType()
	{
		return type;
	}
	
}
