package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.model.BitmapSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.ElasticSearchResponse;
import com.CMPUT301W14T13.gpscommentlogger.model.ElasticSearchSearchResponse;
import com.CMPUT301W14T13.gpscommentlogger.model.ViewableSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ServerOperations {

	private static ServerOperations instance = new ServerOperations();
	
	private ServerOperations() {
		
	}

	public ServerOperations getInstance()
	{
		return instance;
	}
	
	public static String deleteAll(String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new ViewableSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		HttpClient client = new DefaultHttpClient();
		HttpDelete request = new HttpDelete(WEB_URL);
		
		String returnValue = "";
		
		try
		{
			request.setHeader("Accept","application/json");
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = reader.readLine();
			returnValue = output;
			while(output != null)
			{
				Log.w("ElasticSearchDELETE", output);
				returnValue += "\n" + output;
				output = reader.readLine();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static String updateField(String id, Task currentTask, String fieldName, String newJson, String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new ViewableSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");
		
		String returnValue = "";
		
		try
		{
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = "{\"script\" : \"ctx._source." + fieldName + " = field\", " +
					"\"params\" : {" +
				        "\"field\" : \""+ newJson +"\"" +
				    "}}";
			StringEntity stringentity = new StringEntity(query);

			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearch", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = reader.readLine();
			returnValue = output;
			while(output != null)
			{
				Log.w("ElasticSearchUPDATE", output);
				returnValue += "\n" + output;
				output = reader.readLine();
			}	
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	
	
	public static String addToList(String id, String listName, Task currentTask, String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeHierarchyAdapter(Viewable.class, new ViewableSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");

		String returnValue = "";
		
		try
		{
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = currentTask.getObj().getID();
			
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = "{\"script\" : \"ctx._source.CLASS_DATA." + listName + "+= viewable\", " +
					"\"params\" : {" +
				        "\"viewable\" : \""+ jsonString +"\"" +
				    "}}";
			StringEntity stringentity = new StringEntity(query);

			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearchADDLIST", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = reader.readLine();
			returnValue = output;
			while(output != null)
			{
				Log.w("ElasticSearchADDTOLIST", output);
				returnValue += "\n" + output;
				output = reader.readLine();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static String postNewViewable(Task currentTask, String WEB_URL)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeHierarchyAdapter(Viewable.class, new ViewableSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost autogenerates keys
		HttpPost request = new HttpPost(WEB_URL);
		String returnValue = "";
		try
		{
			request.setHeader("Accept","application/json");
			
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = gson.toJson(currentTask.getObj());
			
			//proceeding with elasticSearch request
			request.setEntity(new StringEntity(jsonString));
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearchPOSTNEW", response.getStatusLine().toString());

			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = reader.readLine();
			returnValue = output;
			while(output != null)
			{
				Log.w("ElasticSearch", output);
				returnValue += "\n" + output;
				output = reader.readLine();		
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static String findESIDByID(Task currentTask, String WEB_URL)
	{
		String id = "";
		
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new ViewableSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
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
			Log.w("ElasticSearchFINDES",status);

			//Get ID of Viewable as it exists in the elastic search system
			String json = getEntityContent(response);
			
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Viewable>>(){}.getType();
			ElasticSearchSearchResponse<Viewable> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			System.err.println(esResponse);
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<Viewable> r : esResponse.getHits()) {
				id = r.getESID();
				Log.w("ElasticSearchFINDES",id);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static Viewable retrieveViewable(Task currentTask, String WEB_URL)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new ViewableSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		try
		{
			Log.w("ESTest",currentTask.getSearchTerm());
			Log.w("ESTest",WEB_URL);
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
			Log.w("ESTEST", json);

			//Set the matching viewable as the object of the ServerResult
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Viewable>>(){}.getType();
			ElasticSearchSearchResponse<Viewable> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new ViewableSerializer()).create();
			for(Viewable each : esResponse.getSources())
			{
				System.err.println(gson2.toJson(each));
			}
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<Viewable> r : esResponse.getHits()) {
				Viewable output = r.getSource();
				Log.w("ElasticSearch",(output.getID()));
				return output;
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	

	//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
	
	/**
	 * get the http response and return json string
	 */
	private static String getEntityContent(HttpResponse response) throws IOException {
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
}
