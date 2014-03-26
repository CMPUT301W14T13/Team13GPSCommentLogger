package com.CMPUT301W14T13.gpscommentlogger.view;

import android.app.Activity;
import android.os.Bundle;

import com.CMPUT301W14T13.gpscommentlogger.R;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;


public class FavouritesViewActivity extends Activity implements FView<CommentLogger>
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_view);
	
		
		
	}
	
	
	
	
	
	
	
	
	@Override
	public void update(CommentLogger model)
	{

		// TODO Auto-generated method stub
		
	}

}
