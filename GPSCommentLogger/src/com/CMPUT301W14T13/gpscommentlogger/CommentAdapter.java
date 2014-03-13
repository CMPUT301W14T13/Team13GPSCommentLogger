package com.CMPUT301W14T13.gpscommentlogger;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.Comment;
import com.CMPUT301W14T13.gpscommentlogger.model.Viewable;


/*
 * This is the custom adapter for the comment listview when viewing a topic
 *
 */
public class CommentAdapter extends BaseAdapter {


	private Context context;
	private ArrayList<Viewable> data = new ArrayList<Viewable>();
	private static LayoutInflater inflater = null;
	private String currentUsername = "";

	public CommentAdapter(Context context, ArrayList<Viewable> comments, String username) {

		this.context = context;
		this.data = comments;
		this.currentUsername = username;
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
		Button replyButton;
		Button editButton;
		Comment comment = this.getItem(position);
		ViewHolder holder = null;

		if (vi == null){

			vi = inflater.inflate(R.layout.comment_view, null);
			holder = new ViewHolder();

			holder.username = (TextView) vi.findViewById(R.id.comment_username);
			holder.commentText = (TextView) vi.findViewById(R.id.commentText);
		
			//Hide the edit button if it's not the user's comment
			
			
			vi.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		replyButton = (Button) vi.findViewById(R.id.comment_reply_button);
		replyButton.setTag(position); //gives a unique tag for identifying comments

		editButton = (Button) vi.findViewById(R.id.comment_edit_button);
		editButton.setTag(position); //gives a unique tag for identifying comments
		
		System.out.println(currentUsername);
		if (!currentUsername.equals(comment.getUsername())){
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


	public void setTopicView(View vi, int position){

		TextView text = (TextView) vi.findViewById(R.id.comment_username);
		text.setText("Reply from: " + String.valueOf(data.get(position).getUsername()));

		text = (TextView) vi.findViewById(R.id.commentText);
		text.setText(String.valueOf(data.get(position).getCommentText()));
	}

	public void setIndentView(View vi, int indentLevel, int position){

		int indent = indentLevel; 



		//indent = comment.getIndentLevel();

		View[] indentViews = new View[] {
				vi.findViewById(R.id.left_indent1),
				vi.findViewById(R.id.left_indent2),
				vi.findViewById(R.id.left_indent3),
				vi.findViewById(R.id.left_indent4),
				vi.findViewById(R.id.left_indent5),

		};
		for (int i = 0; i < indent && i < indentViews.length; i++) {

			indentViews[i].setVisibility(View.VISIBLE);


		}

		//remove unnecessary indent lines otherwise they will all show
		for (int i = indent; i < indentViews.length; i++) {
			indentViews[i].setVisibility(View.GONE);
		}

	}
}


