package com.CMPUT301W14T13.gpscommentlogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;


/*
 * This is the custom adapter for the various listviews throughout the app
 *
 */
public class CustomAdapter extends BaseAdapter {


	private Context context;
	private ArrayList<Viewable> data = new ArrayList<Viewable>();
	private static LayoutInflater inflater = null;


	public CustomAdapter(Context context, ArrayList<Viewable> topics) {

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

	/* gets the difference between two dates and corrects for time resolution */
	/* maybe make this a comment/topic method */
	private String getDateDiff(Date previous, Date current) {
	    long diffInMillies = current.getTime() - previous.getTime();
	    
	    if (diffInMillies >= 0 && diffInMillies < 60000)
	    	return String.valueOf(TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" seconds ago");
	    else if (diffInMillies >= 60000 && diffInMillies < 3600000)
	    	return String.valueOf(TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" minutes ago");
	    else if (diffInMillies >= 3600000 && diffInMillies < 24*3600000)
	    	return String.valueOf(TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" hours ago");
	    else if (diffInMillies >= 24*3600000 && diffInMillies < 24*30*3600000)
	    	return String.valueOf(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS)).concat(" days ago");
	    else if (diffInMillies >= 24*30*3600000 && diffInMillies < 24*30*12*3600000)
	    	return String.valueOf((long) Math.ceil(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS)/31)).concat(" months ago");
	    else
	    	return String.valueOf((long) Math.ceil(TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS)/365)).concat(" years ago");
	    
	}
	
	public void setHomeView(View vi, int position){
		TextView text = (TextView) vi.findViewById(R.id.title);
		text.setText(String.valueOf(data.get(position).getTitle()));

		text = (TextView) vi.findViewById(R.id.username);
		text.setText("by " + String.valueOf(data.get(position).getUsername()));

		/* get the GPS coordinates */
		text = (TextView) vi.findViewById(R.id.coordinates);
		
		text.setText(this.data.get(position).locationString());

		/* get the comment age */
		text = (TextView) vi.findViewById(R.id.age);
		Date post_time = this.data.get(position).getTimestamp();
		text.setText(this.getDateDiff(post_time, new Date()));
	
		/* count the number of comments in the topic*/
		text = (TextView) vi.findViewById(R.id.number_of_comments);
		text.setText(String.valueOf(this.data.get(position).getChildren().size()).concat(" comments"));
		
	
	}


}
