package usecasegroup1.integration;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class SortByPicturesIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	public SortByPicturesIntegrationTest() {
		super(HomeView.class);
	}

	
	
	public void testSortByPicture(){
	
		HomeView activity;
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();
		
		ArrayList<CommentTreeElement> topics = CommentTree.getInstance().getChildren(activity);
			
			
			topics = SortFunctions.sortByPicture(topics);
			
			
			boolean picture = true;
			for (int j = 0; j < topics.size(); j ++){
				
				if (!topics.get(j).getHasImage()){
					picture = false;
				}
				
				if (!picture){
					assertFalse("There should be no pictures after a topic is found with no image", topics.get(j).getHasImage());
				}
				else{
					assertTrue("There should be a picture", topics.get(j).getHasImage());
				}
			}
		}
}
	
	
