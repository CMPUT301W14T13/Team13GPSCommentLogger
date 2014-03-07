package com.CMPUT301W14T13.gpscommentlogger.view;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;




public class TopicViewActivity extends Activity

{


	private Topic topic = new Topic();
	private ArrayList<Viewable> comments;
	private Comment comment = new Comment();
	private ListView commentListview;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view);
        
        topic = (Topic) getIntent().getParcelableExtra("Topic");
        
        //comments = new ArrayList<Viewable>();
        //topic.setChildren(comments); //initialize children
        
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
		
		commentListview.setAdapter(new CommentAdapter(this, topic.getChildren()));
	}
	
	public void reply(View v) throws InterruptedException{
		
		Intent intent = new Intent(this, CreateSubmissionActivity.class);
		int rowNumber;
		
				
		 switch (v.getId()) {
		 
	         case R.id.topic_reply_button:
	        	 //intent.putExtra("row number", -1);
	        	 intent.putExtra("code", 1);
	        	 startActivityForResult(intent, 0);  //replying to a topic
	             break;
	             
	         case R.id.comment_reply_button:
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being replied to
	        	 intent.putExtra("code", 1);
	        	 intent.putExtra("row number", rowNumber);
	        	 startActivityForResult(intent, 1); //replying to a comment
	        	 break;
	        	 
	         default:
	 			throw new InterruptedException("Invalid button press");
		 }
		 
		
	}
	
	public void edit(View v) throws InterruptedException{
		
		Intent intent = new Intent(this, CreateSubmissionActivity.class);
		int rowNumber;
		
				
		 switch (v.getId()) {
		 
	         case R.id.topic_edit_button:
	        	 //intent.putExtra("row number", -1);
	        	 intent.putExtra("code", 3);
	        	 intent.putExtra("submission", topic);
	        	 startActivityForResult(intent, 2);  //editing a topic
	             break;
	             
	         case R.id.comment_edit_button:
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being edited
	        	 comment = (Comment) topic.getChildren().get(rowNumber);
	        	 
	        	 intent.putExtra("code", 2);
	        	 intent.putExtra("row number", rowNumber);
	        	 intent.putExtra("submission", comment);
	        	 startActivityForResult(intent, 3); //editing a comment
	        	 break;
	        	 
	         default:
	 			throw new InterruptedException("Invalid button press");
		 }
		 
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			
		
		if (resultCode == RESULT_OK){
			
			int row;
			Comment comment = (Comment) data.getParcelableExtra("comment");
			comments = new ArrayList<Viewable>();
		
		
				
			switch (requestCode){
				
			case(0):  //reply to topic
				topic.addChild(comment);
				break;
				
			case(1): //reply to comment
				row = data.getIntExtra("row number", -1);
				//comment.setChildren(comments); //initialize children
				topic.getChildren().get(row).addChild(comment);
				break;
				
			case(2)://edit topic
				Topic editedTopic = (Topic) data.getParcelableExtra("Topic");
				topic.setUsername(editedTopic.getUsername());
				topic.setCommentText(editedTopic.getCommentText());
				break;
			
			case(3): //edit comment
				row = data.getIntExtra("row number", -1);
				topic.getChildren().get(row).setUsername(comment.getUsername());
				topic.getChildren().get(row).setCommentText(comment.getCommentText());
				break;
				
			 default:
				Log.d("onActivityResult", "Error adding comment reply");
			}
			
		}
			
		//update the listview after the reply has been added
		((BaseAdapter) commentListview.getAdapter()).notifyDataSetChanged();
		

	}

}
