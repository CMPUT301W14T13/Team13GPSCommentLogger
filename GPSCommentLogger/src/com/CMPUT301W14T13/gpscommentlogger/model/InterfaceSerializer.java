package com.CMPUT301W14T13.gpscommentlogger.model;

import java.lang.reflect.Type;

import android.util.Log;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Class responsible for serializing and deserializing
 * objects we need to save and load into JSON objects
 *
 * @param <T>
 */
public class InterfaceSerializer<T> implements
        JsonSerializer<T>, JsonDeserializer<T> {

    private static final String CLASS_META_KEY = "CLASS_META_KEY";
    private static final String CLASS_DATA = "CLASS_DATA";

    /**
     * Function converts from JSON to given Type
     */
    @Override
    public T deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        Log.w("Deserialization", "Type: " + type);
        Log.w("Deserialization", "JSONOBJ : " + jsonElement);
        String className = jsonObj.get(CLASS_META_KEY).getAsString();
        Log.w("Deserialization", "Class type: " + className);
        try {
            Class<?> clz = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonObj.get(CLASS_DATA), clz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    /**
     * Function converts object of given Type to JSON
     */
    @Override
    public JsonElement serialize(T object, Type type,
            JsonSerializationContext jsonSerializationContext) {
        
    	Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, this).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
    	
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty(CLASS_META_KEY,
                object.getClass().getCanonicalName());
        jsonObj.add(CLASS_DATA, gson.toJsonTree(object,object.getClass()));
        Log.w("Serialization", "Class type: " + object.getClass().getCanonicalName());
        return jsonObj;
    }

}

