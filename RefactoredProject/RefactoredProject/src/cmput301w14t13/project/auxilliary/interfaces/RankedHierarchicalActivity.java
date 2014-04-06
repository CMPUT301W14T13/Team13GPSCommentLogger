package cmput301w14t13.project.auxilliary.interfaces;

import cmput301w14t13.project.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public abstract class RankedHierarchicalActivity extends Activity {

	protected UpdateRank rank;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rank = new UpdateRank(getIntent().getIntExtra("updateRank", 0));
	}
	
    public UpdateRank getRank()
    {
    	return rank;
    }
    
    
}
