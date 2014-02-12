package com.CMPUT301W14T13.gpscommentlogger.view;

import com.CMPUT301W14T13.gpscommentlogger.DebugActivity;
import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.R.layout;
import com.CMPUT301W14T13.gpscommentlogger.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class HomeView extends Activity {

	public static final String DEBUG = "DEBUGGING_MODE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        
        Intent intent = getIntent();
        boolean isDebugging = intent.getExtras().getBoolean(DEBUG);
        if(isDebugging){
        	debug();
        }
        else
        {
        	start();
        }
    }
    
    private void debug(){
    	Intent intent = new Intent(this,DebugActivity.class);
    	startActivity(intent);
    }

    private void start(){
    	//TODO: app goes here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
