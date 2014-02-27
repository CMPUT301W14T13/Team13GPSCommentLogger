package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;


public class HomeView extends Activity {

	
	private ArrayList<Topic> topics = new ArrayList<Topic>();
	private Comment comment;
	private ListView topicListview;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        
       //Test topic to display
       //addTestTopics();
       
       //set up adapter and listview
       topicListview = (ListView) findViewById(R.id.topic_listview);
       
       //set up listener for topic clicks
       topicListview.setOnItemClickListener(new OnItemClickListener() {
           
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        	   Intent viewTopic = new Intent(HomeView.this, TopicView.class);
        	   viewTopic.putExtra("Topic", topics.get(position));
        	   startActivity(viewTopic);
          
           
           }
       });
    }
    

    protected void onResume(){
    	super.onResume();
    	
    	topicListview.setAdapter(new CustomAdapter(this, topics));
    }
    
    private void addTestTopics() {
    	Topic topic = new Topic();
        comment = new Comment();
        topic.setTitle("TestingTitle");
        topic.setRootComment(comment);
        topics.add(topic);
        
        topic = new Topic();
        comment = new Comment();
        topic.setUsername("Austin");
        topic.setRootComment(comment);
        topics.add(topic);
		
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
                
                
    private void viewFavourites(){
    	
    }
    
    private void sortBy(){
    	
    }
    
    private void createTopic(){
    	Intent topic = new Intent(this, CreateTopicActivity.class);
    	startActivityForResult(topic, 0);
    }
    
   
    //View comments that have been marked as "read later"
    private void viewReadLater(){
    	
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == 0){
			if (resultCode == RESULT_OK){
				
				Topic topic = (Topic) data.getSerializableExtra("Topic");
				topics.add(topic);
			}	
		}
	 
	}
	
}
