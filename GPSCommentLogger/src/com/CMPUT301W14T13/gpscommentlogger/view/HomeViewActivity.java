package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTask;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskSourceCode;
import com.CMPUT301W14T13.gpscommentlogger.model.ClientTaskTaskCode;
import com.CMPUT301W14T13.gpscommentlogger.model.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;

/* this is our main activity */
public class HomeViewActivity extends Activity {


	private ArrayList<Viewable> topics = new ArrayList<Viewable>();
	private ListView topicListview;


	private Root home_view = new Root();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

		// IDEALLY, this should get the topics from the server.

		//Testing: Populate ArrayList with topic objects
		Topic top1 = new Topic("First", "User1");
		home_view.addChild(top1);

		Topic top2 = new Topic("Second", "User2");
		home_view.addChild(top2);

		Topic top3 = new Topic("Third", "User3");
		home_view.addChild(top3);

		Topic top4 = new Topic("Fourth", "User4");
		home_view.addChild(top4);


		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);

		//set up listener for topic clicks, clicking makes you enter the topic
		topicListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent viewTopic = new Intent(HomeViewActivity.this, TopicViewActivity.class);
				viewTopic.putExtra("Topic", (Topic) home_view.getChildren().get(position));
				//viewTopic.putExtra("Topic", topics.get(position));
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


	}


	protected void onResume(){
		super.onResume();

		topicListview.setAdapter(new CustomAdapter(this, home_view.getChildren()));
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
				//pushTopicToServer(topic);
			}	
		}

	}


	private void pushTopicToServer(Topic topic){

		//Build task object
		ClientTask task = new ClientTask();

		task.setTaskCode(ClientTaskTaskCode.POST);
		task.setSourceCode(ClientTaskSourceCode.SERVER_DATA);
		task.setObj(topic);

		//Add task object
		//ClientController controller = new ClientController();

		//controller.addTask(task);

	}
}
