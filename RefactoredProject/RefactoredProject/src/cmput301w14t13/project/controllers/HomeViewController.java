package cmput301w14t13.project.controllers;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.models.tasks.InitializationServerTask;
import cmput301w14t13.project.models.tasks.RootSearchServerTask;
import cmput301w14t13.project.models.tasks.SearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.DataStorageService.LocalBinder;
import cmput301w14t13.project.views.FavouritesView;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
	private ServiceConnection dataServiceConnection;
	private HomeView homeView;

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
	
	public synchronized void bind() throws InterruptedException
	{
		initializeDataServiceConnection();
		initializeLocationService();
        DataStorageService dss = DataStorageService.getInstance();
        dss.doTask(this, new TaskFactory(dss).getRoot(homeView));
        wait();
	}
	
	public void resume() throws InterruptedException
	{
		CommentTree ct = CommentTree.getInstance();
        DataStorageService dss = DataStorageService.getInstance();
		getRoot(dss);
		ct.addView(homeView);
		addListListener();
		ct.updateCommentList(homeView); //updates the topic age in HomeViewActivity for when the user exits this activity
		homeView.invalidateOptionsMenu();
	}
	
	private void addListListener() {
		//set up listener for topic clicks, clicking makes you enter the topic
		homeView.getListView().setOnItemClickListener(new OnLinkClickListener());
	}

	private synchronized void getRoot(DataStorageService dss) throws InterruptedException {
		SearchServerTask task = new TaskFactory(dss).getNewBrowser("ROOT");
		dss.doTask(this, task);
		wait();
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
		/* setup the location managers now so that you can get GPS coords */
		// Acquire a reference to the system Location Manager

		LocationManager locationManager = (LocationManager) homeView.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.
				Log.w("loc_change","changed location");
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}
		};

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
	
	private void initializeDataServiceConnection() {
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
		   
        Intent intent = new Intent(homeView, DataStorageService.class);
        homeView.bindService(intent, dataServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void createTopic(){
		Intent topic = new Intent(homeView, CreateSubmissionController.class);
		topic.putExtra("construct code", 0);
		topic.putExtra("updateRank", homeView.getRank().getRank());
		homeView.startActivity(topic);
	}

	private void viewFavourites(){
		Intent favourites = new Intent(homeView, FavouritesView.class);
		homeView.startActivity(favourites);
	}

	public boolean selectOptions(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_post_thread:
				createTopic();
				return true;
	
			case R.id.saved:
				viewFavourites();
				return true;
				
			default:
				return false;
		}
	}
	
	public void unbind()
	{
		if (isBoundToDataService)
		    homeView.getApplicationContext().unbindService(dataServiceConnection);
	}

}