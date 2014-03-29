package cmput301w14t13.project.auxilliary.adapters;
import java.util.ArrayList;

import cmput301w14t13.project.R;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a custom adapter to display the list of comments in TopicViewActivity.
 * Comment objects are stored in an ArrayList, which this adapter accesses to display.
 * This adapter provides functionality such as returning the number of comments in the
 * list, accessing specific comment objects by indexing them.
 * 
 * @author Austin
 *
 */
public class CommentAdapter extends BaseAdapter {
	
	private ArrayList<CommentTreeElement> data = new ArrayList<CommentTreeElement>();
	private static LayoutInflater inflater = null;
	
	public CommentAdapter(Context context, ArrayList<CommentTreeElement> comments) {

		this.data = comments;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * This function returns the number of comments in the ArrayList.
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * This function, given an index (position in the list),
	 * return the comment object at that index in the ArrayList. 
	 * 
	 * This is the main way of accessing specific comments in the  
	 * list in order to perform other functions such as editing 
	 * a comment's attributes (attachment, title, username).
	 */
	@Override
	public CommentTreeElement getItem(int position) {

		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	 * This function is responsible for creating a view for one comment object.
	 * 
	 * This function uses the comment_view layout, which has fields specified
	 * for title, username, text, location, text, and edit and reply buttons,
	 * and fills them with information from the comment at a given index (int position). 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ImageButton replyButton;
		Button editButton;
		CommentTreeElement comment = this.getItem(position);
		ViewHolder holder = null;
		CommentTree ct = CommentTree.getInstance();

		if (vi == null){

			vi = inflater.inflate(R.layout.comment_view, null);
			holder = new ViewHolder();

			holder.username = (TextView) vi.findViewById(R.id.comment_username);
			holder.commentText = (TextView) vi.findViewById(R.id.commentText);
			holder.coordinates = (TextView) vi.findViewById(R.id.comment_coordinates);
			vi.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		/* show bitmap */
		ImageView imageView = (ImageView) vi.findViewById(R.id.commentImage);
		imageView.setTag(position);
		if (comment.getHasImage()) {
			imageView.setImageBitmap(comment.getImage());
		}
		
		replyButton = (ImageButton) vi.findViewById(R.id.comment_reply_button);
		replyButton.setTag(position); //gives a unique tag for identifying comments

		editButton = (Button) vi.findViewById(R.id.comment_edit_button);
		editButton.setTag(position); //gives a unique tag for identifying comments
		
		Button saveButton = (Button) vi.findViewById(R.id.saveComment);
		saveButton.setTag(position);
		/*
		 * Hide the edit button if it's not the user's comment.
		 * Show the edit button if it is the user's comment.
		 * Currently, only checks if the usernames are equal
		 */
	
		if (!ct.getCurrentUsername().equals(comment.getUsername())){
			editButton.setVisibility(View.INVISIBLE);
		}	
		else{
			editButton.setVisibility(View.VISIBLE);
		}
		
		holder.indentLevel = comment.getIndentLevel();
		setIndentView(vi, holder.indentLevel, position);
		holder.username.setText("Reply from: " + String.valueOf(comment.getUsername()));
		holder.commentText.setText(String.valueOf(comment.getCommentText()));
		holder.coordinates.setText("Posted from: " + comment.locationString());
		
		return vi;
	}



	/**
	 * ViewHolder is a container for a comment's main
	 * main attributes: username, text, location, and
	 * level of indentation in the view in relation
	 * to parent comments.
	 * 
	 * This is used by the view to reference attributes of
	 * a comment that is being displayed.
	 */
	public static class ViewHolder {
		public TextView username;
		public TextView commentText;
		public int indentLevel;
		public TextView coordinates;

	}

	
	/**
	 * Modified from the redditisfun app: 
	 * https://github.com/talklittle/reddit-is-fun/blob/master/src/com/andrewshu/android/reddit/comments/CommentsListActivity.java
	 * 
	 * This is the function that handles the display of indent lines
	 * for comment replies. It uses the indent level saved in the 
	 * comment and displays the appropriate views to show the indent
	 * lines. It then removes the indent lines past the indent or else
	 * all lines would display.
	 * 
	 * 
	 * @param vi  the current view
	 * @param indentLevel  the indent level of the comment
	 * @param position  the position of the comment in the array
	 */
	public void setIndentView(View vi, int indentLevel, int position){

		int indent = indentLevel; 

		View[] indentViews = new View[] {
				vi.findViewById(R.id.left_indent1),
				vi.findViewById(R.id.left_indent2),
				vi.findViewById(R.id.left_indent3),
				vi.findViewById(R.id.left_indent4),
				vi.findViewById(R.id.left_indent5),

		};
		
		//display lines up until the indent point
		for (int i = 0; i < indent && i < indentViews.length; i++) {

			indentViews[i].setVisibility(View.VISIBLE);

		}

		//remove unnecessary indent lines otherwise they will all show
		for (int i = indent; i < indentViews.length; i++) {
			indentViews[i].setVisibility(View.GONE);
		}

	}
}


