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


public class HomeViewController implements AsyncProcess{

	private final class OnLinkClickListener implements OnItemClickListener,AsyncProcess {
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
	
	private void createTopic(){
		Intent topic = new Intent(homeView, CreateSubmissionView.class);
		topic.putExtra("construct code", 0);
		topic.putExtra("updateRank", homeView.getRank().getRank());
		homeView.startActivity(topic);
	}

	private void viewFavourites(){
		Intent favourites = new Intent(homeView, FavouritesView.class);
		homeView.startActivity(favourites);
	}

	public void selectUsername(){
		Intent intent = new Intent(homeView, SelectUsernameController.class);
		homeView.startActivity(intent);
	}
	
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
	
	public void openMap() {
		if(NetworkReceiver.isConnected){
			Intent map = new Intent(homeView, MapViewController.class);
			//Log.d("CreateSubmissionActivity", locationGetter.getLocation().toString());
			map.putExtra("lat", LocationSelection.getInstance().getLocation().getLatitude()); 
			map.putExtra("lon", LocationSelection.getInstance().getLocation().getLongitude());
			map.putExtra("updateRank", homeView.getRank().getRank());
			map.putExtra("canSetMarker", 1);// for editing  location
			homeView.startActivityForResult(map, 0);  

		} else {
			// when we are not connected to any network we open a dialog for user to edit dialog
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

	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		// When the given dropdown item is selected, show its contents in the
		// container view.

		// ITEM SELECTION ACTIONS DONE HERE
		ArrayList<CommentTreeElement> sortedTopics = CommentTree.getInstance().getCommentList(homeView);
		
		switch (itemPosition) {
		case 0:
			
			sortedTopics = SortFunctions.sortByCurrentLocation(sortedTopics);
			Toast.makeText(homeView.getApplicationContext(), "Proximity to Me",
					Toast.LENGTH_LONG).show();
			break;
			
		case 1:
			
				openMap();

				sortedTopics = SortFunctions.sortByGivenLocation(sortedTopics, location);
				Toast.makeText(homeView.getApplicationContext(), "Proximity to Location",
						Toast.LENGTH_LONG).show();
			
			
			break;
			
		case 2:
			
			sortedTopics = SortFunctions.sortByPicture(sortedTopics);
			Toast.makeText(homeView.getApplicationContext(), "Pictures",
					Toast.LENGTH_LONG).show();
			break;
			
		case 3:
			
			sortedTopics = SortFunctions.sortByNewest(sortedTopics);
			
			Toast.makeText(homeView.getApplicationContext(), "Newest",
					Toast.LENGTH_LONG).show();
			break;
			
		case 4:
			
			sortedTopics = SortFunctions.sortByOldest(sortedTopics);
			Toast.makeText(homeView.getApplicationContext(), "Oldest",
					Toast.LENGTH_LONG).show();
			break;
		case 5:
			sortedTopics = SortFunctions.sortByMostRelevant(sortedTopics);
			Toast.makeText(homeView.getApplicationContext(), "Relevant",
					Toast.LENGTH_LONG).show();
			break;
		}

		CommentTree.getInstance().addSortedList(homeView, sortedTopics);
		
		return true;
	}


}