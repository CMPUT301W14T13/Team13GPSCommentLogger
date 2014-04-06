package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.HomeView;

/**
 * Creates task specific to what is needed, E.g. posting to server 
 * updating image, or updating location
 *  
 */
public class TaskFactory {


	private DataStorageService esc;
	
	public TaskFactory(DataStorageService esc) {
		this.esc = esc;
	}
	
	public InitializationServerTask getNewInitializer()
	{
		return new InitializationServerTask(esc);
	}
	
	public void requestImageUpdate(CommentTreeElement obj)
	{
		esc.getProxy().saveTask(new ImageUpdateServerTask(esc, obj));
	}
	
	public void requestLocationUpdate(CommentTreeElement obj)
	{
		esc.getProxy().saveTask(new LocationUpdateServerTask(esc, obj));
	}

	public MyFavouritesLocalTask getNewFavouriteBrowser(CommentTreeElement ele)
	{
		return new MyFavouritesLocalTask(esc, ele);
	}
	
	public MySavesLocalTask getNewSavesBrowser(String searchTerm)
	{
		return new MySavesLocalTask(esc, searchTerm);
	}
	
	public void requestPost(String parentID, CommentTreeElement obj)
	{
		esc.getProxy().saveTask(new PostNewServerTask(esc, parentID, obj));
	}
	
	public SearchServerTask getNewBrowser(String searchTerm)
	{
		return new SearchServerTask(esc, searchTerm);
	}
	
	public RootSearchServerTask getRoot(HomeView hva)
	{
		return new RootSearchServerTask(esc, hva);
	}
	
	public void requestTextUpdate(CommentTreeElement obj)
	{
		esc.getProxy().saveTask(new TextUpdateServerTask(esc, obj));
	}
	
}
