package com.CMPUT301W14T13.gpscommentlogger;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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
		public Object getItem(int position) {
		
		return data.get(position);
		}
		
		@Override
		public long getItemId(int position) {
		
		return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
				View vi = convertView;
				Button button;
				
		        if (vi == null){
		        	
		        	vi = inflater.inflate(R.layout.comment_view, null);
		        	setTopicView(vi, position);
		        	
		        	button = (Button) vi.findViewById(R.id.comment_reply_button);
		        	button.setTag(position); //gives a unique tag for identifying comments
		         }
		        
		       
		        return vi;
		}



		
		public void setTopicView(View vi, int position){
			
			TextView text = (TextView) vi.findViewById(R.id.comment_username);
	        text.setText("Reply from: " + String.valueOf(data.get(position).getUsername()));
	        
	        text = (TextView) vi.findViewById(R.id.commentText);
	        text.setText(String.valueOf(data.get(position).getCommentText()));
		}
	}


