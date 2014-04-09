package cmput301w14t13.project.views;

import java.util.ArrayList;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CustomAdapter;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.controllers.HomeViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import cmput301w14t13.project.services.LocationSelection;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.OnNavigationListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/* this is our main activity */
/**
 * HomeViewActivity is where the user will see a list of topics and various options 
 * like adding a topic, viewing saved comments, and sorting topics. From here the user
 * can click on a topic to enter it and view its comments and/or reply to comments
 * @author  Austin
 */
public class HomeView extends RankedHierarchicalActivity implements UpdateInterface, OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";	
	private ListView topicListview;
	private CustomAdapter displayAdapter; //adapter to display the topics
	private Menu menu; //A reference to the options menu
	protected HomeViewController controller = new HomeViewController(this);


	/**
	 * This method loads up a ListView onto the screen
	 * then initializes the controller to handle the list of topics
	 * to view, as well as clicks on topis and elements
	 */


	Location location = new Location("default");


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);

		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);

		try {
			controller.connect();
			//controller.init();
			controller.bind();
			initializeActionBar();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();
		/*try
		{
			controller.bind();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}*/
	}


	@Override
	public void onResume(){
		super.onResume();
		try {
			controller.resume();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause(){
		super.onPause();
		CommentTree.getInstance().deleteView(this);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		controller.unbind();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_action_bar, menu);

		this.menu = menu;

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		return (controller.selectOptions(item))?true:super.onOptionsItemSelected(item);
	}

	public Menu getMenu() {
		return menu;
	}

	/**
	 * Sets up a dropdown list in the actionbar for sorting
	 * 
	 */
	private void initializeActionBar()
	{
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
				// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
					getString(R.string.sort1),
					getString(R.string.sort2),
					getString(R.string.sort3),
					getString(R.string.sort4),
					getString(R.string.sort5),
					getString(R.string.sort6),
				}), this);
	}


	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}


	@Override

	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		controller.onNavigationItemSelected(itemPosition, itemId);
		return true;
	}






public void openMap()
{
	controller.openMap();
}

/**
 * Used to get the location chosen during the sortByGivenLocation function
 */
@SuppressLint("NewApi")
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data)
{
	if (requestCode == 0){
		if (resultCode == Activity.RESULT_OK){
			double latitude = data.getDoubleExtra("lat", LocationSelection.getInstance().getLocation().getLatitude());
			double longitude = data.getDoubleExtra("lon", LocationSelection.getInstance().getLocation().getLongitude());
			location = new Location("default");
			location.setLongitude(longitude);
			location.setLatitude(latitude);

		}
	}
}

/**
 * updates the list view with the most recent list of topics.
 */
@Override
public void update() {
	CommentTree ct = CommentTree.getInstance();
	displayAdapter = new CustomAdapter(this, ct.getChildren(this));
	topicListview.setAdapter(displayAdapter);
	displayAdapter.notifyDataSetChanged();		
}

public ListView getListView()
{
	return topicListview;
}

public void setListView(ListView topicListview)
{
	this.topicListview = topicListview;
}

@Override
public UpdateRank getRank() {
	return rank;
}
}