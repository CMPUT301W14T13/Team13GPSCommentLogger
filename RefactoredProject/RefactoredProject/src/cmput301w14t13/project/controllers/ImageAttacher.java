package cmput301w14t13.project.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import cmput301w14t13.project.models.content.CommentTreeElementSubmission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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

public class ImageAttacher
{

	// Reference: http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically

	private static final int PICK_FROM_FILE = 1;
	private CommentTreeElementSubmission submission;
	private CreateSubmissionView view;
	private Bitmap bitmap;
	
	
	public ImageAttacher(CommentTreeElementSubmission submission, Bitmap bitmap)
	{
		this.submission = submission;
		this.bitmap = bitmap;
	}

	/**
	 * Helper function resolves URI address
	 * to get Bitmap object. Function will return Bitmap
	 * only if satisfies size requirement of 100 KB
	 */
	@SuppressLint("NewApi") // Suppression can be removed if API target is greater than 12
	public Bitmap getBitmap () {
		if(sizeCheck(bitmap))
		{
			return bitmap;
		}
		else
		{
			return resizeImage(bitmap);
		}
	}
	

	/**
	 * 
	 * @author http://stackoverflow.com/questions/4837715/how-to-resize-a-bitmap-in-android
	 * 
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @return
	 * @param image is the image being checked
	 * @return boolean:  true if it's 100 KB or less
	 */
	@SuppressLint("NewApi")
	public boolean sizeCheck (Bitmap image) {
		// Get size in bytes
		
		int size = image.getByteCount();
		
		// Return true if size is less than 100*1024 bytes
		if (size < 102401) return true;
		
		else return false;
	}
	
	
	/**
	 * If image size is greater than 100 KB,
	 * this function is called to resize the image
	 * to 100 KB or less.
	 * 
	 * This function only deals images after 
	 * they are imported into a bitmap object.
	 */
	public Bitmap resizeImage (Bitmap image) {
		// Set width/height requirements
		int maxSize = 102401;

		int width = image.getWidth();
		int height = image.getHeight();

		float bitmapRatio = (float) width / (float) height;

		// Handle portrait and landscape images
		if (bitmapRatio > 0) {
			width = maxSize;
			height = (int) (width / bitmapRatio);
		}
		else {
			height = maxSize;
			width = (int) (width * bitmapRatio);	
		}
		
		return Bitmap.createScaledBitmap(image, width, height, true);
	}

	public void execute()
	{
		submission.setBitmap(getBitmap());
	}
}
