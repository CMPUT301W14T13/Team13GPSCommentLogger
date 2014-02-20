package com.CMPUT301W14T13.gpscommentlogger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;



/*
 * The purpose of this class is to test various functions such as button
 * clicks, option selections, etc, that you can't implement in the main
 * classes yet because they are not ready. An example is testing a 
 * dialogue box when you click "reply" on a comment. This can't be 
 * implemented without some other things first so it can be created
 * and tested here instead.
 */

public class TestFunctions extends Activity {

	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.functions_test_view);
	        
	    }
	    

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	    
	    
	    
	    public void makeComment(View v){
	    	
	    }
}
