package cmput301w14t13.project.views;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CommentAdapter;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.controllers.TopicViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.DataStorageService;
import cmput301w14t13.project.services.LocationSelection;

/* this is our main activity */
/**
 * Where the user Views the Topic and all of its replies. The user can 
 * reply to Topics or Comments, as well as edit them, Sort the Comments 
 * by different parameters, select a username from a list or create a 
 * new one,set the current topic or any of its replies as favorites, 
 * and open a map showing the locations of the Topic and all its replies
 * 
 * @author  Austin
 */
public class TopicView extends RankedHierarchicalActivity implements UpdateInterface, OnNavigationListener{

	private ListView commentListview;

	private CommentAdapter adapter; //adapter to display the comments

	private TopicViewController controller = new TopicViewController(this);

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	Location location = new Location("default");
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_view);  
		initializeActionBar();
	}

	@Override
	public void onResume(){
		super.onResume();
		CommentTree.getInstance().addView(this);
		update();
	}

	@Override 
	public void onPause()
	{
		super.onPause();
		CommentTree.getInstance().deleteView(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try
		{
			CommentTree.getInstance().popFromCommentStack(this);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.topic_action_bar, menu);
		return super.onCreateOptionsMenu(menu);

	}

	/**
	 * There will be a select username option on the action bar
	 * which takes the user to an activity to manage their
	 * usernames.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			
		case R.id.action_select_username:
			controller.selectUsername();
			return true;
			
		case R.id.action_help:
			controller.helpPage();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

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
	
	public void openMap(View v){
		controller.OpenMap();
	}

	public void reply(View v) throws InterruptedException{
		controller.reply(v);
	}

	public void edit(View v) throws InterruptedException{
		controller.edit(v);
	}

	/**
	 * This method is invoked when the user wishes to save the topic for later viewing. 
	 * This instructs the topic controller to save the topic to the user's list of favourites.
	 * 
	 * @param v The current view of the topic.
	 * @throws InterruptedException
	 */
	
	/*
	public void  saveTopic(View v) throws InterruptedException{

		controller.saveTopic(v);
	}

	*/
	
	/**
	 * When user clicks the save button in a comment we use DataStorageService to favourite the specified comment
	 * @param v
	 */
	public void favouriteComment(View v){
		CommentTree ct = CommentTree.getInstance();
		Toast.makeText(getApplicationContext(), "Comment Favourited!",Toast.LENGTH_SHORT).show();
		CommentTreeElement comment = ct.getCommentList(this).get((Integer) v.getTag());
		DataStorageService.getInstance().getProxy().startSaveFavourites(comment);
	}
	
	/**
	 * When user clicks the save button in the Topic we use DataStorageService to favorite the specified topic
	 * @param v 
	 */
	public void saveTopic(View v){
		CommentTree ct = CommentTree.getInstance();
		Toast.makeText(getApplicationContext(), "Topic Favourited!",Toast.LENGTH_SHORT).show();
		CommentTreeElement topic = ct.getElement(this);	
		DataStorageService.getInstance().getProxy().startSaveFavourites(topic);
	}
	
	/**
	 * Updates the commentListView with the most recent comments made
	 */
	@Override
	public void update()
	{
		CommentTree cl = CommentTree.getInstance();
		cl.update(this);
		adapter = new CommentAdapter(this, cl.getCommentList(this));
		commentListview = (ListView) findViewById(R.id.comment_list);
		commentListview.setAdapter(adapter);
		controller.fillTopicLayout();
		adapter.notifyDataSetChanged();
	}

	@Override
	public UpdateRank getRank() {
		return rank;
	}

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

}