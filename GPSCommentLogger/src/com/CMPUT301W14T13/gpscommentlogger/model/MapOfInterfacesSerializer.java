package com.CMPUT301W14T13.gpscommentlogger.model;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.inject.TypeLiteral;

public class MapOfInterfacesSerializer implements
	JsonSerializer<HashMap<String,Viewable>>, JsonDeserializer<HashMap<String,Viewable>>{

	@Override
	public HashMap<String, Viewable> deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {

		HashMap<String, Viewable> out = new HashMap<String, Viewable>();
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
		
		JsonObject jObject =  arg0.getAsJsonObject();
        Iterator<Entry<String,JsonElement>> keys = jObject.entrySet().iterator();

        while( keys.hasNext() ){
        	Entry<String,JsonElement> pairs = (Entry<String,JsonElement>)keys.next();
        	Log.w("MapDeserialization", "JSONElement: " + pairs.getValue());
        	Log.w("MapDeserialization",pairs.getKey() + "==" + gson.fromJson(pairs.getValue(), Viewable.class));
            out.put(pairs.getKey(), gson.fromJson(pairs.getValue(),Viewable.class));
        }
		
		return out;
	}

	@Override
	public JsonElement serialize(HashMap<String, Viewable> arg0, Type arg1,
			JsonSerializationContext arg2) {

		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
		
		Iterator<Entry<String, Viewable>> it = arg0.entrySet().iterator();
		JsonObject jObj = new JsonObject();
	    while (it.hasNext()) {
	        HashMap.Entry<String,Viewable> pairs = (HashMap.Entry<String,Viewable>)it.next();
	        Log.w("MapSerialization",pairs.getKey().toString() + "==" + gson.toJsonTree(pairs.getValue(),Viewable.class));
	        jObj.add(pairs.getKey().toString(), gson.toJsonTree(pairs.getValue(),Viewable.class));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
	    return jObj;
	}

}
