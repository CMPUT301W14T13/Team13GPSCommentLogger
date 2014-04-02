package cmput301w14t13.project.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.tasks.SearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.views.FavouritesView;
import cmput301w14t13.project.views.TopicView;

/* This controller simply needs to be able to view the saved topics and comment data. 
 * I don't think that it should be using any of the server stuff because this must work offline.
 * 
 * It seems fairly clear to me that a lot of what CommentTreeProxy can do wants to be done in this
 * controller. 
 * 
 * */

/**
 * @author  mjnichol
 */
public class FavouritesViewController{

	private FavouritesView favouritesView;
	
	public FavouritesViewController(FavouritesView favouritesView) {
		this.favouritesView = favouritesView;
	}
	
	private void addListListener() {
		//set up listener for topic clicks, clicking makes you enter the topic
		favouritesView.getListView().setOnItemClickListener(new OnLinkClickListener());
	}
	
	/* */
	public void initialize(){
		
	
	}
	
	/* I think this click listener will function as desired. It is more or less a straight copy
	 * of the homeView listener */
	private final class OnLinkClickListener implements OnItemClickListener,AsyncProcess {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent viewTopic = new Intent(favouritesView, TopicView.class);
			viewTopic.putExtra("updateRank", favouritesView.getRank().getRank() + 1);
			
			/* We will want to switch out some of the below with the new Favourites model (that is to act as a stand in for the CommentTree) */
			CommentTree ct = CommentTree.getInstance();
			DataStorageService dss = DataStorageService.getInstance();
			SearchServerTask task = new TaskFactory(dss).getNewBrowser(ct.getChildren(favouritesView).get(position).getID());
			try {
				dss.doTask(this, task);
				waitForCompletion();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ct.pushToCommentStack(task.getObj()); //set the current topic the user is opening
			dss.getProxy().startSaveData(task.getObj());
			
			
			
			
			
			favouritesView.startActivity(viewTopic);
		}

		private synchronized void waitForCompletion() throws InterruptedException {
			wait();			
		}

		@Override
		public synchronized void receiveResult(String result) {
			notify();			
		}
	}
}
