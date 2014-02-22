package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;



public class TopicView extends Activity
{

	private Topic topic = new Topic();
	private ArrayList<Viewable> comments = new ArrayList<Viewable>();
	
	private ListView commentListview;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view);
        
        topic = (Topic) getIntent().getSerializableExtra("Topic");
        Comment comment = new Comment();
        comment.setCommentText("testing");
        comments.add(comment);
        topic.setChildren(comments);
        
        commentListview = (ListView) findViewById(R.id.comment_list);
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
		
		//commentListview.setAdapter(new CustomAdapter(this, topic.getChildren()));
	}
}
