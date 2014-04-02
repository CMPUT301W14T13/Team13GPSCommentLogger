package cmput301w14t13.project.views;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CustomAdapter;
import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.controllers.FavouritesViewController;
import cmput301w14t13.project.models.CommentTree;

public class FavouritesView extends RankedHierarchicalActivity implements UpdateInterface
{
	
	private ListView favouritesListview;
	private CustomAdapter displayAdapter; //adapter to display the topics
	private Menu menu; //A reference to the options menu
	protected FavouritesViewController controller = new FavouritesViewController(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_view);
		favouritesListview = (ListView) findViewById(R.id.favourites_listview);
		
		/* Inform the view what controller controls it!*/
	//	controller.connect();
	}
	
	@Override
	public void update() {
		CommentTree ct = CommentTree.getInstance();
		displayAdapter = new CustomAdapter(this, ct.getCommentList(this));
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
	
	public Menu getMenu() {
		return menu;
	}
	
	@Override
	public UpdateRank getRank() {
		return rank;
	}
}
