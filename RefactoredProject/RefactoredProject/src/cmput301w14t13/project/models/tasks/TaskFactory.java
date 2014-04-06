package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
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
	
	public InitializationServerTask getNewInitializer(RankedHierarchicalActivity activity)
	{
		return new InitializationServerTask(esc, activity);
	}
	
	public void requestImageUpdate(CommentTreeElement obj, RankedHierarchicalActivity activity)
	{
		esc.getProxy().saveTask(new ImageUpdateServerTask(esc, obj, activity));
	}
	
	public void requestLocationUpdate(CommentTreeElement obj, RankedHierarchicalActivity activity)
	{
		esc.getProxy().saveTask(new LocationUpdateServerTask(esc, obj, activity));
	}

	public MyFavouritesLocalTask getNewFavouriteBrowser(CommentTreeElement ele, RankedHierarchicalActivity activity)
	{
		return new MyFavouritesLocalTask(esc, ele, activity);
	}
	
	public MySavesLocalTask getNewSavesBrowser(String searchTerm, RankedHierarchicalActivity activity)
	{
		return new MySavesLocalTask(esc, searchTerm, activity);
	}
	
	public void requestPost(String parentID, CommentTreeElement obj, RankedHierarchicalActivity activity)
	{
		esc.getProxy().saveTask(new PostNewServerTask(esc, parentID, obj, activity));
	}
	
	public SearchServerTask getNewBrowser(String searchTerm, RankedHierarchicalActivity activity)
	{
		return new SearchServerTask(esc, searchTerm, activity);
	}
	
	public RootSearchServerTask getRoot(HomeView hva, RankedHierarchicalActivity activity)
	{
		return new RootSearchServerTask(esc, hva, activity);
	}
	
	public void requestTextUpdate(CommentTreeElement obj, RankedHierarchicalActivity activity)
	{
		esc.getProxy().saveTask(new TextUpdateServerTask(esc, obj, activity));
	}
	
}
