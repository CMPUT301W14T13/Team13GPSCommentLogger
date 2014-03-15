package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.util.ArrayList;

import android.os.Message;
import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.results.MockResult;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;

public class ServerListener extends Thread
{
	private ClientController client;
	private ArrayList<Result> results;

	public ServerListener(ClientController clientController)
	{
		client = clientController;
		results = new ArrayList<Result>();
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

	}

}
