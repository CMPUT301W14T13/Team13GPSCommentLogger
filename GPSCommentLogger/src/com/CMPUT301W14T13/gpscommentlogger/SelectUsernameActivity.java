package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;

/**
 * In this activity, the user can manage their usernames and
 * select their global username to user when creating or
 * editing. The usernames are stored and retrieved using
 * shared preferences.
 * 
 * @author Austin
 *
 */
public class SelectUsernameActivity extends Activity
{

	private ArrayList<String> usernames = new ArrayList<String>();
	private ListView usernameListView;
	private String currentUsername = "";
	private TextView text;
	private UsernameAdapter adapter; //adapter to display the usernames
	private Preferences prefs;
	private CommentLogger cl; //our model

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.username_view);
		
		prefs = new Preferences(getApplicationContext());
		cl = CommentLoggerApplication.getCommentLogger();
		currentUsername = cl.getCurrentUsername();


		usernames = prefs.getArray(); //get the list of usernames

		//get the user's global username and display it
		text = (TextView) findViewById(R.id.currentUsernameTextView);
		text.setText("Current username: " + currentUsername);

		//set the adapter to display the list of usernames
		usernameListView = (ListView) findViewById(R.id.usernamesList);
		adapter = new UsernameAdapter(this, usernames);


		usernameListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				cl.setCurrentUsername(adapter.getItem(position)); //set the global username
				text = (TextView) findViewById(R.id.currentUsernameTextView);
				text.setText("Current username: " + cl.getCurrentUsername()); //display it
			}
		});

	}

	public void onResume(){
		super.onResume();

		usernames = prefs.getArray();
		usernameListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	/**
	 * The user can add usernames to their list
	 * 
	 * @param v the add button
	 */
	public void add(View v){
		EditText newUsername = (EditText) findViewById(R.id.addUsername);
		usernames.add(newUsername.getText().toString());
		prefs.saveArray(usernames);
		onResume();
	}

	/**
	 * The user can deleted usernames from their list. Tags are used 
	 * to determine which username is being deleted.
	 * 
	 * @param v the delete button
	 */
	public void remove(View v){
		int tag = (Integer) v.getTag();
		usernames.remove(tag);
		prefs.saveArray(usernames);
		onResume();
	};


	public void done(View v){

		finish();
	}
}
