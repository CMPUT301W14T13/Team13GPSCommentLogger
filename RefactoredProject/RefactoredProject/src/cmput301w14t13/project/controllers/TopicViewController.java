package cmput301w14t13.project.controllers;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import auxilliary.other.NavigationItems;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.services.NetworkReceiver;
import cmput301w14t13.project.views.HelpView;
import cmput301w14t13.project.views.TopicView;
import cmput301w14t13.project.views.submissions.EditCommentSubmissionView;
import cmput301w14t13.project.views.submissions.EditTopicSubmissionView;
import cmput301w14t13.project.views.submissions.ReplyToCommentCommentSubmissionView;
import cmput301w14t13.project.views.submissions.ReplyToTopicCommentSubmissionView;


/**
 * TopicViewActivity is where the user can view the topic that they selected from 
 * HomeViewActivity. Here they can comment, edit their comments, and select a global
 * username.
 * 
 * @author  Austin
 */
public class TopicViewController implements AsyncProcess
{

	private TopicView topicView;

	Location location = new Location("default");
	
	public TopicViewController(TopicView topicView) {
		this.topicView = topicView;
	}

	public void selectUsername(){
		Intent intent = new Intent(topicView, SelectUsernameController.class);
		topicView.startActivity(intent);
	}
	
	/**
	 * Method starts MapViewController if online or
	 * opens a dialog fragment if offline to get
	 * a location input from the user.  
	 * 
	 * This is called by sort functions when the user 
	 * selects to sort topics by proximity to a given 
	 * location.
	 */

	/**
	 * This function starts the activity
	 * for the help page. It is called
	 * when it's clicked from the options
	 * menu inside HomeView
	 */
	public void helpPage() {
		Intent intent = new Intent(topicView, HelpView.class);
		topicView.startActivity(intent);
	}

	public void OpenMap(){
		CommentTree commentTree = CommentTree.getInstance();
		CommentTreeElement topic = commentTree.getElement(topicView);
		Intent map = new Intent(topicView, MapViewController.class);
		map.putExtra("lat", topic.getGPSLocation().getLatitude()); 
		map.putExtra("lon", topic.getGPSLocation().getLongitude());
		map.putExtra("canSetMarker", 0);// for editing  location
		MapViewController.setTopicView(topicView);
		topicView.startActivity(map);

	}

	/**
	 * This method fills up the various fields in TopicView
	 * with the proper information from the topic such as 
	 * username, location, Title, etc.
	 * 
	 * It is used When TopicView starts to show the user all 
	 * the correct information from the current Topic
	 * 
	 */
	public void fillTopicLayout(){

		CommentTreeElement currentTopic = CommentTree.getInstance().getElement(topicView);

		TextView text = (TextView) topicView.findViewById(R.id.topic_username);
		text.setText(currentTopic.getUsername());

		text = (TextView) topicView.findViewById(R.id.topic_comment);
		text.setText(currentTopic.getCommentText());

		text = (TextView) topicView.findViewById(R.id.topic_title);
		text.setText(currentTopic.getTitle());

		/* having trouble getting the coordinates, why is this?*/
		text = (TextView) topicView.findViewById(R.id.coordinates);
		text.setText(currentTopic.locationString());

		Date post_time = currentTopic.getTimestamp();
		text = (TextView) topicView.findViewById(R.id.age);
		
		String num = currentTopic.getDateDiff(post_time, new Date()).substring(0, 1);
		int time = Integer.parseInt(num);
		
		if (time > 1){
			text.setText(currentTopic.getDateDiff(post_time, new Date()) + "s ago");
		}
		else{
			text.setText(currentTopic.getDateDiff(post_time, new Date()) + " ago");
		}
		

		text = (TextView) topicView.findViewById(R.id.number_of_comments);
		int count = currentTopic.getNumberOfChildren();
		if (count > 1){
			text.setText(String.valueOf(currentTopic.getNumberOfChildren()) + " comments");
		}
		else{
			text.setText(String.valueOf(currentTopic.getNumberOfChildren()) + " comment");
		}
		

		Button editButton = (Button) topicView.findViewById(R.id.topic_edit_button);
		
		CommentTree ct = CommentTree.getInstance();
		CommentTreeElement topic = ct.getElement(topicView);
		if (!ct.getCurrentUsername().equals(topic.getUsername())){
			editButton.setVisibility(View.INVISIBLE);
		}	
		else{
			editButton.setVisibility(View.VISIBLE);
		}
		
		/* show bitmap */
		ImageView imageView = (ImageView) topicView.findViewById(R.id.topicCommentImage);
		if (CommentTree.getInstance().getElement(topicView).getHasImage()) {
			imageView.setImageBitmap(CommentTree.getInstance().getElement(topicView).getImage());
		}

	}

