package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;



/**
 * This is the custom adapter for displaying the topics in HomeViewActivity
 *
 * @author Austin
 */

public class CustomAdapter extends BaseAdapter {


	
	private ArrayList<Viewable> data = new ArrayList<Viewable>();
	private static LayoutInflater inflater = null;


	public CustomAdapter(Context context, ArrayList<Viewable> topics) {

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
		Topic topic = (Topic) getItem(position);
		if (vi == null){

			vi = inflater.inflate(R.layout.root_comment_view, null);
			

		}
		//TODO: look into null return value for getItem
		if(topic != null)setHomeView(vi, topic);
		return vi;
	}

	
	/**
	 * Sets all the topic fields to display in HomeViewActivity
	 * 
	 * 
	 * @param vi  is the current view
	 * @param topic is the current topic being s
	 */
	public void setHomeView(View vi, Topic topic){
		
		TextView text = (TextView) vi.findViewById(R.id.title);
		text.setText(String.valueOf(topic.getTitle()));

		text = (TextView) vi.findViewById(R.id.username);
		text.setText("by " + String.valueOf(topic.getUsername()));

		/* get the GPS coordinates */
		text = (TextView) vi.findViewById(R.id.coordinates);
		//text.setText(topic.locationString());

		/* get the comment age */
		text = (TextView) vi.findViewById(R.id.age);
		Date post_time = topic.getTimestamp();
		text.setText(topic.getDateDiff(post_time, new Date()));
	
		/* count the number of comments in the topic*/
		text = (TextView) vi.findViewById(R.id.number_of_comments);
		text.setText(topic.getCommentCount() + " comments");
		
		/* show bitmap */
		ImageView imageView = (ImageView) vi.findViewById(R.id.topicImage);
		if (topic.getHasImage()) {			
			imageView.setImageBitmap(topic.getImage());
		}
		
	
	}


}
