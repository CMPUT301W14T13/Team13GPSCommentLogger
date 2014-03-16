package com.CMPUT301W14T13.gpscommentlogger.view;

import com.CMPUT301W14T13.gpscommentlogger.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_attachment_view);
		
		((Button) findViewById(R.id.pick_image)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// This can be separate function
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_FILE);
				
			}
		});
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_FROM_FILE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
			}
		}
	}
	
	public String getPath(Uri uri) {
		if(uri == null) {
			return null;
		}
		
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();return cursor.getString(column_index);
		}
		
		return uri.getPath();
	}
	
	
	
}