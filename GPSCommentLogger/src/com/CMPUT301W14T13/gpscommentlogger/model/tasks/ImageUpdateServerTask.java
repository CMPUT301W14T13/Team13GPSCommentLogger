package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.InterfaceSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
* This class's method searches for the ID and updates
* the version in the elasticSearch versioning system 
* to the new state provided by the client
*/
public class ImageUpdateServerTask extends Task {

	public ImageUpdateServerTask(ElasticSearchController esc, Viewable obj) {
		super(esc, obj.getID(), obj);
	}

	@Override
	public String doTask() throws InterruptedException {	
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//the object of the current serverTask is the viewable to be serialized
		String jsonString = gson.toJson(this.getObj().getImage());
		String fieldName = "image";

		//searchTerm should have the viewable's ID
		//we need to get the ESID to process an update request
		String esID = ServerOperations.findESIDByID(this, esc.getURL());
		
		//next, we update the viewable
		return ServerOperations.updateField(esID,this,fieldName,jsonString , esc.getURL());
	}

}
