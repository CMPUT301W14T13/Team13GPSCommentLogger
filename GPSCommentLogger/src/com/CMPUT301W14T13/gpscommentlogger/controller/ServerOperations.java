package com.CMPUT301W14T13.gpscommentlogger.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.model.ElasticSearchResponse;
import com.CMPUT301W14T13.gpscommentlogger.model.ElasticSearchSearchResponse;
import com.CMPUT301W14T13.gpscommentlogger.model.InterfaceSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.ServerTask;
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
	
	public static void deleteAll(ServerResult result, String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		HttpClient client = new DefaultHttpClient();
		HttpDelete request = new HttpDelete(WEB_URL);
		
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
	
	public static void updateField(String id, ServerTask currentTask, ServerResult result, String fieldName, String newJson, String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");

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
	
	
	
	public static void addToList(String id, String listName, ServerTask currentTask, ServerResult result, String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeHierarchyAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");

		try
		{
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = gson.toJson(currentTask.getObj());
			Log.w("JsonTest",jsonString);
			
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = "{\"script\" : \"ctx._source.CLASS_DATA." + listName + "+= viewable\", " +
					"\"params\" : {" +
				        "\"viewable\" : "+ jsonString +"" +
				    "}}";
			StringEntity stringentity = new StringEntity(query);

			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);
			HttpResponse response = client.execute(request);
			Log.w("ElasticSearchADDLIST", response.getStatusLine().toString());
			
			//Entering response into the ServerResult to be returned to client
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String output = result.getContent();
			output += "\n" + reader.readLine();
			while(output != null)
			{
				Log.w("ElasticSearchADDLIST", output);
				output = reader.readLine();
			}
			result.setContent(output);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public static void postNewViewable(ServerTask currentTask, ServerResult result, String WEB_URL)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeHierarchyAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost autogenerates keys
		HttpPost request = new HttpPost(WEB_URL);

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
			String output = result.getContent();
			output += "\n" + reader.readLine();
			while(output != null)
			{
				Log.w("ElasticSearchPOSTNEW", output);
				output = reader.readLine();
			}
			result.setContent(output);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String findESIDByID(ServerTask currentTask, String WEB_URL)
	{
		String id = "";
		
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
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
	
	public static void retrieveViewable(ServerTask currentTask, ServerResult result, String WEB_URL)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
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
	public static ServerResult processUpdateRequest(ServerTask currentTask, String WEB_URL) throws ClientProtocolException, IOException {
		ServerResult result = new ServerResult();

		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
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
