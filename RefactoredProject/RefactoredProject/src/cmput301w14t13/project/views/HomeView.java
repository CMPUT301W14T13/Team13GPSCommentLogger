package cmput301w14t13.project.views;

import java.util.ArrayList;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CustomAdapter;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.controllers.HomeViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Root;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/* this is our main activity */
/**
 * HomeViewActivity is where the user will see a list of topics and various options
 * like adding a topic, viewing saved comments, and sorting topics. From here the user can
 * click on a topic to enter it and view its comments and/or reply to comments
 *
 * @author Austin
 *
 */
public class HomeView extends RankedHierarchicalActivity implements UpdateInterface{

	private ListView topicListview;
	private CustomAdapter displayAdapter; //adapter to display the topics
	private Menu menu; //A reference to the options menu
	protected HomeViewController controller = new HomeViewController(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		
		//set up adapter and listview
		topicListview = (ListView) findViewById(R.id.topic_listview);
		
		try {
			controller.connect();
			//controller.init(); /* this clears the server */
			controller.bind();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	public void onStop(){
		super.onStop();
        controller.unbind();
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        CommentTree ct = CommentTree.getInstance();
		ct.popRoot();
        ct.deleteView(this);
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

	@Override
	public void update() {
		CommentTree ct = CommentTree.getInstance();
		Log.w("UpdateHomeView", Boolean.toString(ct.getElement(this) == null));
		Log.w("UpdateHomeView", ct.getElement(this).toString());
		Log.w("UpdateHomeView", ct.getChildren(this).toString());
		displayAdapter = new CustomAdapter(this, ct.getChildren(this));
		topicListview.setAdapter(displayAdapter);
		CommentTree.getInstance().getCommentList(this);
		displayAdapter.notifyDataSetChanged();		
	}

	public ListView getListView()
	{
		return topicListview;
	}

	@Override
	public UpdateRank getRank() {
		return rank;
	}
}