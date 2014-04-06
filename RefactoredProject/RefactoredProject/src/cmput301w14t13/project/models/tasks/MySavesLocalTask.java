package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.services.DataStorageService;

/**
 * Task for testing local saved
 * topic listings.
 *
 */
public class MySavesLocalTask extends Task {

	public MySavesLocalTask(DataStorageService esc, String searchTerm, RankedHierarchicalActivity activity) {
		super(esc, searchTerm, null, activity);
	}

	@Override
	public String doTask() {
		this.obj = this.esc.getProxy().getData(this.searchTerm);
		if(this.obj == null)
		{
			return "failure";
		}
		else
		{
			return "success";
		}
	}

}
