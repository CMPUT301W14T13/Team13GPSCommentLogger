package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.CMPUT301W14T13.gpscommentlogger.controller.CommentLoggerController;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.Preferences;
import com.CMPUT301W14T13.gpscommentlogger.view.FView;

/**
 * In this activity, the user can manage their usernames and
 * select their global username to user when creating or
 * editing. The usernames are stored and retrieved using
 * shared preferences.
 * 
 * @author Austin
 *
 */
public class SelectUsernameActivity extends Activity implements FView<CommentLogger>{

	private ArrayList<String> usernames = new ArrayList<String>();
	private ListView usernameListView;
	private String currentUsername = "";
	private TextView text;
	private UsernameAdapter adapter; //adapter to display the usernames
	private Preferences prefs;
	private CommentLogger cl; //our model
	private CommentLoggerController controller;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.username_view);
		
		prefs = new Preferences(getApplicationContext());
		cl = CommentLogger.getInstance();
		controller = new CommentLoggerController(cl);
		
		usernames = prefs.getArray(); //get the list of usernames

		//get the user's global username and display it
		text = (TextView) findViewById(R.id.currentUsernameTextView);
		text.setText("Current username: " + cl.getCurrentUsername());

		//set the adapter to display the list of usernames
		usernameListView = (ListView) findViewById(R.id.usernamesList);
		adapter = new UsernameAdapter(this, usernames);
		usernameListView.setAdapter(adapter);

		usernameListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				controller.updateCurrentUsername(adapter.getItem(position)); //set the global username
				text = (TextView) findViewById(R.id.currentUsernameTextView);
				text.setText("Current username: " + cl.getCurrentUsername()); //display it
			}
		});
		
		cl.addView(this);
	}

	
	@Override
    public void onDestroy() {
        super.onDestroy();
        CommentLogger cl = CommentLogger.getInstance();
        cl.deleteView(this);
	}
	
	/**
	 * The user can add usernames to their list and displays a toast
	 * if the username is already in their saved list
	 * 
	 * @param v the add button
	 */
	public void add(View v){
		EditText editText = (EditText) findViewById(R.id.addUsername);
		String newUsername = editText.getText().toString();
		
		Toast toast = null;
		Context context = getApplicationContext();
		String text = "Username already added"; 
		int duration = Toast.LENGTH_LONG;
		
		if (!usernames.contains(newUsername) && newUsername.length() != 0){
			usernames.add(newUsername);
			prefs.saveArray(usernames);
		}
		else
		{
			if (newUsername.length() == 0){
				text = "Please enter a username";
			}
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		editText.setText(""); //clear the text field
		controller.update();
	}

	/**
	 * The user can delete usernames from their list. Tags are used 
	 * to determine which username is being deleted.
	 * 
	 * @param v the delete button
	 */
	public void remove(View v){
		int tag = (Integer) v.getTag();
		usernames.remove(tag);
		prefs.saveArray(usernames);
		controller.update();
	};


	public void done(View v){

		finish();
	}

	@Override
	public void update(CommentLogger model)
	{
		usernames.clear();
		usernames.addAll(prefs.getArray());
		adapter.notifyDataSetChanged();
		
	}
}
