package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.NetworkReceiver;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.SortFunctions;
import com.CMPUT301W14T13.gpscommentlogger.controller.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.ElasticSearchController;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.LocationSelection;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.model.tasks.RootSearchServerTask;
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
public class HomeViewActivity extends Activity implements FView<CommentLogger>, OnNavigationListener {

	private ListView topicListview;
	private Handler listViewHandler;
	private Root homeView = new Root();
	private CommentLoggerController controller; //controller for the model
	private CommentLogger cl; // our model
	private CustomAdapter adapter; //adapter to display the topics
	private ArrayList<Viewable> displayedTopics = new ArrayList<Viewable>();
	private LocationSelection locationGetter;
	private EditText text;
	private Location location = new Location("default");
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	private Menu menu; //A reference to the options menu

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);

		// IDEALLY, this should get the topics from the server.
		cl = CommentLogger.getInstance();
		controller = new CommentLoggerController(cl);

		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);
		topicListview.setAdapter(adapter);	

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
				// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
					getString(R.string.sort1),
					getString(R.string.sort2),
					getString(R.string.sort3),
					getString(R.string.sort4),
					getString(R.string.sort5),
					getString(R.string.sort6),
				}), this);

		//set up listener for topic clicks, clicking makes you enter the topic
		topicListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent viewTopic = new Intent(HomeViewActivity.this, TopicViewActivity.class);
				controller.updateCurrentTopic(position); //set the current topic the user is opening

				startActivity(viewTopic);


			}
		});

		ElasticSearchController.getInstance().start();

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
		locationGetter = new LocationSelection(this);
		locationGetter.startLocationSelection();
		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);



		// IDEALLY, this should get the topics from the server.
		cl = CommentLogger.getInstance();
		controller = new CommentLoggerController(cl);
		cl.addTopic(new Topic("Testing", true));
		updateHomeView(homeView);
		final HomeViewActivity activity = this;

		ElasticSearchController esc = ElasticSearchController.getInstance();

		//InitializationServerTask initTask = new TaskFactory(esc).getNewInitializer();
		//esc.addTask(initTask);

		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RootSearchServerTask task = new TaskFactory(esc).getRoot(activity);
		esc.addTask(task);

		//set up listener for topic clicks, clicking makes you enter the topic
		topicListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent viewTopic = new Intent(HomeViewActivity.this, TopicViewActivity.class);
				controller.updateCurrentTopic(position); //set the current topic the user is opening

				startActivity(viewTopic);


			}
		});


		cl.addView(this);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}



	@Override
	public void onResume(){
		super.onResume();
		controller.update(); //updates the topic age in HomeViewActivity for when the user exits this activity
		invalidateOptionsMenu();		
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

		case R.id.action_settings:
			return true;

		case R.id.saved:
			viewFavourites();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	public void updateHomeView(Root root)
	{
		cl.setRoot(root);
		Log.w("UpdateHomeView", Boolean.toString(root == null));
		adapter = new CustomAdapter(this, cl.getTopics());
		topicListview.setAdapter(adapter);
	}


	private void createTopic(){
		Intent topic = new Intent(this, CreateSubmissionActivity.class);
		topic.putExtra("construct code", 0);
		startActivity(topic);
	}

	private void viewFavourites(){
		Intent favourites = new Intent(this, FavouritesViewActivity.class);
		startActivity(favourites);
	}

	@Override
	public void update(CommentLogger model)
	{
		adapter.notifyDataSetChanged();

	}

	public Menu getMenu() {
		return menu;
	}


	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// When the given dropdown item is selected, show its contents in the
		// container view.

		// ITEM SELECTION ACTIONS DONE HERE
		ArrayList<Viewable> sortedTopics = cl.getTopics();
		
		switch (itemPosition) {
		case 0:
			
			sortedTopics = SortFunctions.sortByCurrentLocation(sortedTopics);
			Toast.makeText(getApplicationContext(), "Proximity to Me",
					Toast.LENGTH_LONG).show();
			break;
			
		case 1:
			
				openMap();
			
				//locationGetter.startLocationSelection();
			
				sortedTopics = SortFunctions.sortByGivenLocation(sortedTopics, location);
				Toast.makeText(getApplicationContext(), "Proximity to Location",
						Toast.LENGTH_LONG).show();
			
			
			break;
			
		case 2:
			
			sortedTopics = SortFunctions.sortByPicture(sortedTopics);
			Toast.makeText(getApplicationContext(), "Pictures",
					Toast.LENGTH_LONG).show();
			break;
			
		case 3:
			
			sortedTopics = SortFunctions.sortByNewest(sortedTopics);
			
			Toast.makeText(getApplicationContext(), "Newest",
					Toast.LENGTH_LONG).show();
			break;
			
		case 4:
			
			sortedTopics = SortFunctions.sortByOldest(sortedTopics);
			Toast.makeText(getApplicationContext(), "Oldest",
					Toast.LENGTH_LONG).show();
			break;
		case 5:
			sortedTopics = SortFunctions.sortByMostRelevant(sortedTopics);
			Toast.makeText(getApplicationContext(), "Relevant",
					Toast.LENGTH_LONG).show();
			break;
		}

		cl.getTopics().clear();
		cl.getTopics().addAll(sortedTopics);
		adapter.notifyDataSetChanged();
		return true;
	}
	
	public void openMap() {
		if(NetworkReceiver.isConnected){
			Intent map = new Intent(this, MapViewActivity.class);
			//Log.d("CreateSubmissionActivity", locationGetter.getLocation().toString());
			map.putExtra("lat", locationGetter.getLocation().getLatitude()); 
			map.putExtra("lon", locationGetter.getLocation().getLongitude());
			map.putExtra("canSetMarker", 1);// for editing  location
			startActivityForResult(map, 0);  

		} else {
			// when we are not connected to any network we open a dialog for user to edit dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			LayoutInflater inflater = getLayoutInflater();
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
							text = (EditText) dialogView.findViewById(R.id.offlineLatitude);
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
		
		@SuppressLint("NewApi")
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == 0){
				if (resultCode == RESULT_OK){
					double latitude = data.getDoubleExtra("lat", locationGetter.getLocation().getLatitude());
					double longitude = data.getDoubleExtra("lon", locationGetter.getLocation().getLongitude());
					location = new Location("default");
					location.setLongitude(longitude);
					location.setLatitude(latitude);

				}
			}

}
}