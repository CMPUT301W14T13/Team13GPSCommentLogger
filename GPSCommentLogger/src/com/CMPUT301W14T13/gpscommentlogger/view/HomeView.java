package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
       Topic topic = new Topic();
       comment = new Comment("testing comment text");
       topic.setTitle("TestingTitle");
       topic.setRootComment(comment);
       topics.add(topic);
       
       //set up adapter
       topicListview = (ListView) findViewById(R.id.topic_listview);
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
                
                
    private void viewFavourites(){
    	
    }
    
    private void sortBy(){
    	
    }
    
    private void createTopic(){
    	Intent topic = new Intent(this, CreateTopic.class);
    	startActivity(topic);
    }
    
    //View comments that have been marked as "read later"
    private void viewReadLater(){
    	
    }
	


	 
}
