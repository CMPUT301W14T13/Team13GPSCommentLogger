package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.controller.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.InitializationServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.SearchServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;

/* this is our main activity */
/**
 * HomeViewActivity is where the user will see a list of topics and various options
 * like adding a topic, viewing saved comments, and sorting topics. From here the user can
 * click on a topic to enter it and view its comments and/or reply to comments
 *
 * @author Austin
 *
 */
public class HomeViewActivity extends Activity implements FView<CommentLogger>{

	private ListView topicListview;
	private Handler listViewHandler;
	private Root home_view = new Root();
	private CommentLoggerController controller; //controller for the model
	private CommentLogger cl; // our model
	private CustomAdapter adapter; //adapter to display the topics
	private ArrayList<Viewable> displayedTopics = new ArrayList<Viewable>();
	
	private Menu menu; //A reference to the options menu
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);

		// IDEALLY, this should get the topics from the server.
		cl = CommentLogger.getInstance();
		controller = new CommentLoggerController(cl);
		
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		controller.update(); //updates the topic age in HomeViewActivity for when the user exits this activity
		invalidateOptionsMenu();
		
		final HomeViewActivity activity = this;
		
		listViewHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
            	final Root root = (Root)inputMessage.obj;
            	activity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Log.w("DebugMessage", "Message Received in Home View");
						home_view = root;
						init();
		            	}});			
            }
        };
        
        ClientServerSystem.getInstance().init(listViewHandler);
        ClientController clientController = ClientServerSystem.getInstance().getClient();
        
        InitializationServerTask initTask = new TaskFactory(clientController.getDispatcher(),clientController.getMockup(),clientController.getDataManager()).getNewInitializer();
        clientController.addTask(initTask);

		SearchServerTask task = new TaskFactory(clientController.getDispatcher(),clientController.getMockup(),clientController.getDataManager()).getNewBrowser();
		try
		{
			task.setId("ROOT");
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.setSearchTerm("ROOT");
		clientController.addTask(task);

	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        CommentLogger cl = CommentLogger.getInstance();
        cl.deleteView(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_action_bar, menu);
		
		this.menu = menu;
		
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.action_post_thread:
				createTopic();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void init()
	{
		adapter = new CustomAdapter(this, home_view.getChildren());
		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);
		topicListview.setAdapter(adapter);


		//set up listener for topic clicks, clicking makes you enter the topic
		topicListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent viewTopic = new Intent(HomeViewActivity.this, TopicViewActivity.class);
				controller.updateCurrentTopic(position); //set the current topic the user is opening

				startActivity(viewTopic);


			}
		});
		/* setup the location managers now so that you can get GPS coords */
		// Acquire a reference to the system Location Manager

		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);



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

		cl.addView(this);
	}

		
	private void createTopic(){
		Intent topic = new Intent(this, CreateSubmissionActivity.class);
		topic.putExtra("construct code", 0);
		startActivity(topic);
	}


	@Override
	public void update(CommentLogger model)
	{
		displayedTopics.clear();
		displayedTopics.addAll(home_view.getChildren());
		adapter.notifyDataSetChanged();
		
	}

	public Menu getMenu() {
		return menu;
	}

}