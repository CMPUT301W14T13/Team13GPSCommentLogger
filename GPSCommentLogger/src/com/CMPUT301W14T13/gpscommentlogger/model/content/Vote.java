package com.CMPUT301W14T13.gpscommentlogger.model.content;

import java.util.Date;


/**
 * Class for a vote object. Object keeps
 * track of date, and ID of user who
 * authored the vote and its value.
 *
 */
public class Vote
{
	private int value;
	private Date timestamp;
	private String androidID;
	private Topic t = new com.CMPUT301W14T13.gpscommentlogger.model.content.Topic();

	public Topic getT()
	{
		return t;
	}

	public void setT(Topic t)
	{
		this.t = t;
	}

	private Comment c = new com.CMPUT301W14T13.gpscommentlogger.model.content.Comment();

	public Comment getC()
	{
		return c;
	}

	public void setC(Comment c)
	{
		this.c = c;
	}
}
