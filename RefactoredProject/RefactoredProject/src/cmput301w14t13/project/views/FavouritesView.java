package cmput301w14t13.project.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CustomAdapter;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;
import cmput301w14t13.project.controllers.FavouritesViewController;

public class FavouritesView extends Activity implements UpdateInterface
{
	
	private ListView favouritesListview;
	private CustomAdapter displayAdapter; //adapter to display the topics
	private Menu menu; //A reference to the options menu
	protected FavouritesViewController controller = new FavouritesViewController(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourites_view);

	}
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
	}
	@Override
	public UpdateRank getRank() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListView getListView()
	{
		return favouritesListview;
	}
}
