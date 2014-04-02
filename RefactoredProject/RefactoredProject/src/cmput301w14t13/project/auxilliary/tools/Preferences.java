package cmput301w14t13.project.auxilliary.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;

import com.google.gson.Gson;

/**
 * Credit to Ben Butzow. Taken from http://butzow.me/blog/saving-arrays-in-sharedpreferences/
 * 
 * Class responsible for handling 
 * ArrayLists and converting them from and
 * to JSON objects
 */
public class Preferences {
	Context context;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	private static final String FAVOURITES = "favourites.sav";


	public Preferences(Context context) {
		this.context = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
	}

	/**
	 * Converts the provided ArrayList<String>
	 * into a JSONArray and saves it as a single
	 * string in the apps shared preferences
	 * @param array ArrayList<String> containing the list items
	 */
	public void saveArray(ArrayList<String> array) {
		JSONArray jArray = new JSONArray(array);
		editor.remove("buttonArray");
		editor.putString("buttonArray", jArray.toString());
		editor.commit();
	}

	/**
	 * Loads a JSONArray from shared preferences
	 * and converts it to an ArrayList<String>
	 * @return ArrayList<String> containing the saved values from the JSONArray
	 */
	public ArrayList<String> getArray() {
		ArrayList<String> array = new ArrayList<String>();
		String jArrayString = prefs.getString("buttonArray", "cr5315AI");
		if (jArrayString.matches("cr5315AI")) return getDefaultArray();
		else {
			try {
				JSONArray jArray = new JSONArray(jArrayString);
				for (int i = 0; i < jArray.length(); i++) {
					array.add(jArray.getString(i));
				}
				return array;
			} catch (JSONException e) {
				return getDefaultArray();
			}
		}
	}
	// Get the default list if there isn't anything saved
	private ArrayList<String> getDefaultArray() {
		ArrayList<String> array = new ArrayList<String>();

		return array;
	}

	//load the viewables using gson
	public ArrayList<CommentTreeElement> loadTopicFile(String file){
		Log.d("load_fav","loaded array list");
		ArrayList<CommentTreeElement> topics = new ArrayList<CommentTreeElement>();
		Log.d("load_fav","trying");
		try {
			Log.d("load_fav","making input stream");
			FileInputStream fis = context.openFileInput(file);
			Log.d("load_fav","making reader");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			Log.d("load_fav","getting first line");
			String line = reader.readLine();
			Gson gson = new Gson();

			//iterate through the loaded array and add it to the topic array

			CommentTreeElement topic;	
			Log.d("load_fav","reading all topics");
			while (line != null) {
				Log.d("load_fav","looping");
				Log.d("load_fav", line);
				topic = gson.fromJson(line, Topic.class);
				topics.add(topic);
				line = reader.readLine();
			}


			fis.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return topics;
	}

	//save the viewables using gson
	public void saveInTopicFile(String file, CommentTreeElement topic) {


		try {
			Gson g_object = new Gson();
			String to_be_stored = g_object.toJson(topic) + "\n";
			FileOutputStream fos = context.openFileOutput(file,
					Context.MODE_APPEND);


			fos.write(to_be_stored.getBytes());

			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}


	//load the viewables using gson
	public ArrayList<CommentTreeElement> loadCommentFile(String file){

		ArrayList<CommentTreeElement> comments = new ArrayList<CommentTreeElement>();

		try {
			FileInputStream fis = context.openFileInput(file);

			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String line = reader.readLine();
			Gson gson = new Gson();

			//iterate through the loaded lines and add it to the comment array

			CommentTreeElement comment;	
			while (line != null) {

				comment = gson.fromJson(line, Comment.class);
				comments.add(comment);
				line = reader.readLine();
			}


			fis.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return comments;
	}

	//save the viewables using gson
	
	/* this needs to be fixed, it is bugged */
	public void saveInCommentFile(String file, CommentTreeElement comment) {


		try {
			Gson g_object = new Gson();
			String to_be_stored = g_object.toJson(comment) + "\n";
			FileOutputStream fos = context.openFileOutput(file,
					Context.MODE_APPEND);


			fos.write(to_be_stored.getBytes());

			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
