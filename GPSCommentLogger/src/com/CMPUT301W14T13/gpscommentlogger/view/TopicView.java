package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;



public class TopicView extends Activity
{

	private Topic topic = new Topic();
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view);
        
        topic = (Topic) getIntent().getSerializableExtra("Topic");
        
	}
	
	
	protected void onResume(){
		super.onResume();
		
		fillTopicLayout();
	}
	
	
	
	public void fillTopicLayout(){
		
		TextView text = (TextView) findViewById(R.id.topic_username);
		text.setText(topic.getUsername());
		
		text = (TextView) findViewById(R.id.topic_comment);
		text.setText(topic.getCommentText());
		
		text = (TextView) findViewById(R.id.topic_title);
		text.setText(topic.getTitle());
	}
}
