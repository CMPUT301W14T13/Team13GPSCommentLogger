package cmput301w14t13.project.services;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.util.Log;
import cmput301w14t13.project.models.content.CommentTreeElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
//import com.google.inject.TypeLiteral;

/**
 * Specialized serializer for our
 * Viewable object
 */
public class MapOfInterfacesSerializer implements
	JsonSerializer<HashMap<String,CommentTreeElement>>, JsonDeserializer<HashMap<String,CommentTreeElement>>{

	@Override
	public HashMap<String, CommentTreeElement> deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {

		HashMap<String, CommentTreeElement> out = new HashMap<String, CommentTreeElement>();
		
		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementSerializer()).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
		
		JsonObject jObject =  arg0.getAsJsonObject();
        Iterator<Entry<String,JsonElement>> keys = jObject.entrySet().iterator();

        while( keys.hasNext() ){
        	Entry<String,JsonElement> pairs = (Entry<String,JsonElement>)keys.next();
        	Log.w("MapDeserialization", "JSONElement: " + pairs.getValue());
        	Log.w("MapDeserialization",pairs.getKey() + "==" + gson.fromJson(pairs.getValue(), CommentTreeElement.class));
            out.put(pairs.getKey(), gson.fromJson(pairs.getValue(),CommentTreeElement.class));
        }
		
		return out;
	}

	@Override
	public JsonElement serialize(HashMap<String, CommentTreeElement> arg0, Type arg1,
			JsonSerializationContext arg2) {

		Gson gson = new GsonBuilder().registerTypeAdapter(CommentTreeElement.class, new CommentTreeElementSerializer()).setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
		
		Iterator<Entry<String, CommentTreeElement>> it = arg0.entrySet().iterator();
		JsonObject jObj = new JsonObject();
	    while (it.hasNext()) {
	        HashMap.Entry<String,CommentTreeElement> pairs = (HashMap.Entry<String,CommentTreeElement>)it.next();
	        Log.w("MapSerialization",pairs.getKey().toString() + "==" + gson.toJsonTree(pairs.getValue(),CommentTreeElement.class));
	        jObj.add(pairs.getKey().toString(), gson.toJsonTree(pairs.getValue(),CommentTreeElement.class));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
	    return jObj;
	}

}
