package cmput301w14t13.project.controllers;

import java.util.ArrayList;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.models.tasks.InitializationServerTask;
import cmput301w14t13.project.models.tasks.RootSearchServerTask;
import cmput301w14t13.project.models.tasks.SearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.DataStorageService.LocalBinder;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.services.NetworkReceiver;
import cmput301w14t13.project.views.CreateSubmissionView;
import cmput301w14t13.project.views.FavouritesView;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * This controller handles models and UI elements
 * displayed by HomeView. Specifically, this controller
 * populates and updates a list of topics, handles clicks
 * on topics and buttons to start corresponding intents/activities,
 * as well handling sorting options from the drop down menu.
 *
 */
public class HomeViewController implements AsyncProcess{

	/**
	 * When an item is clicked, a task is started to
	 * cache the topic locally for offline reading,
	 * then a TopicView intent is called.
	 * 
	 */
	private final class OnLinkClickListener implements OnItemClickListener, AsyncProcess {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Intent viewTopic = new Intent(homeView, TopicView.class);
			viewTopic.putExtra("updateRank", homeView.getRank().getRank() + 1);
			CommentTree ct = CommentTree.getInstance();
			DataStorageService dss = DataStorageService.getInstance();
			SearchServerTask task = new TaskFactory(dss).getNewBrowser(ct.getChildren(homeView).get(position).getID());
			
			try {
				dss.doTask(this, task);
				waitForCompletion();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ct.pushToCommentStack(task.getObj()); //set the current topic the user is opening
			dss.getProxy().startSaveData(task.getObj());
			
			homeView.startActivity(viewTopic);
		}

		private synchronized void waitForCompletion() throws InterruptedException {
			wait();			
		}

		@Override
		public synchronized void receiveResult(String result) {
			notify();			
		}
	}

	
	private boolean isBoundToDataService;
	private ServiceConnection dataServiceConnection = null;
	private HomeView homeView;
	private Location location = new Location("default");

	public HomeViewController(HomeView homeView) {
		this.homeView = homeView;
	}
	
	public void init() throws InterruptedException
	{
        DataStorageService dss = DataStorageService.getInstance();
        initialize(dss);
	}

	public void connect()
			throws InterruptedException {
		
        DataStorageService.getInstance().registerContext(homeView);
        CommentTree.getInstance().registerUIThread(homeView);
	}
	
	public void bind() throws InterruptedException
	{
		initializeDataServiceConnection();
		initializeLocationService();
	}
	
	public synchronized void resume() throws InterruptedException
	{
		CommentTree ct = CommentTree.getInstance();
        DataStorageService dss = DataStorageService.getInstance();
        Log.w("HVResume","Test");
        Log.w("TimingTest","test");
        if(CommentTree.getInstance().isEmpty())
        {
            dss.doTask(this, new TaskFactory(dss).getRoot(homeView));
            wait();
        }
        Log.w("TimingTest","test");
		ct.addView(homeView);
		addListListener();
		ct.updateCommentList(homeView); //updates the topic age in HomeViewActivity for when the user exits this activity
		homeView.invalidateOptionsMenu();
	}
	
	private void addListListener() {
		//set up listener for topic clicks, clicking makes you enter the topic
		homeView.getListView().setOnItemClickListener(new OnLinkClickListener());
	}

	private synchronized void initialize(DataStorageService dss) throws InterruptedException {
		InitializationServerTask initTask = new TaskFactory(dss).getNewInitializer();
        dss.doTask(this, initTask);
        wait();
        dss.getProxy().clearFavourites();
        dss.getProxy().clearSaves((Root)initTask.getObj());
        
	}
	
	@Override
	public synchronized void receiveResult(String result) {
		notify();
	}

	private void initializeLocationService() {
		LocationSelection.getInstance().registerContext(homeView);
		LocationSelection.getInstance().startLocationSelection();
		//set up adapter and listview
		homeView.setListView((ListView) homeView.findViewById(R.id.topic_listview));
	}
	
