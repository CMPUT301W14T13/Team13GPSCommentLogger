package cmput301w14t13.project.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

/* this is our main activity */
/**
 * HomeViewActivity is where the user will see a list of topics and various options like adding a topic, viewing saved comments, and sorting topics. From here the user can click on a topic to enter it and view its comments and/or reply to comments
 * @author  Austin
 */
public class TopicView extends RankedHierarchicalActivity implements UpdateInterface{

	private ListView commentListview;

	private CommentAdapter adapter; //adapter to display the comments

	private TopicViewController controller = new TopicViewController(this);

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_view);  
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
		default:
			return super.onOptionsItemSelected(item);
		}
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
	public void favouriteComment(View v){
		CommentTree ct = CommentTree.getInstance();
		Toast.makeText(getApplicationContext(), "Comment Favourited!",Toast.LENGTH_SHORT).show();
		CommentTreeElement comment = ct.getCommentList(this).get((Integer) v.getTag());
		DataStorageService.getInstance().getProxy().startSaveFavourites(comment);
	}

	public void saveTopic(View v){
		CommentTree ct = CommentTree.getInstance();
		Toast.makeText(getApplicationContext(), "Topic Favourited!",Toast.LENGTH_SHORT).show();
		CommentTreeElement topic = ct.getElement(this);	
		DataStorageService.getInstance().getProxy().startSaveFavourites(topic);
	}
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


}