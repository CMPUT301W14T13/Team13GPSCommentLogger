package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.CMPUT301W14T13.gpscommentlogger.CustomAdapter;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.controller.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Root;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;



public class FavouritesViewActivity extends Activity implements FView<CommentLogger>
{

	ArrayList<Topic> favouriteTopics = new ArrayList<Topic>();
	ArrayList<Comment> favouriteComments = new ArrayList<Comment>();
	Preferences prefs;
	
	private ListView topicListView;
	private ListView commentListView;
	private CustomAdapter adapter; //adapter to display the topics
	
	private CommentLoggerController controller; //controller for the model
	private CommentLogger cl; // our model
	
	/* whole topics can be favourited, as can individual comments */
	private static final String topicFile = "topics.sav";
	private static final String commentFile = "comments.sav";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_view);
		prefs = new Preferences(getApplicationContext());

		/*load the topics and comments */
		favouriteTopics = prefs.loadTopicFile(topicFile);
		
		/* loading the comments does not work! */
		//favouriteComments = prefs.loadCommentFile(commentFile);
		
		/* now display these loaded topics */
		topicListView = (ListView) findViewById(R.id.favourites_listview);
		topicListView.setAdapter(adapter);
		
		//set up listener for topic clicks, clicking makes you enter the topic
		topicListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/* this may create strange behaviour, make sure the parent activity is chosen correctly */
				Intent viewTopic = new Intent(FavouritesViewActivity.this, TopicViewActivity.class);
				controller.updateCurrentTopic(position); //set the current topic the user is opening

				startActivity(viewTopic);


			}
		});
		
		// IDEALLY, this should get the topics from the server.
		cl = CommentLogger.getInstance();
		controller = new CommentLoggerController(cl);
		cl.addTopic(new Topic("Testing", true));
		updateFavouritesView();
		
	}
	
	public void updateFavouritesView()
	{
		Log.w("UpdateHomeView", Boolean.toString(cl.getRoot() == null));
		adapter = new CustomAdapter(this, cl.getTopics());
		topicListView.setAdapter(adapter);
	}
	
	@Override
	public void update(CommentLogger model)
	{

		// TODO Auto-generated method stub

	}

}