	/**
	 * When the user hits a reply button, it can either be a reply to the topic
	 * or a reply to a comment. Since there is a separate button for each comment,
	 * they have tags which were set in CommentAdapter. This row number is then passed
	 * along into CreateSubmissonActivity so it can get the proper parent comment
	 * and add a child comment to it. It also passes a construct code which tells
	 * the activity how to construct the submission. In this case, it will be 
	 * constructing a comment. A submit code is passed as well which tells the
	 * activity what is being submitted and how to add it to the proper parent comment
	 * or topic.
	 * 
	 * 
	 * @param v	 the view for the reply button
	 * @throws InterruptedException when an incorrect id is found
	 */
	public void reply(View v) throws InterruptedException{
		Intent intent;
		switch (v.getId()) {

			case R.id.topic_reply_button:

				intent = new Intent(topicView, ReplyToTopicCommentSubmissionView.class);
				intent.putExtra("updateRank", topicView.getRank().getRank());
				topicView.startActivity(intent); 
				break;

			case R.id.comment_reply_button:

				intent = new Intent(topicView, ReplyToCommentCommentSubmissionView.class);
				intent.putExtra("updateRank", topicView.getRank().getRank());
				int rowNumber = (Integer) v.getTag(); //get the row number of the comment being replied to
				intent.putExtra("row number", rowNumber);
				topicView.startActivity(intent); //replying to a comment
				break;

			default:
				throw new InterruptedException("Invalid button press");
		}


	}

	/**
	 * When the user hits an edit button, they can either be editing the topic
	 * or editing a comment. Since there is a separate button for each comment,
	 * they have tags which were set in CommentAdapter. This row number is then passed
	 * along into CreateSubmissonActivity so it can get the proper comment
	 * and edit it. It also passes a construct code which tells
	 * the activity how to construct the edited submission. In this case, it will be 
	 * editing a topic or comment. A submit code is passed as well which tells the
	 * activity what is being submitted and how to edit it properly.
	 * 
	 * 
	 * @param v	 the view for the edit button
	 * @throws InterruptedException when an incorrect id is found
	 */
	public void edit(View v) throws InterruptedException{

		Intent intent;
		switch (v.getId()) {

			case R.id.topic_edit_button:

				intent = new Intent(topicView, EditTopicSubmissionView.class);
				intent.putExtra("updateRank", topicView.getRank().getRank());
				topicView.startActivity(intent); 
				break;

			case R.id.comment_edit_button:

				intent = new Intent(topicView, EditCommentSubmissionView.class);
				intent.putExtra("updateRank", topicView.getRank().getRank());
				int rowNumber = (Integer) v.getTag(); //get the row number of the comment being edited
				intent.putExtra("row number", rowNumber);
				topicView.startActivity(intent); 
				break;

			default:
				throw new InterruptedException("Invalid button press");
		}


	}


	//Called when the image in a comment is clicked
	//Need to expand the image or something here
	public void viewImage(View v){
		int tag = (Integer) v.getTag();
	}

	/**
	 * This method is called when the user wants to save a Topic so they can view it when offline. It will be added 
	 * to the favourites save file which is locally stored on the device.
	 * 
	 * @param v The current View of the object
	 */
	/* You will want to append the new topic to the FavouritesModel class you are writing*/
	public void saveTopic(View v){

	}
	@Override
	public void receiveResult(String result) {
		notify();		
	}

	
	public void openMap() {
		if(NetworkReceiver.isConnected){
			Intent map = new Intent(topicView, MapViewController.class);
			
			map.putExtra("lat", LocationSelection.getInstance().getLocation().getLatitude()); 
			map.putExtra("lon", LocationSelection.getInstance().getLocation().getLongitude());
			map.putExtra("updateRank", topicView.getRank().getRank());
			map.putExtra("canSetMarker", 1);// for editing  location
			topicView.startActivityForResult(map, 0);  

		} else {
			// when we are not connected to any network we open a dialog for user to edit location
			AlertDialog.Builder builder = new AlertDialog.Builder(topicView);

			LayoutInflater inflater = topicView.getLayoutInflater();
			final View dialogView = inflater.inflate(R.layout.offline_location_dialog, null);
			builder.setView(dialogView);
			AlertDialog ad = builder.create();
			ad.setTitle("Select Location");
			ad.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
					new DialogInterface.OnClickListener()
					{	
						@Override
						public void onClick(DialogInterface dialog, int which)
						{  
							EditText text = (EditText) dialogView.findViewById(R.id.offlineLatitude);
							double latitude = Double.parseDouble(text.getText().toString().trim());
							text = (EditText) dialogView.findViewById(R.id.offlineLongitude);
							double longitude = Double.parseDouble(text.getText().toString().trim());
							location = new Location("default");
							location.setLatitude(latitude);
							location.setLongitude(longitude);
							
						}
					});

			ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//do nothing
				}
			});
			ad.show();
			
		}
	}

		
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		// When the given dropdown item is selected, show its contents in the
		// container view.
		
		if(itemPosition == 2)
		{	
			openMap();
		}
		CommentTree.getInstance().sortElements(NavigationItems.fromOrdinal(itemPosition), topicView, location);
		return true;
	}
	
}
