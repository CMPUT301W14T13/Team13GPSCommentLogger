package com.CMPUT301W14T13.gpscommentlogger.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.ElasticSearchResponse;
import com.CMPUT301W14T13.gpscommentlogger.model.ElasticSearchSearchResponse;
import com.CMPUT301W14T13.gpscommentlogger.model.InterfaceSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


public class ServerController extends Controller
{
	//store references to auxiliary classes
	private ClientDispatcher dispatcher; 
	private ClientListener listener;
	
	//store reference to client
	private ClientController client;
	
	//store list of tasks
	private ArrayList<Task> tasks;
	
	//store reference to output window for debugging
	protected TextView debuggingWindow;
	protected Handler handler;
	
	//store web communication information
	private static String WEB_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t13/viewables/";
	private static ServerContext context = new ServerContext(WEB_URL);
	
	public ServerController(Handler handler, TextView debuggingWindow)
	{
		isInit = false;
		this.handler = handler;
		this.debuggingWindow = debuggingWindow;
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public void init()
	{
		if (isInit) return;
		listener = new ClientListener(this);
		listener.start();
		isInit = true;

	}
	
	public void setPipes()
	{
		dispatcher = new ClientDispatcher(client.getListener());
	}

	@Override
	public void run()
	{
		try {
			writeDebuggingMessage("Server Running");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(true){
			try{
				//First check if there is something to do	
				checkTasks();
				//Do the oldest task in queue
				Result result = doTask();
				//Handle edge cases and debugging info here
				processResult(result);
			}
			catch(InterruptedException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	protected synchronized void checkTasks() throws InterruptedException
	{
		if(tasks.isEmpty()) wait();
	}
	
	public synchronized void addTask(Task task) throws InterruptedException
	{
		tasks.add(task);
		writeDebuggingMessage("Task added to Server");
		notify();
	}
	
	protected Result doTask()
	{
		//TODO: Bitmap serialization?
		ServerTask currentTask = (ServerTask)tasks.remove(0);
		
		return currentTask.executeOnServer(context);
	}

	protected void processResult(Result result)
	{
		dispatcher.dispatch((ServerResult)result);
	}

	public void setClient(ClientController client)
	{
		this.client = client;
	}
	
	private void writeDebuggingMessage(String message) throws InterruptedException
	{
		if (debuggingWindow == null) return;
		//Message msg = handler.obtainMessage(0, message);
		Message msg = new Message();
		msg.obj = message;
		Log.w("DebugMessage", "Message Sent");
		//msg.sendToTarget();	
		handler.dispatchMessage(msg);
	}
	
	public ClientListener getListener(){
		return listener;
	}

}
