package cmput301w14t13.project.models.content;

import java.util.Date;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * This class is the same as CommentTreeElement. The difference being
 * that this one is designed to hold a View which it updates as the
 * comment object is mutated.
 * 
 * This can be used in activities where comments are created or edited,
 * where the view will be referenced by this class, and the view is
 * updated when an attribute in this class is modified.
 * **/
public class CommentTreeElementSubmission extends CommentTreeElement {

	public CommentTreeElementSubmission(Parcel in) {
		super(in);
		// TODO Auto-generated constructor stub
	}

	public CommentTreeElementSubmission(String ID, String username,
			Bitmap picture, Date timestamp, String commentText) {
		super(ID, username, picture, timestamp, commentText);
		// TODO Auto-generated constructor stub
	}

	public CommentTreeElementSubmission(String ID) {
		super(ID);
		// TODO Auto-generated constructor stub
	}

}
