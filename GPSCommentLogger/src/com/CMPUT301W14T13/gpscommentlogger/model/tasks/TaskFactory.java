package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerDispatcher;
import com.CMPUT301W14T13.gpscommentloggertests.mockups.DataEntityMockup;

public class TaskFactory {

	private ServerDispatcher dispatcher;
	private DataEntityMockup mock;
    private DataManager local;
	
	public TaskFactory(ServerDispatcher dispatcher,
					   DataEntityMockup mock,
					   DataManager local) {
		this.dispatcher = dispatcher;
		this.mock = mock;
		this.local = local;
	}
	
	public InitializationServerTask getNewInitializer()
	{
		return new InitializationServerTask(dispatcher);
	}
	
	public ImageUpdateServerTask getNewImageUpdater()
	{
		return new ImageUpdateServerTask(dispatcher);
	}
	
	public LocationUpdateServerTask getNewLocationUpdater()
	{
		return new LocationUpdateServerTask(dispatcher);
	}

	public MyFavouritesLocalTask getNewFavouriteBrowser()
	{
		return new MyFavouritesLocalTask(local);
	}
	
	public MySavesLocalTask getNewSavesBrowser()
	{
		return new MySavesLocalTask(local);
	}
	
	public PageMockTask getNewMockBrowser()
	{
		return new PageMockTask(mock);
	}
	
	public PostMockTask getNewMockPoster()
	{
		return new PostMockTask(mock);
	}
	
	public PostNewServerTask getNewPoster()
	{
		return new PostNewServerTask(dispatcher);
	}
	
	public SearchServerTask getNewBrowser()
	{
		return new SearchServerTask(dispatcher);
	}
	
	public TextUpdateServerTask getNewTextUpdater()
	{
		return new TextUpdateServerTask(dispatcher);
	}
	
}
