package cmput301w14t13.project.views;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.auxilliary.interfaces.UpdateRank;

public class FavouritesView extends Activity implements UpdateInterface
{
	
	private ListView favouritesListview;
	
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
