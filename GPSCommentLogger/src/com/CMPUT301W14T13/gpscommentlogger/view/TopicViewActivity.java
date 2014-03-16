package com.CMPUT301W14T13.gpscommentlogger.view;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.SelectUsernameActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentModelList;
import com.CMPUT301W14T13.gpscommentlogger.model.FView;
import com.CMPUT301W14T13.gpscommentlogger.model.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;




public class TopicViewActivity extends Activity implements FView<CommentLogger> 

{


	private Topic topic = new Topic();
	private ArrayList<Viewable> commentList = new ArrayList<Viewable>();
	private Comment comment = new Comment();
	private ListView commentListview;
	private static String currentUsername = "";
	private CommentAdapter adapter;
	private CommentLogger cl;
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view);
        cl = CommentLoggerApplication.getCommentLogger();
        
        //topic = (Topic) getIntent().getParcelableExtra("Topic");
        
        
        adapter = new CommentAdapter(this, commentList, currentUsername);
        cl.setCommentAdapter(adapter);
        
        commentList = cl.getCommentList();
        currentUsername = cl.getCurrentUsername();
        
        commentListview = (ListView) findViewById(R.id.comment_list);
        
        adapter = new CommentAdapter(this, commentList, currentUsername);
        
        
		
		
		cl = CommentLoggerApplication.getCommentLogger();
		cl.addView(this);
	}
	
	
	protected void onResume(){
		super.onResume();
		
		fillTopicLayout();
		commentListview.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.topic_action_bar, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_select_username:
			selectUsername();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void selectUsername(){
		Intent intent = new Intent(this, SelectUsernameActivity.class);
		startActivity(intent);
	}
	
	
	public void fillTopicLayout(){
		
		TextView text = (TextView) findViewById(R.id.topic_username);
		text.setText(cl.getCurrentTopic().getUsername());
		
		text = (TextView) findViewById(R.id.topic_comment);
		text.setText(cl.getCurrentTopic().getCommentText());
		
		text = (TextView) findViewById(R.id.topic_title);
		text.setText(cl.getCurrentTopic().getTitle());
		
		
	}
	
	public void reply(View v) throws InterruptedException{
		
		Intent intent = new Intent(this, CreateSubmissionActivity.class);
		int rowNumber;
		intent.putExtra("code", 1);
		
		 switch (v.getId()) {
		 
	         case R.id.topic_reply_button:
	        	 
	        	 intent.putExtra("code 2", 0);
	        	 startActivityForResult(intent, 0);  //replying to a topic
	             break;
	             
	         case R.id.comment_reply_button:
	        	 
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being replied to
	        	 intent.putExtra("code 2", 1);
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
	        	 intent.putExtra("code 2", 2);
	        	 intent.putExtra("submission", topic);
	        	 startActivityForResult(intent, 2);  //editing a topic
	             break;
	             
	         case R.id.comment_edit_button:
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being edited
	        	 comment = (Comment) commentList.get(rowNumber);
	        	 intent.putExtra("code 2", 3);
	        	 intent.putExtra("code", 2);
	        	 intent.putExtra("row number", rowNumber);
	        	 intent.putExtra("submission", comment);
	        	 startActivityForResult(intent, 3); //editing a comment
	        	 break;
	        	 
	         default:
	 			throw new InterruptedException("Invalid button press");
		 }
		 
		
	}

	

	@Override
	public void update(CommentLogger model)
	{
		//CommentLogger cl = CommentLoggerApplication.getCommentLogger();
		//commentList = cl.getCommentList();
		
	}

}
