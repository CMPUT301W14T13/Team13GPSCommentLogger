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


public class SelectUsernameActivity extends Activity
{

	private ArrayList<String> usernames = new ArrayList<String>();
	private ListView usernameListView;
	private String currentUsername = "";
	private TextView text;
	private UsernameAdapter adapter;
	private Preferences prefs;
	private CommentLogger cl;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_view);
       prefs = new Preferences(getApplicationContext());
       cl = CommentLoggerApplication.getCommentLogger();
       currentUsername = cl.getCurrentUsername();
        //Add test usernames
       /* usernames.add("User1");
        usernames.add("User2");
        usernames.add("User3");
        usernames.add("User4");*/
        
        usernames = prefs.getArray();
        
        text = (TextView) findViewById(R.id.currentUsernameTextView);
		text.setText("Current username: " + currentUsername);
        
        
		usernameListView = (ListView) findViewById(R.id.usernamesList);
		//listView.setTextFilterEnabled(true);
		adapter = new UsernameAdapter(this, usernames);
		
		
		usernameListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			   
			    cl.setCurrentUsername(adapter.getItem(position));
			    text = (TextView) findViewById(R.id.currentUsernameTextView);
				text.setText("Current username: " + cl.getCurrentUsername());
			}
		});
        
	}
	
	public void onResume(){
		super.onResume();
		
		usernames = prefs.getArray();
		usernameListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
	}
	
	public void add(View v){
		EditText newUsername = (EditText) findViewById(R.id.addUsername);
		usernames.add(newUsername.getText().toString());
		prefs.saveArray(usernames);
		onResume();
	}
	
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
