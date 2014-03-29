package cmput301w14t13.project.auxilliary.adapters;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cmput301w14t13.project.R;
import cmput301w14t13.project.models.content.CommentTreeElement;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This is a custom adapter to display the list of topics (aka. top level comments)
 * in HomeViewActivity.
 * Topic objects are stored in an ArrayList, which this adapter accesses to display.
 * This adapter provides functionality such as returning the number of comments in the
 * list, accessing specific comment objects by indexing them.
 * 
 * @author Austin
 */

public class CustomAdapter extends BaseAdapter {

	private ArrayList<CommentTreeElement> data = new ArrayList<CommentTreeElement>();
	private static LayoutInflater inflater = null;


	public CustomAdapter(Context context, ArrayList<CommentTreeElement> topics) {
		this.data = topics;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public CommentTreeElement getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * This function is responsible for creating a view for one topic object.
	 * 
	 * This function uses the root_comment_view layout, which has an
	 * empty field specified a topic object, and fills it with 
	 * the topic given the position in the list of topics. 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		CommentTreeElement topic = getItem(position);
		if (vi == null){

			vi = inflater.inflate(R.layout.root_comment_view, null);
			

		}
		//TODO: look into null return value for getItem
		if(topic != null)setHomeView(vi, topic);
		return vi;
	}

	
	/**
	 * This function looks up empty fields from the layout,
	 * such as title, username, location, image attachement, age,
	 * and the list of comments (replies to the topic),
	 * and fills the empty field with the corresponding attributes
	 * from a topic view. 
	 * 
	 * 
	 * @param vi  is the current view
	 * @param topic is the current topic being s
	 */
	public void setHomeView(View vi, CommentTreeElement topic){
		
		TextView text = (TextView) vi.findViewById(R.id.title);
		text.setText(String.valueOf(topic.getTitle()));

		text = (TextView) vi.findViewById(R.id.username);
		text.setText("by " + String.valueOf(topic.getUsername()));

		/* get the GPS coordinates */
		text = (TextView) vi.findViewById(R.id.coordinates);
		text.setText(topic.locationString());

		/* get the comment age */
		text = (TextView) vi.findViewById(R.id.age);
		Date post_time = topic.getTimestamp();
		text.setText(topic.getDateDiff(post_time, new Date()));
	
		/* count the number of comments in the topic*/
		text = (TextView) vi.findViewById(R.id.number_of_comments);
		text.setText(topic.getNumberOfChildren() + " comments");
		
		/* show bitmap */
		ImageView imageView = (ImageView) vi.findViewById(R.id.topicImage);
		if (topic.getHasImage()) {			
			imageView.setImageBitmap(topic.getImage());
		}
		
	
	}


}
