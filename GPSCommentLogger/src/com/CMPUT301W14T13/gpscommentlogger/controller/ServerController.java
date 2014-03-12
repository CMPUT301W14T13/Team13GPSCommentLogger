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
		Result out = new ServerResult();
		
		switch(currentTask.getCode())
		{
			case INITIALIZE:
				processInitializationRequest(currentTask, (ServerResult)out);
				break;
			case DELETE:
				throw new UnsupportedOperationException("Delete from server has not been implemented.");
			case INSERT:
				processInsertRequest(currentTask,(ServerResult)out);
				break;
			case SEARCH:
				processSearchRequest(currentTask,(ServerResult)out);
				break;
			case UPDATE:
				try
				{
					processUpdateRequest(currentTask,(ServerResult)out);
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
	
	private void processInitializationRequest(ServerTask currentTask,
			ServerResult out)
	{
		//first we delete everything that already exists
		deleteAll(out);
		
		//next, we add the new object
		postNewViewable(currentTask, out);		
	}

	//Modified from https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	// This method adds a given Viewable to server
	private void processInsertRequest(ServerTask currentTask, ServerResult result) {

		//searchTerm should have the parent's ID
		//we need to get the ESID to process an update request
		String esID = findESIDByID(currentTask);
		
		//next, we add the new object
		postNewViewable(currentTask, result);
		
		//finally, we update the parent object
		addToList(esID, "comments", currentTask, result);
		
	}
	
	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	//This method searches for a Viewable based on its ID field
	private void processSearchRequest(ServerTask currentTask, ServerResult result) {
		
		retrieveViewable(currentTask, result);
		
	}
	
	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	//This method searches for the ID and updates
	//the version in the elasticSearch versioning system 
	//to the new state provided by the client
	private void processUpdateRequest(ServerTask currentTask, ServerResult result) {
		
		//searchTerm should have the viewable's ID
		//we need to get the ESID to process an update request
		String esID = findESIDByID(currentTask);
		
		//next, we update the viewable
		updateField(esID,currentTask, result);
	}
	
	private void deleteAll(ServerResult result){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpDelete request = new HttpDelete(WEB_URL + "_all");
		
		try
		{
			request.setHeader("Accept","application/json");
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = result.getContent();
			output += "\n" + reader.readLine();
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

	}
	
	private void updateField(String id, ServerTask currentTask, ServerResult result){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");

		try
		{
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = getFieldJson(gson,currentTask);
			
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = "{\"script\" : \"ctx._source." + getFieldString(currentTask) + " = field\", " +
					"\"params\" : {" +
				        "\"field\" : \""+ jsonString +"\"" +
				    "}";
			StringEntity stringentity = new StringEntity(query);

			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = result.getContent();
			output += "\n" + reader.readLine();
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

	}
	
	private String getFieldJson(Gson gson, ServerTask currentTask){
		switch(currentTask.getUpdateCode())
		{
			case TEXT:
				return gson.toJson(currentTask.getObj().getCommentText());
			case LOCATION:
				return gson.toJson(currentTask.getObj().getGPSLocation());
			case IMAGE:
				return gson.toJson(currentTask.getObj().getImage());
			default:
				throw new IllegalArgumentException("Server Error: Illegal UpdateCode.");
				
		}
	}
	
	private String getFieldString(ServerTask currentTask){
		switch(currentTask.getUpdateCode())
		{
			case TEXT:
				return "commentText";
			case LOCATION:
				return "GPSLocation";
			case IMAGE:
				return "image";
			default:
				throw new IllegalArgumentException("Server Error: Illegal UpdateCode.");
		}
	}
	
	private void addToList(String id, String listName, ServerTask currentTask, ServerResult result){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");

		try
		{
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = gson.toJson(currentTask.getObj());
			
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = "{\"script\" : \"ctx._source." + listName + "+= viewable\", " +
					"\"params\" : {" +
				        "\"viewable\" : \""+ jsonString +"\"" +
				    "}";
			StringEntity stringentity = new StringEntity(query);

			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = result.getContent();
			output += "\n" + reader.readLine();
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

	}
	
	private void postNewViewable(ServerTask currentTask, ServerResult result)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost autogenerates keys
		HttpPut request = new HttpPut(WEB_URL);

		try
		{
			request.setHeader("Accept","application/json");
			
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = gson.toJson(currentTask.getObj());
			
			//proceeding with elasticSearch request
			request.setEntity(new StringEntity(jsonString));
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());

			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = result.getContent();
			output += "\n" + reader.readLine();
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
	}
	
	private String findESIDByID(ServerTask currentTask)
	{
		String id = "";
		
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		try
		{
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);

			//Execute search
			HttpResponse response = client.execute(request);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearch",status);

			//Get ID of Viewable as it exists in the elastic search system
			String json = getEntityContent(response);
			
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Viewable>>(){}.getType();
			ElasticSearchSearchResponse<Viewable> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			System.err.println(esResponse);
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<Viewable> r : esResponse.getHits()) {
				id = r.getESID();
				Log.w("ElasticSearch",id);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return id;
	}
	
	private void retrieveViewable(ServerTask currentTask, ServerResult result)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		try
		{
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);

			//Execute elasticSearch request
			HttpResponse response = client.execute(request);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearch",status);


			String json = getEntityContent(response);

			//Set the matching viewable as the object of the ServerResult
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
	}
	
	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	//This method searches for the ID and updates
	//the version in the elasticSearch versioning system 
	//to the new state provided by the client
	public ServerResult processUpdateRequest(ServerTask currentTask) throws ClientProtocolException, IOException {
		ServerResult result = new ServerResult();

		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		try
		{
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);

			//Execute search
			HttpResponse response = client.execute(request);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearch",status);

			//Get ID of Viewable as it exists in the elastic search system
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

			//overwrite current version with the id and _create tag
			HttpPost updateRequest = new HttpPost(WEB_URL + id + "/_create");
			updateRequest.setHeader("Accept","application/json");
			String jsonString = gson.toJson(currentTask.getObj());
			updateRequest.setEntity(new StringEntity(jsonString));

			HttpResponse updateResponse = client.execute(request);
			Log.w("ElasticSearch", updateResponse.getStatusLine().toString());

			HttpEntity entity = updateResponse.getEntity();

			//Entering response into the ServerResult to be returned to client
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
		dispatcher.dispatch(task);
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
