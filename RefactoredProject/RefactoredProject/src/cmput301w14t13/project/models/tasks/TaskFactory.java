package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.HomeView;

/**
 * Class creates Task objects
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
	
	public ImageUpdateServerTask getNewImageUpdater(CommentTreeElement obj)
	{
		return new ImageUpdateServerTask(esc, obj);
	}
	
	public LocationUpdateServerTask getNewLocationUpdater(CommentTreeElement obj)
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
	
	public PostNewServerTask getNewPoster(String parentID, CommentTreeElement obj)
	{
		return new PostNewServerTask(esc, parentID, obj);
	}
	
	public SearchServerTask getNewBrowser(String searchTerm)
	{
		return new SearchServerTask(esc, searchTerm);
	}
	
	public RootSearchServerTask getRoot(HomeView hva)
	{
		return new RootSearchServerTask(esc, hva);
	}
	
	public TextUpdateServerTask getNewTextUpdater(CommentTreeElement obj)
	{
		return new TextUpdateServerTask(esc, obj);
	}
	
}
