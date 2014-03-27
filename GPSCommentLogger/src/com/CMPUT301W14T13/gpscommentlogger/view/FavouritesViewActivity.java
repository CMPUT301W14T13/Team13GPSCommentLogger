package com.CMPUT301W14T13.gpscommentlogger.view;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;


public class FavouritesViewActivity extends Activity implements FView<CommentLogger>
{

	ArrayList<Topic> favouriteTopics = new ArrayList<Topic>();
	ArrayList<Comment> favouriteComments = new ArrayList<Comment>();
	Preferences prefs;
	private static final String topicFile = "topics.sav";
	private static final String commentFile = "comments.sav";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_view);
		prefs = new Preferences(getApplicationContext());

		/*favouriteComments = prefs.loadCommentFile(commentFile);

		String text = "";
		for (int i = 0; i < favouriteComments.size(); i++){
			text += favouriteComments.get(i).getCommentText() + "\n";
		}*/

		favouriteTopics = prefs.loadTopicFile(topicFile);

		String text = "";
		for (int i = 0; i < favouriteTopics.size(); i++){
			text += favouriteTopics.get(i).getUsername() + "\n";
		}

		TextView textview = (TextView) findViewById(R.id.textView1);
		textview.setText(text);
	}








	@Override
	public void update(CommentLogger model)
	{

		// TODO Auto-generated method stub

	}

}
