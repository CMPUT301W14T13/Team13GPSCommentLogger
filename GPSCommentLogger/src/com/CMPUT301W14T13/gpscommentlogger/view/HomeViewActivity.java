package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientController;
import com.CMPUT301W14T13.gpscommentlogger.controller.ClientServerSystem;
import com.CMPUT301W14T13.gpscommentlogger.controller.DataManager;
import com.CMPUT301W14T13.gpscommentlogger.controller.ServerController;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.InitializationServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.PostNewServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.SearchServerTask;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.TaskFactory;

/* this is our main activity */
public class HomeViewActivity extends Activity {


	// private ArrayList<Viewable> topics = new ArrayList<Viewable>();
	private ListView topicListView;
	private Root home_view = new Root();
	ArrayList<Viewable> contentList;
	/* variables used for passing things back and forth between client and server */
	private ClientController client;
	private ServerController server;

	private DataManager dataManager;
	private TaskFactory taskFactory;

	Handler textHandler;
	Handler listHandler;

	CustomAdapter commentAdapter;
	/*
	private ServerController serverController = new ServerController(handler, debuggingWindow);
	private ClientListener clientListener = new ClientListener(serverController);
	private ServerDispatcher serverDispatch = new ServerDispatcher(clientListener);
	private TaskFactory factory = new TaskFactory(serverDispatch, mockData, localData);
	 */

	/* where the cached data gets saved */
	private String savePath = "local_data.sav";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home_view);
		String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
		
		//temp debug window
		final TextView debugWindow = (TextView)findViewById(R.id.debug_window);
		debugWindow.setText("Hello World!");
		final HomeViewActivity activity = this;

		textHandler = new Handler(Looper.getMainLooper()) {

			@Override
			public void handleMessage(Message inputMessage) {
				final String msg = inputMessage.obj.toString();
				activity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Log.w("DebugMessage", "Message Received: " + msg);
						((TextView)activity.findViewById(R.id.debug_window)).setText(msg);
						Log.w("Debug Message", "Current Text: " + debugWindow.getText().toString());
					}});			
			}
		};

		/////////////////////////

		contentList = home_view.getChildren();
		topicListView = (ListView) findViewById(R.id.topic_listview);

		commentAdapter = new CustomAdapter(this, home_view.getChildren());
		topicListView.setAdapter(new CustomAdapter(this, home_view.getChildren()));
		/*
		listHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
            	final Viewable msg = (Viewable)inputMessage.obj;
            	activity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Log.w("DebugMessage", "Message Received: " + msg);
				    	home_view = (Root)msg;
				    	contentList = msg.getChildren();
				    	commentAdapter.notifyDataSetChanged();
		            	Log.w("Debug Message", "Current Comment: " + home_view.getID());
		            	}});			
            }
        };
		 */
		// IDEALLY, this should get the topics from the server.

		/* Initialize all the server variables in here */
		ClientServerSystem.getInstance().init(textHandler, debugWindow);
		client = ClientServerSystem.getInstance().getClient();
		server = ClientServerSystem.getInstance().getServer();

		taskFactory = new TaskFactory(client.getDispatcher(), client.getMockup(), client.getDataManager());

		InitializationServerTask task = taskFactory.getNewInitializer();
		client.addTask(task);

		//Testing: Populate ArrayList with topic objects
		Topic top1 = new Topic("First", "User1");
		PostNewServerTask addChild1 = taskFactory.getNewPoster();
		addChild1.setSearchTerm("ROOT");
		addChild1.setObj(top1);
		client.addTask(addChild1);

		Topic top2 = new Topic("Second", "User2");
		top2.addChild(new Comment());
		PostNewServerTask addChild2 = taskFactory.getNewPoster();
		addChild2.setSearchTerm("ROOT");
		addChild2.setObj(top2);
		client.addTask(addChild2);

		Topic top3 = new Topic("Third", "User3");
		top3.addChild(new Comment());
		PostNewServerTask addChild3 = taskFactory.getNewPoster();
		addChild3.setSearchTerm("ROOT");
		addChild3.setObj(top3);
		client.addTask(addChild3);

		Topic top4 = new Topic("Fourth", "User4");
		PostNewServerTask addChild4 = taskFactory.getNewPoster();
		addChild4.setSearchTerm("ROOT");
		addChild4.setObj(top4);
		client.addTask(addChild4);

		// try getting a pushed topic from the server
		this.getViewableFromServer("ROOT");

		//set up adapter and listview
		//topicListView = (ListView) findViewById(R.id.topic_listview);

		//set up listener for topic clicks, clicking makes you enter the topic
		topicListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent viewTopic = new Intent(HomeViewActivity.this, TopicViewActivity.class);
				viewTopic.putExtra("Topic", (Topic) home_view.getChildren().get(position));
				//viewTopic.putExtra("Topic", topics.get(position));
				startActivity(viewTopic);
				debugWindow.setText(home_view.getID());

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


	}

	protected void onResume(){
		super.onResume();

		topicListView.setAdapter(new CustomAdapter(this, home_view.getChildren()));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_action_bar, menu);
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

	private void createTopic(){
		Intent topic = new Intent(this, CreateSubmissionActivity.class);
		topic.putExtra("code", 0);
		startActivityForResult(topic, 0);
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 0){
			if (resultCode == RESULT_OK){

				Topic topic = (Topic) data.getParcelableExtra("Topic");
				home_view.addChild(topic);
				this.pushTopicToServer(topic);
				/* do a local cache of this as well in the save file */

			}	
		}

	}
	
	private void getViewableFromServer(String ID){
		
		SearchServerTask task = taskFactory.getNewBrowser();
		task.setSearchTerm(ID);
		client.addTask(task);
		
	}

	private void pushTopicToServer(Topic topic){

				
		PostNewServerTask task = taskFactory.getNewPoster();
		task.setSearchTerm(home_view.getID());
		task.setObj(topic);
		client.addTask(task);

		/*
		//Build task object
		ClientTask task = new ClientTask();

		task.setTaskCode(ClientTaskTaskCode.POST);
		task.setSourceCode(ClientTaskSourceCode.SERVER_DATA);
		task.setObj(topic);

		//Add task object
		//ClientController controller = new ClientController();

		//controller.addTask(task);
		 */
	}

}
