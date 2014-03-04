package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;


public class HomeViewActivity extends Activity {


	private ArrayList<Topic> topics = new ArrayList<Topic>();
	private ListView topicListview;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		String androidId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
		
		//Testing: Populate ArrayList with topic objects
		Topic top1 = new Topic("First", "User1");
		topics.add(top1);
		
		Topic top2 = new Topic("Second", "User2");
		topics.add(top2);
		
		Topic top3 = new Topic("Third", "User3");
		topics.add(top3);
		
		Topic top4 = new Topic("Fourth", "User4");
		topics.add(top4);


		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);

		//set up listener for topic clicks
		topicListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent viewTopic = new Intent(HomeViewActivity.this, TopicViewActivity.class);
				viewTopic.putExtra("Topic", topics.get(position));
				startActivity(viewTopic);


			}
		});
	}


	protected void onResume(){
		super.onResume();

		topicListview.setAdapter(new CustomAdapter(this, topics));
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
				topics.add(topic);
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
