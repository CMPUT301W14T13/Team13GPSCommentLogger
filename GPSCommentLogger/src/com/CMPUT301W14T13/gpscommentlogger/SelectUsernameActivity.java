package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SelectUsernameActivity extends Activity
{

	private ArrayList<String> usernames = new ArrayList<String>();
	private ListView usernameListView;
	private String currentUsername = "";
	private TextView text;
	private ArrayAdapter<String> adapter;
	
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_view);
       
        //Add test usernames
        usernames.add("User1");
        usernames.add("User2");
        usernames.add("User3");
        usernames.add("User4");
        
        
        text = (TextView) findViewById(R.id.currentUsernameTextView);
		text.setText("Current username: " + currentUsername);
        
        
		usernameListView = (ListView) findViewById(R.id.usernamesList);
		//listView.setTextFilterEnabled(true);
		adapter = new ArrayAdapter<String>(this, R.layout.username_row, R.id.usernameTextView, usernames);
		usernameListView.setAdapter(adapter);
		
		usernameListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
			    currentUsername = adapter.getItem(position);
			    text = (TextView) findViewById(R.id.currentUsernameTextView);
				text.setText("Current username: " + currentUsername);
			}
		});
        
	}
	
	
	public void done(View v){
		
		Intent intent = getIntent();
		intent.putExtra("current username", currentUsername);
		
		setResult(RESULT_OK, intent);
		finish();
		
	}
}