	private void initializeDataServiceConnection() {
		//Create service connection
		if(dataServiceConnection == null)
		{
			dataServiceConnection = new ServiceConnection() {
	
				@Override
			    public void onServiceDisconnected(ComponentName arg0) {
			    	isBoundToDataService = false;
			    }
	
				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
			        LocalBinder binder = (LocalBinder) service;
			        binder.getService();
			        isBoundToDataService = true;
				}
			    
			   };
			   
			//Bind service
	        Intent intent = new Intent(homeView, DataStorageService.class);
	        homeView.bindService(intent, dataServiceConnection, Context.BIND_AUTO_CREATE);
		}
	}
	
	/**
	 * Start topic view activity for creating
	 * a new topic. Called by button click
	 * listener.
	 */
	private void createTopic(){
		Intent topic = new Intent(homeView, CreateSubmissionView.class);
		topic.putExtra("construct code", 0);
		topic.putExtra("updateRank", homeView.getRank().getRank());
		homeView.startActivity(topic);
	}

	/**
	 * Start favourites view activity to
	 * view topics and comments favourited by the user.
	 * Called by button click listener.
	 */
	private void viewFavourites(){
		Intent favourites = new Intent(homeView, FavouritesView.class);
		homeView.startActivity(favourites);
	}

	/**
	 * Start select usernames activity, where
	 * users can set and select usernames to use
	 * in the app. Not used in this acivity.
	 */
	public void selectUsername(){
		Intent intent = new Intent(homeView, SelectUsernameController.class);
		homeView.startActivity(intent);
	}
	
	/**
	 * Method to handle button clicks on the action bar,
	 * including: create new topic and view favourites.
	 * @param item
	 * @return boolean
	 */
	public boolean selectOptions(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_post_thread:
				createTopic();
				return true;
	
			case R.id.saved:
				viewFavourites();
				return true;
				
			case R.id.action_select_username:
				selectUsername();
				
			default:
				return false;
		}
	}
	
	public void unbind()
	{
		if (isBoundToDataService)
		    homeView.getApplicationContext().unbindService(dataServiceConnection);
	}
	
	/**
	 * Method starts MapViewController if online or
	 * opens a dialog fragment if offline to get
	 * a location input from the user.  
	 * 
	 * This is called by sort functions when the user 
	 * selects to sort topics by proximity to a given 
	 * location.
	 */
	public void openMap() {
		if(NetworkReceiver.isConnected){
			Intent map = new Intent(homeView, MapViewController.class);
			
			map.putExtra("lat", LocationSelection.getInstance().getLocation().getLatitude()); 
			map.putExtra("lon", LocationSelection.getInstance().getLocation().getLongitude());
			map.putExtra("updateRank", homeView.getRank().getRank());
			map.putExtra("canSetMarker", 1);// for editing  location
			homeView.startActivityForResult(map, 0);  

		} else {
			// when we are not connected to any network we open a dialog for user to edit location
			AlertDialog.Builder builder = new AlertDialog.Builder(homeView);

			LayoutInflater inflater = homeView.getLayoutInflater();
			final View dialogView = inflater.inflate(R.layout.offline_location_dialog, null);
			builder.setView(dialogView);
			AlertDialog ad = builder.create();
			ad.setTitle("Select Location");
			ad.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
					new DialogInterface.OnClickListener()
					{	
						@Override
						public void onClick(DialogInterface dialog, int which)
						{  
							EditText text = (EditText) dialogView.findViewById(R.id.offlineLatitude);
							double latitude = Double.parseDouble(text.getText().toString().trim());
							text = (EditText) dialogView.findViewById(R.id.offlineLongitude);
							double longitude = Double.parseDouble(text.getText().toString().trim());
							location = new Location("default");
							location.setLatitude(latitude);
							location.setLongitude(longitude);
							
						}
					});

			ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//do nothing
				}
			});
			ad.show();
			
		}


		}

	/**
	 * This function retrieves results from the 
	 * location selection activity called by
	 * openMap. The location variable in the controller
	 * is updated by setting it to the retrieved latitude
	 * and longitude.
	 *  
	 * @param requestCode
	 * @param resultCode
	 * @param data The Intent from the activity returning a result
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 0){
			if (resultCode == Activity.RESULT_OK){
				double latitude = data.getDoubleExtra("lat", LocationSelection.getInstance().getLocation().getLatitude());
				double longitude = data.getDoubleExtra("lon", LocationSelection.getInstance().getLocation().getLongitude());
				location = new Location("default");
				location.setLongitude(longitude);
				location.setLatitude(latitude);

			}
		}
	}

	/**
	 * Method handles item selection from the
	 * sort drop down menu and calls the corresponding
	 * sort function, passing to it list of current
	 * topics, and a context.
	 * 
	 * A popup message is displayed to indicate selection to the user.
	 * 
	 * @param itemPosition To determine which option user chose
	 * @param itemId
	 * @return
	 */
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		// When the given dropdown item is selected, show its contents in the
		// container view.


		
		switch (itemPosition) {
		//SortByCurrentLocation
		case 0:
			
			CommentTree.getInstance().sortListByCurrentLocation(homeView);
			Toast.makeText(homeView.getApplicationContext(), "Proximity to Me",
					Toast.LENGTH_SHORT).show();
			break;
		//SortByGivenLocation	
		case 1:
			
				openMap();
				CommentTree.getInstance().sortListByGivenLocation(homeView, location);
				Toast.makeText(homeView.getApplicationContext(), "Proximity to Location",
						Toast.LENGTH_SHORT).show();
			
			
			break;
		//SortByPicture	
		case 2:
			
			CommentTree.getInstance().sortListByPicture(homeView);
			Toast.makeText(homeView.getApplicationContext(), "Pictures",
					Toast.LENGTH_SHORT).show();
			break;
		//SortByNewest	
		case 3:
			
			CommentTree.getInstance().sortListByNewest(homeView);
			Toast.makeText(homeView.getApplicationContext(), "Newest",
					Toast.LENGTH_SHORT).show();
			break;
		//SortByOldest	
		case 4:
			
			CommentTree.getInstance().sortListByOldest(homeView);
			Toast.makeText(homeView.getApplicationContext(), "Oldest",
					Toast.LENGTH_SHORT).show();
			break;
		//SortByMostRelevant	
		case 5:
			CommentTree.getInstance().sortListByMostRelevant(homeView);
			Toast.makeText(homeView.getApplicationContext(), "Relevant",
					Toast.LENGTH_SHORT).show();
			break;
		}

		return true;
	}


}