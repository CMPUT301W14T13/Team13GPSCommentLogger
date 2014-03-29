package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.services.DataStorageService;

/**
 * Task for testing local favorites
 * topic listings.
 *
 */
public class MyFavouritesLocalTask extends Task {

	public MyFavouritesLocalTask(DataStorageService esc, String searchTerm) {
		super(esc, searchTerm, null);
	}

	@Override
	public String doTask() {
		this.obj = this.esc.getProxy().getFavourite(this.searchTerm);
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
