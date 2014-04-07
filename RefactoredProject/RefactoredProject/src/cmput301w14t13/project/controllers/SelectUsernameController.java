package cmput301w14t13.project.controllers;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.UsernameAdapter;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.services.DataStorageService;

/**
 * In this activity, the user can manage their usernames and 
 * select their global username to user when creating or editing. 
 * The usernames are stored and retrieved using shared preferences.
 * 
 * @author  Austin
 */
public class SelectUsernameController extends Activity implements UpdateInterface{

	private ArrayList<String> usernames = new ArrayList<String>();
	private ListView usernameListView;
	private String currentUsername = "";
	private TextView text;

	private UsernameAdapter adapter; //adapter to display the usernames
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.username_view);
		
		final CommentTree cl = CommentTree.getInstance();
		usernameListView = (ListView) findViewById(R.id.usernamesList);
		
		DataStorageService.getInstance().getProxy().initializePrefs(this);
		
		usernames = DataStorageService.getInstance().getProxy().getArray(); //get the list of usernames

		//get the user's global username and display it
		text = (TextView) findViewById(R.id.currentUsernameTextView);
		text.setText("Current username: " + cl.getCurrentUsername());

		adapter = new UsernameAdapter(this, usernames);
		usernameListView.setAdapter(adapter);

		usernameListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				cl.setCurrentUsername(adapter.getItem(position)); //set the global username
				text = (TextView) findViewById(R.id.currentUsernameTextView);
				text.setText("Current username: " + cl.getCurrentUsername()); //display it
			}
		});
		
		cl.addView(this);
	}

	
	@Override
    public void onDestroy() {
        super.onDestroy();
        CommentTree cl = CommentTree.getInstance();
        cl.deleteView(this);
	}
	
	/**
	 * The user can add usernames to their list and displays a toast
	 * if the username is already in their saved list
	 * 
	 * @param v the add button
	 */
	public void add(View v){
		EditText editText = (EditText) findViewById(R.id.addUsername);
		String newUsername = editText.getText().toString();
		
		Toast toast = null;
		Context context = getApplicationContext();
		String text = "Username already added"; 
		int duration = Toast.LENGTH_LONG;
		
		if (!usernames.contains(newUsername) && newUsername.length() != 0){
			DataStorageService.getInstance().getProxy().saveUsername(newUsername);
		}
		else
		{
			if (newUsername.length() == 0){
				text = "Please enter a username";
			}
			toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		editText.setText(""); //clear the text field
		update();
	}

	/**
	 * The user can delete usernames from their list. Tags are used 
	 * to determine which username is being deleted.
	 * 
	 * @param v the delete button
	 */
	public void remove(View v){
		int tag = (Integer) v.getTag();
		DataStorageService.getInstance().getProxy().removeUsername(tag);
		update();
	};


	public void done(View v){
		finish();
	}

	@Override
	public void update()
	{
		//set the adapter to display the list of usernames
		usernames.clear();
		DataStorageService.getInstance().getProxy().saveArray();
		usernames.addAll(DataStorageService.getInstance().getProxy().getArray());
		adapter.notifyDataSetChanged();
	}

}
