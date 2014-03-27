package com.CMPUT301W14T13.gpscommentlogger.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.PostNewServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.SearchServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;
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
 * Class responsible for serializing and deserializing
 * objects we need to save and load into JSON objects
 *
 * @param <T>
 */
public class ViewableSerializer implements
        JsonSerializer<Viewable>, JsonDeserializer<Viewable> {

    private static final String CLASS_META_KEY = "CLASS_META_KEY";
    private static final String CLASS_DATA = "CLASS_DATA";

    /**
     * Function converts from JSON to given Type
     */
    @Override
    public Viewable deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        Log.w("Deserialization", "Type: " + type);
        Log.w("Deserialization", "JSONOBJ : " + jsonElement);
        String className = jsonObj.get(CLASS_META_KEY).getAsString();
        Log.w("Deserialization", "Class type: " + className);
        try {
        	Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, this).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
        	
        	JsonObject viewable = jsonObj.get(CLASS_DATA).getAsJsonObject();
        	
        	String title = viewable.get("title").getAsString();
        	String ID = viewable.get("ID").getAsString();
        	String commentText = viewable.get("commentText").getAsString();
        	Bitmap image = gson.fromJson(viewable.get("image").getAsString(), Bitmap.class);
        	Date timestamp = gson.fromJson(viewable.get("timestamp").getAsString(), Date.class);
        	Date freshness = gson.fromJson(viewable.get("freshness").getAsString(), Date.class);
        	String username = viewable.get("username").getAsString();
        	Location GPSLocation = gson.fromJson(viewable.get("GPSLocation").getAsString(), Location.class);;
        	ArrayList<Viewable> childPosts = new ArrayList<Viewable>();
        	
        	TaskFactory factory = new TaskFactory(ElasticSearchController.getInstance());
        	for(final JsonElement each : viewable.get("childPosts").getAsJsonArray())
        	{
        		String eachID = each.getAsJsonPrimitive().getAsString();
        		SearchServerTask task = factory.getNewBrowser(eachID);
        		ElasticSearchController.getInstance().addTask(task);
        		childPosts.add(task.getObj());
        	}
        	
        	
        	Class<?> clazz = Class.forName(className);
        	Constructor<?> constructor = clazz.getConstructor(String.class, String.class, Bitmap.class, Date.class, String.class);
        	Viewable instance = (Viewable) constructor.newInstance(ID, username, image, timestamp, commentText);
        	instance.setLocation(GPSLocation);
        	instance.setChildren(childPosts);
        	instance.setFreshness(freshness);
        	instance.setTitle(title);        	
        	return instance;
        	
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        } catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
        return null;
    }

    /**
     * Function converts object of given Type to JSON
     */
    @Override
    public JsonElement serialize(Viewable object, Type type,
            JsonSerializationContext jsonSerializationContext) {
        
    	Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, this).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").registerTypeAdapter(Bitmap.class, new BitmapSerializer()).create();
    	
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
        for (final Viewable each : object.getChildren()) {
            childArray.add(new JsonPrimitive(each.getID()));
        }
        
        viewableJson.add("childPosts", childArray);
        
        jsonObj.add(CLASS_DATA, viewableJson);
        Log.w("Serialization", "Class type: " + object.getClass().getCanonicalName());
        return jsonObj;
    }

}

