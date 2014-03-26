package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerOperations;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeViewActivity;


public class RootSearchServerTask extends SearchServerTask
{

	private HomeViewActivity hva;
	
	public RootSearchServerTask(ElasticSearchController esc, HomeViewActivity hva)
	{
		super(esc, "ROOT");
		this.hva = hva;
	}
	
	@Override
	public String doTask() throws InterruptedException {
		this.obj = ServerOperations.retrieveViewable(this, esc.getURL());
		final Root root = (Root)this.obj;
		hva.runOnUiThread(new Runnable(){

			@Override
			public void run()
			{
				hva.updateHomeView(root);
			}
			
		});
		
		if(this.obj == null)
		{
			return "failure";
		}
		else
		{
			return "success";
		}
	}

}
