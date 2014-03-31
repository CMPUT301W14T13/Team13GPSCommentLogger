package cmput301w14t13.project.views;

import java.util.ArrayList;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CommentAdapter;
import cmput301w14t13.project.auxilliary.adapters.CustomAdapter;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.controllers.HomeViewController;
import cmput301w14t13.project.controllers.TopicViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
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
public class TopicView extends RankedHierarchicalActivity implements UpdateInterface{
	
	private ListView commentListview;
	private CommentAdapter adapter; //adapter to display the comments
	private TopicViewController controller = new TopicViewController(this);
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_view);
        
		CommentTree.getInstance().addView(this);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		update();
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        CommentTree cl = CommentTree.getInstance();
        cl.deleteView(this);
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

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void reply(View v) throws InterruptedException{
		controller.reply(v);
	}
	
	public void edit(View v) throws InterruptedException{
		controller.edit(v);
	}
	
	@Override
	public void update()
	{
        CommentTree cl = CommentTree.getInstance();
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

}