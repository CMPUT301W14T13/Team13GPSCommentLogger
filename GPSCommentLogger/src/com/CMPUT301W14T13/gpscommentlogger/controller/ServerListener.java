package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.results.MockResult;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;

public class ServerListener extends Thread
{
	private ClientController client;
	private Handler handler;
	private ArrayList<Result> results;
	private HashMap<String, String> debuggingOutput; 

	public ServerListener(ClientController clientController, Handler handler)
	{
		client = clientController;
		results = new ArrayList<Result>();
		this.handler = handler;
	}

	@Override
	public void run()
	{
		while(true)
		{
			try{
				checkResult();
				doAction();
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
	
	public synchronized void addResult(Result result)
	{
		this.results.add(result);
		notify();
	}
	
	private synchronized void checkResult() throws InterruptedException
	{
		if (results.isEmpty()) wait();
	}
	
	private void doAction(){
		Result result = results.remove(0);
		
		Log.w("ClientController", "Result received");
		if(result instanceof MockResult)
		{
			switch(((MockResult) result).getType())
			{
				case BROWSE:
					Log.w("ClientController", "Mock Result received");
					MockResult mock = (MockResult)result;
					Viewable data = (Viewable)mock.getData();
					Message msg = new Message();
					msg.obj = data;
					Log.w("DebugMessage", "Message Sent");	
					client.handler.dispatchMessage(msg);
					break;
				case POST:
					Log.w("ClientController", "Mock Result received");
					mock = (MockResult)result;
					boolean success = Boolean.parseBoolean(mock.getData().toString());
					Log.w("ClientController", "Post result: " + success);
					break;
				default:
					throw new IllegalArgumentException("Illegal MockResult Type");
			}
		}
		
		//TODO: perform appropriate action based on result
		if(result instanceof ServerResult)
		{
			ServerResult serverResult = (ServerResult)result;
			if(((ServerResult) result).getId().equals("ROOT"))
			{
				Log.w("ServerListener","Test");
				Message msg = new Message();
				msg.obj = serverResult.getObj();
				handler.dispatchMessage(msg);
			}
		}

	}
	
	public String getResultByTaskID(String id)
	{
		String output = debuggingOutput.get(id);
		return (output == null)?"null":output;
	}

}
