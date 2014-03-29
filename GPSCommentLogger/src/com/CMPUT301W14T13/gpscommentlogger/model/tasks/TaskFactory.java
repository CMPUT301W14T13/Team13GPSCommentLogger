package com.CMPUT301W14T13.gpscommentlogger.model.tasks;

import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeViewActivity;

/**
 * Class creates Task objects
 */
public class TaskFactory {

	private ElasticSearchController esc;
	
	public TaskFactory(ElasticSearchController esc) {
		this.esc = esc;
	}
	
	public InitializationServerTask getNewInitializer()
	{
		return new InitializationServerTask(esc);
	}
	
	public ImageUpdateServerTask getNewImageUpdater(Viewable obj)
	{
		return new ImageUpdateServerTask(esc, obj);
	}
	
	public LocationUpdateServerTask getNewLocationUpdater(Viewable obj)
	{
		return new LocationUpdateServerTask(esc, obj);
	}

	public MyFavouritesLocalTask getNewFavouriteBrowser(String searchTerm)
	{
		return new MyFavouritesLocalTask(esc, searchTerm);
	}
	
	public MySavesLocalTask getNewSavesBrowser(String searchTerm)
	{
		return new MySavesLocalTask(esc, searchTerm);
	}
	
	public PostNewServerTask getNewPoster(String parentID, Viewable obj)
	{
		return new PostNewServerTask(esc, parentID, obj);
	}
	
	public SearchServerTask getNewBrowser(String searchTerm)
	{
		return new SearchServerTask(esc, searchTerm);
	}
	
	public RootSearchServerTask getRoot(HomeViewActivity hva)
	{
		return new RootSearchServerTask(esc, hva);
	}
	
	public TextUpdateServerTask getNewTextUpdater(Viewable obj)
	{
		return new TextUpdateServerTask(esc, obj);
	}
	
}
