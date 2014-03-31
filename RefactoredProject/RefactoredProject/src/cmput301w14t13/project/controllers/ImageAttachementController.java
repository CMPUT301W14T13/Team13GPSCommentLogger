package cmput301w14t13.project.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


/**
 * This class contains code for starting a
 * picture attachment intent, retrieving an image from
 * the phone gallery, and resolving it into a bitmap.
 * 
 * The code insures that returned images
 * don't surpass the 100 KB size requirement.
 *   
 * @author Monir Imamverdi
 */

public class ImageAttachementController extends Activity
{

	// Reference: http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically

	private static final int PICK_FROM_FILE = 1;
	private String selectedImagePath;
	private Bitmap selectedImageBitmap;

	/**
	 * Start intent for user to select
	 * image from gallery and return bitmap
	 * if conditions are satisfied
	 */
	public void attachmentIntentStart() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_FILE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_FROM_FILE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				selectedImageBitmap = getBitmap(selectedImageUri);
			}
		}
	}

	/**
	 * Helper function returns URI address
	 * of selected image from gallery
	 */
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

	/**
	 * Helper function resolves URI address
	 * to get Bitmap object. Function will return Bitmap
	 * only if satisfies size requirement of 100 KB
	 */
	@SuppressLint("NewApi") // Suppression can be removed if API target is greater than 12
	public Bitmap getBitmap (Uri uri) {
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);



			// Return bitmap if size is less than 100*1024 bytes
			if (sizeCheck(bitmap)) {
				return bitmap;
			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Toast.makeText(getApplicationContext(), "Image Size Exceeds 100 KB",
				   Toast.LENGTH_LONG).show();
		Log.w("IMAGE ATTACH", "Image too large");
		return null;
	}

	/**
	 * Method to check size of bitmap, 
	 * 
	 * 
	 * @param image is the image being checked
	 * @return boolean  true if it's 100 KB or less
	 */
	@SuppressLint("NewApi")
	public boolean sizeCheck (Bitmap image) {
		// Get size in bytes
		/*
		int size = image.getByteCount();
		
		// Return true if size is less than 100*1024 bytes
		if (size < 102401) return true;
		
		else return false;
		 */
		return false;
	}



}