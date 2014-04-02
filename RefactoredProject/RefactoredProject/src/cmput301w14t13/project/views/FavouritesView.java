package cmput301w14t13.project.views;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CommentAdapter;
import cmput301w14t13.project.auxilliary.adapters.CustomAdapter;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.auxilliary.tools.Preferences;
import cmput301w14t13.project.controllers.FavouritesViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;

public class FavouritesView extends RankedHierarchicalActivity implements UpdateInterface
{
	
	private ListView favouritesListview;
	

	private CommentAdapter displayAdapter; //adapter to display the topics
	
	private Menu menu; //A reference to the options menu

	protected FavouritesViewController controller = new FavouritesViewController(this);
	
	private Preferences prefs;  

	private ArrayList<CommentTreeElement> favouritesTopics;
	
	
	
	final private String topicSaves = "topics.sav";
	final private String commentSaves = "comments.sav";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_view);
		favouritesListview = (ListView) findViewById(R.id.favourites_listview);
		
		prefs = new Preferences(getApplicationContext());
		
		favouritesTopics = prefs.loadCommentFile(commentSaves);
		
		Log.d("load_fav",favouritesTopics.toString());
		
		
		
		
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
	public void update() {
		CommentTree ct = CommentTree.getInstance();
		displayAdapter = new CommentAdapter(this, favouritesTopics);
		favouritesListview.setAdapter(displayAdapter);
		displayAdapter.notifyDataSetChanged();		
	}
	
	public ListView getListView()
	{
		return favouritesListview;
	}
	
	public void setListView(ListView listview)
	{
		this.favouritesListview = listview;
	}
	
	/**
	 * @return
	 * @uml.property  name="menu"
	 */
	public Menu getMenu() {
		return menu;
	}
	
	@Override
	public UpdateRank getRank() {
		return rank;
	}
}
