package usecasegroup1.integration;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;



@SuppressLint("NewApi")
public class SortByTimeIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView activity;

	public SortByTimeIntegrationTest() {
		super(HomeView.class);
	}



	@Override
	public void setUp(){
		
		Intent intent = new Intent();
		setActivityIntent(intent);
		activity = getActivity();
	}
	
	public void testSortByNewest(){

		ArrayList<CommentTreeElement> topics = CommentTree.getInstance().getChildren(activity);
		topics = SortFunctions.sortByNewest(topics);

		//Every topic should be newer than the ones after it
		for (int i = 0; i < topics.size(); i++){

			if (i != topics.size() - 1){
				assertTrue("Comments should be sorted from newest to oldest", topics.get(i).getTimestamp().after(topics.get(i+1).getTimestamp()));
			}


		}
	}

	public void testSortByOldest() throws InterruptedException{

		ArrayList<CommentTreeElement> topics = CommentTree.getInstance().getChildren(activity);
		//topics = SortFunctions.sortByOldest(topics);

		//Every topic should be older than the ones after it
		for (int i = 0; i < topics.size(); i++){

			if (i != topics.size() - 1){
				assertTrue("Comments should be sorted from newest to oldest", topics.get(i).getTimestamp().before(topics.get(i+1).getTimestamp()));
			}


		}
	}

}
