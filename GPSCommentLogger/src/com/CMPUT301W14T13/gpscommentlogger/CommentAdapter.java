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
		
		
		    public CommentAdapter(Context context, ArrayList<Viewable> comments) {
		       
		        this.context = context;
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
				Button button;
				Comment comment = this.getItem(position);
				
		        if (vi == null){
		        	
		        	vi = inflater.inflate(R.layout.comment_view, null);
		        	setTopicView(vi, position);
		        	
		        	button = (Button) vi.findViewById(R.id.comment_reply_button);
		        	button.setTag(position); //gives a unique tag for identifying comments
		        	
		        	button = (Button) vi.findViewById(R.id.comment_edit_button);
		        	button.setTag(position); //gives a unique tag for identifying comments
		        	
		        	setIndentView(vi, comment, position);
		         }
		        
		       
		        return vi;
		}



		
		public void setTopicView(View vi, int position){
			
			TextView text = (TextView) vi.findViewById(R.id.comment_username);
	        text.setText("Reply from: " + String.valueOf(data.get(position).getUsername()));
	        
	        text = (TextView) vi.findViewById(R.id.commentText);
	        text.setText(String.valueOf(data.get(position).getCommentText()));
		}
		
		public void setIndentView(View vi, Comment comment, int position){
			
			int indent = comment.getIndentLevel();
			
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


