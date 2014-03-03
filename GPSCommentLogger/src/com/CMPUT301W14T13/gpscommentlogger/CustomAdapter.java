package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.Topic;


/*
* This is the custom adapter for the various listviews throughout the app
*
*/
public class CustomAdapter extends BaseAdapter {


	private Context context;
	private ArrayList<Topic> data = new ArrayList<Topic>();
	private static LayoutInflater inflater = null;
	
	
	    public CustomAdapter(Context context, ArrayList<Topic> topics) {
	       
	        this.context = context;
	        this.data = topics;
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
	        if (vi == null){
	        	
	        	vi = inflater.inflate(R.layout.root_comment_view, null);
	        	setHomeView(vi, position);
	        	
	         }
	        
	        /*
	         * Sets the colour of the background for the listview rows, alternating every time.
	         * Change as needed
	         */
	        //if (position % 2 == 0){
	        //	vi.setBackgroundColor(Color.argb(255,0,0,0)); //black
	        //}
	        //else{
	        //	vi.setBackgroundColor(Color.argb(255, 128, 128, 128)); //grey
	        //}
	       
	        return vi;
	}


	public void setHomeView(View vi, int position){
		    TextView text = (TextView) vi.findViewById(R.id.title);
	        text.setText(String.valueOf(data.get(position).getTitle()));
	        
	        text = (TextView) vi.findViewById(R.id.username);
	        text.setText("by " + String.valueOf(data.get(position).getUsername()));
	        
	        text = (TextView) vi.findViewById(R.id.coordinates);
	        text.setText("coordinates");
	        
	        text = (TextView) vi.findViewById(R.id.age);
	        text.setText(String.valueOf("age"));
	        
	        text = (TextView) vi.findViewById(R.id.number_of_comments);
	        text.setText(String.valueOf("number of comments"));
	        
	}
	
	
}
