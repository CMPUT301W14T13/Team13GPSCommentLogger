package com.CMPUT301W14T13.gpscommentlogger.model;

import java.util.ArrayList;

public class MockResult implements Result{

	private Viewable data;
	
	public MockResult(Viewable data)
	{
		this.data = data;
	}
	
	public Viewable getData()
	{
		return data;
	}
	
}
