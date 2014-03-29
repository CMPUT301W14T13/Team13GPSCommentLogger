package cmput301w14t13.project.controllers;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.tasks.InitializationServerTask;
import cmput301w14t13.project.models.tasks.RootSearchServerTask;
import cmput301w14t13.project.models.tasks.TaskFactory;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.DataStorageService.LocalBinder;
import cmput301w14t13.project.views.HomeView;
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

public class HomeController implements AsyncProcess{
	public DataStorageService dataService;
	public boolean isBoundToDataService;
	private final HomeView homeView;

	public HomeController(HomeView homeView) {
		this.homeView = homeView;
	}
	
	public void init() throws InterruptedException
	{
		CommentTree ct = CommentTree.getInstance();
        DataStorageService dss = DataStorageService.getInstance();
        
		initializeDataServiceConnection();
		
		initializeLocationService();
        
        //initialize(dss);
        
		getRoot(dss);
		
		ct.addView(homeView);

		addListListener();
	}
	
	private void addListListener() {
		//set up listener for topic clicks, clicking makes you enter the topic
		homeView.getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Intent viewTopic = new Intent(homeView, TopicViewActivity.class);
				//CommentTree ct = CommentTree.getInstance();
				//ct.pushToCommentStack(ct.getCurrentChildren().get(position)); //set the current topic the user is opening

				//homeView.startActivity(viewTopic);
			}
		});
	}

	private synchronized void getRoot(DataStorageService dss) throws InterruptedException {
		RootSearchServerTask task = new TaskFactory(dss).getRoot(homeView);
		dss.doTask(this, task);
		wait();
	}

	private synchronized void initialize(DataStorageService dss) throws InterruptedException {
		InitializationServerTask initTask = new TaskFactory(dss).getNewInitializer();
        dss.doTask(this, initTask);
        wait();
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
		final ServiceConnection dataServiceConnection = new ServiceConnection() {

			@Override
		    public void onServiceDisconnected(ComponentName arg0) {
		    	isBoundToDataService = false;
		    }

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
		        LocalBinder binder = (LocalBinder) service;
		        dataService = binder.getService();
		        isBoundToDataService = true;
			}
		    
		   };
		   
        Intent intent = new Intent(homeView, DataStorageService.class);
        homeView.bindService(intent, dataServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void createTopic(){
		Intent topic = new Intent(homeView, CreateSubmissionController.class);
		topic.putExtra("construct code", 0);
		homeView.startActivity(topic);
	}

	private void viewFavourites(){
		//Intent favourites = new Intent(homeView, FavouritesViewActivity.class);
		//homeView.startActivity(favourites);
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

}