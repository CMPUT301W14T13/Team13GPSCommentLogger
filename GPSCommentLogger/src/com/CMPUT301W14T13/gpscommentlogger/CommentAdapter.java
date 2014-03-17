package com.CMPUT301W14T13.gpscommentlogger;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;



/**
 * This is a custom adapter to display the list of comments in TopicViewActivity
 * 
 * @author Austin
 *
 */
public class CommentAdapter extends BaseAdapter {


	
	private ArrayList<Viewable> data = new ArrayList<Viewable>();
	private static LayoutInflater inflater = null;

	public CommentAdapter(Context context, ArrayList<Viewable> comments) {

		this.data = comments;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Comment getItem(int position) {

		return (Comment) data.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ImageButton replyButton;
		Button editButton;
		Comment comment = this.getItem(position);
		ViewHolder holder = null;
		CommentLogger cl = CommentLoggerApplication.getCommentLogger();

		if (vi == null){

			vi = inflater.inflate(R.layout.comment_view, null);
			holder = new ViewHolder();

			holder.username = (TextView) vi.findViewById(R.id.comment_username);
			holder.commentText = (TextView) vi.findViewById(R.id.commentText);
		
			vi.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		/* show bitmap */
		ImageView imageView = (ImageView) vi.findViewById(R.id.commentImage);
		if (comment.getHasImage()) {
			imageView.setImageBitmap(comment.getImage());
		}
		
		replyButton = (ImageButton) vi.findViewById(R.id.comment_reply_button);
		replyButton.setTag(position); //gives a unique tag for identifying comments

		editButton = (Button) vi.findViewById(R.id.comment_edit_button);
		editButton.setTag(position); //gives a unique tag for identifying comments
		
		//Hide the edit button if it's not the user's comment
		
		if (!cl.getCurrentUsername().equals(comment.getUsername())){
			editButton.setVisibility(View.INVISIBLE);
		}	
		
		holder.indentLevel = comment.getIndentLevel();
		setIndentView(vi, holder.indentLevel, position);
		holder.username.setText("Reply from: " + String.valueOf(data.get(position).getUsername()));
		holder.commentText.setText(String.valueOf(data.get(position).getCommentText()));

		return vi;
	}



	public static class ViewHolder {
		public TextView username;
		public TextView commentText;
		public int indentLevel;
	}

	/**
	 * Sets text fields in the comments
	 * 
	 * @param vi  the current view
	 * @param position  the position of the comment
	 */
	public void setTopicView(View vi, int position){

		TextView text = (TextView) vi.findViewById(R.id.comment_username);
		text.setText("Reply from: " + String.valueOf(data.get(position).getUsername()));

		text = (TextView) vi.findViewById(R.id.commentText);
		text.setText(String.valueOf(data.get(position).getCommentText()));
		
		
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


