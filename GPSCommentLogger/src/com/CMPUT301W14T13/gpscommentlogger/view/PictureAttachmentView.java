package com.CMPUT301W14T13.gpscommentlogger.view;

import com.CMPUT301W14T13.gpscommentlogger.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;


/**
 * @uml.dependency   supplier="com.CMPUT301W14T13.gpscommentlogger.view.CommentMakingView"
 */
public class PictureAttachmentView extends Activity
{

	// Reference: http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
	
	private static final int PICK_FROM_FILE = 1;
	private String selectedImagePath;
	
	public void onCreate(Bundle savedInstaceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_attachment_view);
		
		((Button) findViewById(R.id.pick_image)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent();
				
			}
		});
		
	}
}