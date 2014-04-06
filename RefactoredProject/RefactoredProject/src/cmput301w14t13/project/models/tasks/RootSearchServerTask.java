package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.elasticsearch.ElasticSearchOperations;
import cmput301w14t13.project.views.HomeView;

/**
 * A subclass of SearchServerTask made specifically to grab 
 * the Root from server for Homeview to display Topics
 * 
 * Used when HomeView displays the list of Topics
 * @author  mjnichol
 */
public class RootSearchServerTask extends SearchServerTask
{

	private HomeView hva;
	
	public RootSearchServerTask(DataStorageService esc, HomeView hva)
	{
		super(esc, "ROOT");
		this.hva = hva;
	}
	/**
	 * Grabs the root from the Server then pushes it to 
	 * the CommentTree's Stack and updates HomeView
	 * returns a success or failure string based on whether
	 * or not it grabbed a null CommentTreeElement
	 */
	@Override
	public String doTask() throws InterruptedException {
		this.obj = ElasticSearchOperations.retrieveViewable(this, esc.getURL());
		final Root newRoot = (Root)this.obj;
		CommentTree.getInstance().pushToCommentStack(newRoot);
		hva.runOnUiThread(new Runnable(){
			@Override
			public void run()
			{
				hva.update();
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
