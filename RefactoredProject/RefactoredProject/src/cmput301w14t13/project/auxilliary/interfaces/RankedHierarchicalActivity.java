package cmput301w14t13.project.auxilliary.interfaces;

import cmput301w14t13.project.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * @author  mjnichol
 */
public abstract class RankedHierarchicalActivity extends Activity {

	/**
	 * @uml.property  name="rank"
	 * @uml.associationEnd  
	 */
	protected UpdateRank rank;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rank = new UpdateRank(getIntent().getIntExtra("updateRank", 0));
	}
}
