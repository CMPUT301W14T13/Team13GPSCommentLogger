package cmput301w14t13.project.controllers;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.adapters.CommentAdapter;
import cmput301w14t13.project.auxilliary.interfaces.AsyncProcess;
import cmput301w14t13.project.auxilliary.interfaces.UpdateInterface;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.services.CommentTreeElementLocalSerializer;
import cmput301w14t13.project.services.CommentTreeElementServerSerializer;
import cmput301w14t13.project.views.TopicView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * TopicViewActivity is where the user can view the topic that they selected from HomeViewActivity. Here they can comment, edit their comments, and select a global username.
 * @author  Austin
 */
public class TopicViewController implements AsyncProcess
{
	/**
	 * @uml.property  name="topicView"
	 * @uml.associationEnd  
	 */
	private TopicView topicView;

	public TopicViewController(TopicView topicView) {
		this.topicView = topicView;
	}

	public void selectUsername(){
		Intent intent = new Intent(topicView, SelectUsernameController.class);
		topicView.startActivity(intent);
	}
	
	/**
	 * Sets the various text fields in the topic 
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
		text.setText(currentTopic.getDateDiff(post_time, new Date()));
		
		text = (TextView) topicView.findViewById(R.id.number_of_comments);
		text.setText(String.valueOf(currentTopic.getNumberOfChildren()) + " comments");
		
		/* show bitmap */
		ImageView imageView = (ImageView) topicView.findViewById(R.id.commentImage);
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
		
		Intent intent = new Intent(topicView, CreateSubmissionController.class);
		int rowNumber;
		intent.putExtra("construct code", 1); //construct a comment
		intent.putExtra("updateRank", topicView.getRank().getRank());
		
		 switch (v.getId()) {
		 
	         case R.id.topic_reply_button:
	        	 
	        	 intent.putExtra("submit code", 0); //replying to a topic
	        	 topicView.startActivity(intent); 
	             break;
	             
	         case R.id.comment_reply_button:
	        	 
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being replied to
	        	 intent.putExtra("submit code", 1); //replying to a comment
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
		
		Intent intent = new Intent(topicView, CreateSubmissionController.class);
		intent.putExtra("updateRank", topicView.getRank().getRank());
		int rowNumber;
		
				
		 switch (v.getId()) {
		 
	         case R.id.topic_edit_button:
	        
	        	 intent.putExtra("construct code", 3); // constructing an edited topic
	        	 intent.putExtra("submit code", 2);  //editing a topic
	        	 topicView.startActivity(intent); 
	             break;
	             
	         case R.id.comment_edit_button:
	        	 
	        	 rowNumber = (Integer) v.getTag(); //get the row number of the comment being edited
	        	
	        	 intent.putExtra("construct code", 2); //constructing an edited comment
	        	 intent.putExtra("submit code", 3); //editing a comment
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

}
