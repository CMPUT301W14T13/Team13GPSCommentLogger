package cmput301w14t13.project.models.content;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * This is the root view that will display
 * all top level comments.
 */
public class Root extends CommentTreeElement{
	
	public Root()
	{
		super("ROOT");
	}
	
	public Root(String ID, String username, Bitmap picture, Date timestamp,
			String commentText) {
		super(ID, username, picture, timestamp, commentText);
	}

}
