package cmput301w14t13.project.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;
import cmput301w14t13.project.auxilliary.tools.Escaper;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.tasks.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Posting to server or grabing from server, or editing objects
 * in the server. 
 * 
 * @author nsd
 *
 */
public class ElasticSearchOperations {


	private static ElasticSearchOperations instance = new ElasticSearchOperations();
	
	private ElasticSearchOperations() {
		
	}

	public ElasticSearchOperations getInstance()
	{
		return instance;
	}
	/**
	 * Deletes Everything from the server, then returns a string
	 * for determining whether or not everything has been deleted
	 * Used when Starting or Restarting the Server
	 * 
	 * @param WEB_URL The Servers url
	 * @return returnValue a string of what is left in the server
	 */
	public static String deleteAll(String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
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
	/**
	 * Searches the server for the Correct CommentTreeElement,which was converted to a jsonObject, using 
	 * a unique ID, id, then updates the field indicated by , fieldName. 
	 * 
	 * Used when a user makes a change to an already existing CommentTreeElement 
	 * 
	 * @param id a unique ID given to CommentTreeElements for searching purposes
	 * @param currentTask  NOT USED 
	 * @param fieldName the field of CommentTreeElement to be updated
	 * @param newJson NOT SURE
	 * @param WEB_URL the Servers url
	 * @return returnValue used to determine success
	 */
	public static String updateField(String id, Task currentTask, String fieldName, String newJson, String WEB_URL){
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");
		
		String returnValue = "";
		
		HttpClient refreshClient = new DefaultHttpClient();
		HttpPost refresh = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t13/_refresh");
		try
		{
			HttpResponse response = refreshClient.execute(refresh);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearchRefresh",status);
			
			
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			Log.w("EscapeTest", newJson);
			String query = "{\"script\" : \"ctx._source.CLASS_DATA." + fieldName + " = field\", " +
					"\"params\" : {" +
				        "\"field\" : \""+ newJson + "\"" +
				    "}}";
			
			StringEntity stringentity = new StringEntity(query);

			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);
			response = client.execute(request);
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
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + id + "/_update");

		String returnValue = "";
		
		HttpClient refreshClient = new DefaultHttpClient();
		HttpPost refresh = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t13/_refresh");
		try
		{
			HttpResponse response = refreshClient.execute(refresh);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearchRefresh",status);
			
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
			response = client.execute(request);
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
	/**
	 * Posts a CommentTreeElement to the Server
	 * Used when the user creates a new CommentTreeElement
	 * 
	 * @param currentTask the task executing this method
	 * @param WEB_URL The servers url
	 * @return returnValue a string used for determining success
	 */
	public static String postNewViewable(Task currentTask, String WEB_URL)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost autogenerates keys
		HttpPost request = new HttpPost(WEB_URL);
		String returnValue = "";
		
		HttpClient refreshClient = new DefaultHttpClient();
		HttpPost refresh = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t13/_refresh");
		try
		{
			HttpResponse response = refreshClient.execute(refresh);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearchRefresh",status);
			request.setHeader("Accept","application/json");
			
			//the object of the current serverTask is the viewable to be serialized
			String jsonString = gson.toJson(currentTask.getObj());
			
			//proceeding with elasticSearch request
			request.setEntity(new StringEntity(jsonString));
			response = client.execute(request);
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
	/**
	 * Searches the Server for the 
	 * 
	 * 
	 * 
	 * @param currentTask
	 * @param WEB_URL
	 * @return
	 */
	public static String findESIDByID(Task currentTask, String WEB_URL)
	{
		String id = "";
		
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");

		HttpClient refreshClient = new DefaultHttpClient();
		HttpPost refresh = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t13/_refresh");
		try
		{
			HttpResponse response = refreshClient.execute(refresh);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearchRefresh",status);
			
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			Log.w("ElasticSearchUpdateTest",currentTask.getSearchTerm());
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);

			//Execute search
			response = client.execute(request);
			status = response.getStatusLine().toString();
			Log.w("ElasticSearchFINDES",status);

			//Get ID of Viewable as it exists in the elastic search system
			String json = getEntityContent(response);
			
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<CommentTreeElement>>(){}.getType();
			ElasticSearchSearchResponse<CommentTreeElement> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			System.err.println(esResponse);
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<CommentTreeElement> r : esResponse.getHits()) {
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
	
	public static CommentTreeElement retrieveViewable(Task currentTask, String WEB_URL)
	{
		//hierarchyAdapter changes serializer rules for first arg
		//to custom serialization class rules
		//specified by the user in the second arg
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:SSSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementServerSerializer()).create();
		
		//Add _search tag to search the elasticSearch data storage system
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(WEB_URL + "_search");
		
		HttpClient refreshClient = new DefaultHttpClient();
		HttpPost refresh = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t13/_refresh");
		try
		{
			HttpResponse response = refreshClient.execute(refresh);
			String status = response.getStatusLine().toString();
			Log.w("ElasticSearchRefresh",status);
			
			Log.w("ESTest",currentTask.getSearchTerm());
			Log.w("ESTest",WEB_URL);
			//search for the current serverTask's searchTerm 
			//in the ID field of the Viewable class
			String query = 	"{\"query\" : {\"query_string\" : {\"default_field\" : \"ID\",\"query\" : \"" + currentTask.getSearchTerm() + "\"}}}";
			StringEntity stringentity = new StringEntity(query);


			request.setHeader("Accept","application/json");
			request.setEntity(stringentity);

			//Execute elasticSearch request
			response = client.execute(request);
			status = response.getStatusLine().toString();
			Log.w("ElasticSearch",status);


			String json = getEntityContent(response);
			Log.w("ESTEST", json);

			//Set the matching viewable as the object of the ServerResult
			Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<CommentTreeElement>>(){}.getType();
			ElasticSearchSearchResponse<CommentTreeElement> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
			if(esResponse.getHits().size() > 1)throw new IllegalArgumentException("Multiple results...ID should be unique in database.");
			for (ElasticSearchResponse<CommentTreeElement> r : esResponse.getHits()) {
				CommentTreeElement output = r.getSource();
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
		
	/**
	 * Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
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
