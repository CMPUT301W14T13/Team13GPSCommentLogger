package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.InterfaceSerializer;
import com.CMPUT301W14T13.gpscommentlogger.model.ServerContext;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.results.Result;
import com.CMPUT301W14T13.gpscommentlogger.model.results.ServerResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Modified form https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
//This method searches for the ID and updates
//the version in the elasticSearch versioning system 
//to the new state provided by the client

public class TextUpdateServerTask extends UpdateServerTask {

	public TextUpdateServerTask(ServerDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public Result executeUpdateOnServer(ServerContext context) throws InterruptedException {
		ServerResult out = new ServerResult();
		out.setId(this.id);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Viewable.class, new InterfaceSerializer<Viewable>()).create();
		
		//the object of the current serverTask is the viewable to be serialized
		String jsonString = gson.toJson(this.getObj().getCommentText());
		String fieldName = "commentText";

		//searchTerm should have the viewable's ID
		//we need to get the ESID to process an update request
		String esID = ServerOperations.findESIDByID(this, context.getURL());
		
		//next, we update the viewable
		ServerOperations.updateField(esID,this, out, fieldName,jsonString ,context.getURL());
		
		return out;
	}

}
