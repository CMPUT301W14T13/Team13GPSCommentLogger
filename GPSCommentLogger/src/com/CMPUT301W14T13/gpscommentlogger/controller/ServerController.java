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
import org.apache.http.client.methods.HttpPost;
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
import com.CMPUT301W14T13.gpscommentlogger.model.ServerResult;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.Task;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;
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
	private static String WEB_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t13/";

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
		
		dispatcher = new ClientDispatcher(client);
		listener = new ClientListener(this);
		listener.start();
		isInit = true;

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
		ServerTask currentTask = (ServerTask)tasks.remove(0);
		Result out = new ServerResult();
		
		switch(currentTask.getCode())
		{
			case DELETE:
				throw new UnsupportedOperationException("Delete from server has not been implemented.");
			case INSERT:
				out = processInsertRequest(currentTask);
				break;
			case SEARCH:
				out = processSearchRequest(currentTask);
				break;
			case UPDATE:
				try
				{
					out = processUpdateRequest(currentTask);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				break;
			default:
				throw new IllegalArgumentException("This should never happen. ServerTaskCode error!!!");
		}
		
		return out;
	}
	
	//Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	
	private ServerResult processInsertRequest(ServerTask currentTask) {
		ServerResult result = new ServerResult();

		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL);

		try
		{
			request.setHeader("Accept","application/json");
			String jsonString = gson.toJson(currentTask.getObj());
			request.setEntity(new StringEntity(jsonString));

			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = reader.readLine();
			while(output != null)
			{
				Log.w("ElasticSearch", output);
				output = reader.readLine();
			}
			
			result.setContent(output);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	
	private ServerResult processSearchRequest(ServerTask currentTask) {
		ServerResult result = new ServerResult();

		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		try
		{
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);


			HttpResponse response = client.execute(request);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearch",status);


			String json = getEntityContent(response);


			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Viewable>>(){}.getType();
			ElasticSearchSearchResponse<Viewable> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			System.err.println(esResponse);
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<Viewable> r : esResponse.getHits()) {
				Viewable output = r.getSource();
				Log.w("ElasticSearch",(output.getID()));
				result.setObj(output);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	
	public ServerResult processUpdateRequest(ServerTask currentTask) throws ClientProtocolException, IOException {
		ServerResult result = new ServerResult();

		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		try
		{
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);


			HttpResponse response = client.execute(request);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearch",status);


			String json = getEntityContent(response);
			String id = "";
			
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Viewable>>(){}.getType();
			ElasticSearchSearchResponse<Viewable> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			System.err.println(esResponse);
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<Viewable> r : esResponse.getHits()) {
				id = r.getESID();
				Log.w("ElasticSearch",id);
			}


			HttpPost updateRequest = new HttpPost(WEB_URL + "/" + id + "/_create");
			updateRequest.setHeader("Accept","application/json");
			String jsonString = gson.toJson(currentTask.getObj());
			updateRequest.setEntity(new StringEntity(jsonString));

			HttpResponse updateResponse = client.execute(request);
			Log.w("ElasticSearch", updateResponse.getStatusLine().toString());

			HttpEntity entity = updateResponse.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = reader.readLine();
			while(output != null)
			{
				Log.w("ElasticSearch", output);
				output = reader.readLine();
			}
			
			result.setContent(output);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}	

	
	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	
	/**
	 * get the http response and return json string
	 */
	String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		Log.w("EntityGrabber","Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			Log.w("EntityGrabber",output);
			json += output;
		}
		Log.w("EntityGrabber","JSON:"+json);
		return json;
	}


	protected void processResult(Result result)
	{
		client.registerResult(result);
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
	

}
