package cmput301w14t13.project.services.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.models.tasks.SearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Recursively converts an entire CommenTreeElement and all its children to a jsonObject
 * and vice versa 
 * 
 * @author nsd
 *
 */
public class CommentTreeElementPureSerializer implements
        JsonSerializer<CommentTreeElement>, JsonDeserializer<CommentTreeElement>,AsyncProcess {

    private static final String CLASS_META_KEY = "CLASS_META_KEY";
    private static final String CLASS_DATA = "CLASS_DATA";

    /**
     * Converts a json object into a subclass of CommentTreeELement 
     * depending on the class specified by the json object then 
     * grabs an array of json representations of the objects children
     * and recurses through it and converts and adds the CommenTreeElements
     * as childposts.
     * 
     * @return CommentTree Element the converted Topic,Comment,or Root
     */
    @Override
    public synchronized CommentTreeElement deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        Log.w("ServerDeserialization", "Type: " + type);
        Log.w("ServerDeserialization", "JSONOBJ : " + jsonElement);
        String className = jsonObj.get(CLASS_META_KEY).getAsString();
        Log.w("ServerDeserialization", "Class type: " + className);
        try {
        	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementPureSerializer()).registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
        	
        	JsonObject viewable = jsonObj.get(CLASS_DATA).getAsJsonObject();
        	
        	String title = viewable.get("title").getAsString();
        	String ID = viewable.get("ID").getAsString();
        	String commentText = viewable.get("commentText").getAsString();
        	Bitmap image = gson.fromJson(viewable.get("image").getAsString(), Bitmap.class);
        	Date timestamp = gson.fromJson(viewable.get("timestamp").getAsString(), Date.class);
        	Date freshness = gson.fromJson(viewable.get("freshness").getAsString(), Date.class);
        	String username = viewable.get("username").getAsString();
        	Location GPSLocation = gson.fromJson(viewable.get("GPSLocation").getAsString(), Location.class);;
        	ArrayList<CommentTreeElement> childPosts = new ArrayList<CommentTreeElement>();
        	

        	for(final JsonElement each : viewable.get("childPosts").getAsJsonArray())
        	{
        		childPosts.add(gson.fromJson(each, CommentTreeElement.class));
        	}
        	
        	CommentTreeElement instance = null;
        	
        	if(className.contains("Root"))
        	{
        		instance = new Root(ID, username, image, timestamp, commentText);
        	}
        	else if(className.contains("Topic"))
        	{
        		instance = new Topic(ID, username, image, timestamp, commentText);
        	}
        	else if(className.contains("Comment"))
        	{
        		instance = new Comment(ID, username, image, timestamp, commentText);
        	}
        	else
        	{
        		throw new IllegalArgumentException("Illegal Class Deserialization");
        	}
        	instance.setLocation(GPSLocation);
        	instance.setChildren(childPosts);
        	instance.setFreshness(freshness);
        	instance.setTitle(title);   
        	return instance;
        	
        } catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
        return null;
    }

    /**
     * Creates  Json object and hands it information from the CommentTreeElement
     * (E.g. text, username, location), then recurses through its children to do the same
     * 
     * returns the Json object containing representation of The CommentTreeElement and its children
     * 
     *@return JsonObject The converted CommentTreeElement and its children
     */
    @Override
    public JsonElement serialize(CommentTreeElement object, Type type,
            JsonSerializationContext jsonSerializationContext) {
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").registerTypeHierarchyAdapter(CommentTreeElement.class, new CommentTreeElementPureSerializer()).registerTypeHierarchyAdapter(Bitmap.class, new BitmapSerializer()).create();
    	
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty(CLASS_META_KEY,
                object.getClass().getCanonicalName());
        
        final JsonObject viewableJson = new JsonObject();
        viewableJson.addProperty("title", object.getTitle());
        viewableJson.addProperty("ID", object.getID());
        viewableJson.addProperty("commentText", object.getCommentText());
        viewableJson.addProperty("image", gson.toJson(object.getImage()));
        viewableJson.addProperty("timestamp", gson.toJson(object.getTimestamp()));
        viewableJson.addProperty("freshness", gson.toJson(object.getFreshness()));
        viewableJson.addProperty("GPSLocation", gson.toJson(object.getLocation()));
        viewableJson.addProperty("username", object.getUsername());
        
        final JsonArray childArray = new JsonArray();
        for (final CommentTreeElement each : object.getChildren()) {
            childArray.add(new JsonPrimitive(gson.toJson(each)));
        }
        
        viewableJson.add("childPosts", childArray);
        
        jsonObj.add(CLASS_DATA, viewableJson);
        Log.w("Serialization", "Class type: " + object.getClass().getCanonicalName());
        return jsonObj;
    }

	@Override
	public synchronized void receiveResult(String result) {
		notify();		
	}

}

