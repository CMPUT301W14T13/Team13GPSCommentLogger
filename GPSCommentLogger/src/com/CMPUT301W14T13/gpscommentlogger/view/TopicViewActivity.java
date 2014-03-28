package com.CMPUT301W14T13.gpscommentlogger.view;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.CommentAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.SelectUsernameActivity;
import com.CMPUT301W14T13.gpscommentlogger.controller.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.controller.CreateSubmissionActivity;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;




/**
 * TopicViewActivity is where the user can view the topic that they selected
 * from HomeViewActivity. Here they can comment, edit their comments, and
 * select a global username.
 * 
 * @author Austin
 *
 */
public class TopicViewActivity extends Activity implements FView<CommentLogger> 

{

	private ArrayList<Viewable> commentList = new ArrayList<Viewable>(); //the list of comments to display
	private Comment comment = new Comment();
	private ListView commentListview;
	private CommentAdapter adapter; //adapter to display the comments
	private CommentLogger cl; // our model
	private CommentLoggerController controller;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view);
        
        cl = CommentLogger.getInstance();
        controller = new CommentLoggerController(cl);
        
        adapter = new CommentAdapter(this, cl.getCommentList());
        commentListview = (ListView) findViewById(R.id.comment_list);
        commentListview.setAdapter(adapter);
       
		cl.addView(this);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		fillTopicLayout();
		commentListview.setAdapter(adapter); //set the adapter again so the appropriate edit buttons are hidden
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
		inflater.inflate(R.menu.topic_action_bar, menu);
		return super.onCreateOptionsMenu(menu);

	}

	/**
	 * There will be a select username option on the action bar
	 * which takes the user to an activity to manage their
	 * usernames.
	 */
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
	
	/**
	 * Sets the various text fields in the topic 
	 */
	public void fillTopicLayout(){
		
		Topic currentTopic = cl.getCurrentTopic();
		
		TextView text = (TextView) findViewById(R.id.topic_username);
		text.setText(currentTopic.getUsername());
		
		text = (TextView) findViewById(R.id.topic_comment);
		text.setText(currentTopic.getCommentText());
		
		text = (TextView) findViewById(R.id.topic_title);
		text.setText(currentTopic.getTitle());
		
		/* having trouble getting the coordinates, why is this?*/
		text = (TextView) findViewById(R.id.coordinates);
		text.setText(currentTopic.locationString());
		
		Date post_time = currentTopic.getTimestamp();
		text = (TextView) findViewById(R.id.age);
		text.setText(currentTopic.getDateDiff(post_time, new Date()));
		
		text = (TextView) findViewById(R.id.number_of_comments);
		text.setText(String.valueOf(currentTopic.getCommentCount()) + " comments");
		
		/* show bitmap */
		ImageView imageView = (ImageView) findViewById(R.id.commentImage);
		if (cl.getCurrentTopic().getHasImage()) {
			imageView.setImageBitmap(cl.getCurrentTopic().getImage());
		}
		
	}
	
	/**
	 * When the user hits a reply button, it can either be a reply to the topic
	 * or a reply to a comment. Since there is a separate button for each comment,
	 * they have tags which were set in CommentAdapter. This row number is then passed
	 * along into CreateSubmissonActivity so it can get the proper parent comment
	 * and add a child comment to it. It also passes a construct code which tells
	 * the activity how to construct the submission. In this case, it will be 
	 * constructing a comment. A submit code is passed as well which tells the
	 * activity what is being submitted and how to add it to the proper parent comment
	 * or topic.
	 * 
	 * 
	 * @param v	 the view for the reply button
	 * @throws InterruptedException when an incorrect id is found
	 */
	public void reply(View v) throws InterruptedException{
		
		Intent intent = new Intent(this, CreateSubmissionActivity.class);
		int rowNumber;
		intent.putExtra("construct code", 1); //construct a comment
		
		 switch (v.getId()) {
		 
	         case R.id.topic_reply_button:
	        	 
	        	 intent.putExtra("submit code", 0); //replying to a topic
	        	 startActivity(intent); 
	             break;
	             
	         case R.id.comment_reply_button:
	        	 
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being replied to
	        	 intent.putExtra("submit code", 1); //replying to a comment
	        	 intent.putExtra("row number", rowNumber);
	        	 startActivity(intent); //replying to a comment
	        	 break;
	        	 
	         default:
	 			throw new InterruptedException("Invalid button press");
		 }
		 
		
	}
	
	/**
	 * When the user hits an edit button, they can either be editing the topic
	 * or editing a comment. Since there is a separate button for each comment,
	 * they have tags which were set in CommentAdapter. This row number is then passed
	 * along into CreateSubmissonActivity so it can get the proper comment
	 * and edit it. It also passes a construct code which tells
	 * the activity how to construct the edited submission. In this case, it will be 
	 * editing a topic or comment. A submit code is passed as well which tells the
	 * activity what is being submitted and how to edit it properly.
	 * 
	 * 
	 * @param v	 the view for the edit button
	 * @throws InterruptedException when an incorrect id is found
	 */
	public void edit(View v) throws InterruptedException{
		
		Intent intent = new Intent(this, CreateSubmissionActivity.class);
		int rowNumber;
		
				
		 switch (v.getId()) {
		 
	         case R.id.topic_edit_button:
	        
	        	 intent.putExtra("construct code", 3); // constructing an edited topic
	        	 intent.putExtra("submit code", 2);  //editing a topic
	        	 startActivity(intent); 
	             break;
	             
	         case R.id.comment_edit_button:
	        	 
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being edited
	        	
	        	 intent.putExtra("construct code", 2); //constructing an edited comment
	        	 intent.putExtra("submit code", 3); //editing a comment
	        	 intent.putExtra("row number", rowNumber);
	        	 startActivity(intent); 
	        	 break;
	        	 
	         default:
	 			throw new InterruptedException("Invalid button press");
		 }
		 
		
	}

	
	//Called when the image in a comment is clicked
	//Need to expand the image or something here
	public void viewImage(View v){
		
		int tag = (Integer) v.getTag();
		
	}
	
	public void saveComment(View v){
		
		Preferences prefs = new Preferences(getApplicationContext());
		Comment comment = (Comment) cl.getCommentList().get((Integer) v.getTag());
		prefs.saveInCommentFile("comments.sav", comment);
	}
	
	public void saveTopic(View v){
		
		Preferences prefs = new Preferences(getApplicationContext());
		Topic topic = cl.getCurrentTopic();
		prefs.saveInTopicFile("topics.sav", topic);
	}
	
	@Override
	public void update(CommentLogger model)
	{
		fillTopicLayout();
		commentList.clear();
		commentList.addAll(cl.getCommentList());
		adapter.notifyDataSetChanged();
	}

}
