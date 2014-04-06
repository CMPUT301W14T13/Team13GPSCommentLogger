package cmput301w14t13.project.services.serialization;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.util.Log;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.ImageUpdateServerTask;
import cmput301w14t13.project.models.tasks.LocationUpdateServerTask;
import cmput301w14t13.project.models.tasks.PostNewServerTask;
import cmput301w14t13.project.models.tasks.Task;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.models.tasks.TextUpdateServerTask;
import cmput301w14t13.project.services.DataStorageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class TaskSerializer implements JsonSerializer<Task>, JsonDeserializer<Task>
{
	private static final String TASK_META_KEY = "TASK_META_KEY";
	private static final String TASK_DATA = "TASK_DATA";
    private RankedHierarchicalActivity activity;
    
    public TaskSerializer(RankedHierarchicalActivity activity)
    {
    	this.activity = activity;
    }
	
	//serialization method taken from http://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
	@Override
	public JsonElement serialize(Task arg0, Type arg1,
			JsonSerializationContext arg2)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementPureSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
		
		JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty(TASK_META_KEY,
                arg0.getClass().getCanonicalName());
        
        final JsonObject viewableJson = new JsonObject();
        viewableJson.addProperty("searchTerm", arg0.getSearchTerm());
        viewableJson.addProperty("obj", gson.toJson(arg0.getObj()));
        
        jsonObj.add(TASK_DATA, viewableJson);
        
		return  jsonObj;
	}

	//deserialization method taken from http://stackoverflow.com/questions/5243547/decode-byte-array-to-bitmap-that-has-been-compressed-in-java
	@Override
	public Task deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext arg2) throws JsonParseException
	{
		JsonObject jsonObj = jsonElement.getAsJsonObject();
        Log.w("ServerDeserialization", "Type: " + type);
        Log.w("ServerDeserialization", "JSONOBJ : " + jsonElement);
        String className = jsonObj.get(TASK_META_KEY).getAsString();
        Log.w("ServerDeserialization", "Class type: " + className);
        try {
        	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementPureSerializer()).registerTypeHierarchyAdapter(Bitmap.class, new BitmapSerializer()).create();
        	
        	JsonObject viewable = jsonObj.get(TASK_DATA).getAsJsonObject();
        	
        	String searchTerm = viewable.get("searchTerm").getAsString();
        	CommentTreeElement ele = gson.fromJson(viewable.get("obj").getAsString(), CommentTreeElement.class);
        	
        	Task instance = null;
        	DataStorageService dss = DataStorageService.getInstance();
        	
        	if(className.contains("ImageUpdateServerTask"))
        	{
        		instance = new ImageUpdateServerTask(dss, ele,activity);
        	}
        	else if(className.contains("LocationUpdateServerTask"))
        	{
        		instance = new LocationUpdateServerTask(dss, ele,activity);
        	}
        	else if(className.contains("TextUpdateServerTask"))
        	{
        		instance = new TextUpdateServerTask(dss, ele,activity);
        	}
        	else if(className.contains("PostNewServerTask"))
        	{
        		instance = new PostNewServerTask(dss, searchTerm, ele,activity);
        	}
        	else
        	{
        		throw new IllegalArgumentException("Illegal Class Deserialization");
        	}

        	return instance;
        	
        } catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
        return null;
	}

}
