package cmput301w14t13.project.models.tasks;

import cmput301w14t13.project.auxilliary.interfaces.RankedHierarchicalActivity;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.services.DataStorageService;

/**
 * Task for testing local favorites
 * topic listings.
 *
 */
public class MyFavouritesLocalTask extends Task {

	public MyFavouritesLocalTask(DataStorageService esc, CommentTreeElement ele, RankedHierarchicalActivity activity) {
		super(esc, null, ele, activity);
	}

	
	private CommentTreeElement trimTree(CommentTreeElement ele){
		ele.getChildren().clear();
		return ele;
	}
	
	@Override
	public String doTask() {
		if (this.getObj() instanceof Topic){
			this.obj = trimTree(this.getObj());
		}
		else if (this.getObj() instanceof Comment){
			this.obj = this.esc.getProxy().getTopicOfFavouriteComment(this.getObj());
		}

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
