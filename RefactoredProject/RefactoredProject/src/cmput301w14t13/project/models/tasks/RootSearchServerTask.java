package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.ElasticSearchOperations;
import cmput301w14t13.project.views.HomeView;

public class RootSearchServerTask extends SearchServerTask
{
	private HomeView hva;
	
	public RootSearchServerTask(DataStorageService esc, HomeView hva)
	{
		super(esc, "ROOT");
		this.hva = hva;
	}
	
	@Override
	public String doTask() throws InterruptedException {
		this.obj = ElasticSearchOperations.retrieveViewable(this, esc.getURL());
		CommentTree.getInstance().pushToCommentStack((Root)this.obj);
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
