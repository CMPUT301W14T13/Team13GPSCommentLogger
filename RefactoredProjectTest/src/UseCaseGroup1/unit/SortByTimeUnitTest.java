package usecasegroup1.unit;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;



@SuppressLint("NewApi")
public class SortByTimeUnitTest extends ActivityInstrumentationTestCase2<HomeView> {


	public SortByTimeUnitTest() {
		super(HomeView.class);
	}



	public void testSortByNewest() throws InterruptedException{

		ArrayList<CommentTreeElement> topics = new ArrayList<CommentTreeElement>();

		for (int i = 0; i <= 5; i++){
			topics.add(new Topic());
			Thread.sleep(500);
		}
		
		topics = SortFunctions.sortByNewest(topics);
		
		for (int i = 0; i < topics.size(); i++){
			System.out.println(topics.get(i).getTimestamp());
		}

		

		//Every topic should be newer than the ones after it
		for (int i = 0; i < topics.size(); i++){

			if (i != topics.size() - 1){
				assertTrue("Comments should be sorted from newest to oldest", topics.get(i).getTimestamp().after(topics.get(i+1).getTimestamp()));
			}


		}
	}

	public void testSortByOldest() throws InterruptedException{

		ArrayList<CommentTreeElement> topics = new ArrayList<CommentTreeElement>();

		for (int i = 0; i <= 5; i++){
			topics.add(new Topic());
			Thread.sleep(500);
		}

		topics = SortFunctions.sortByOldest(topics);
		
		for (int i = 0; i < topics.size(); i++){
			System.out.println(topics.get(i).getTimestamp());
		}

		

		//Every topic should be older than the ones after it
		for (int i = 0; i < topics.size(); i++){

			if (i != topics.size() - 1){
				assertTrue("Comments should be sorted from newest to oldest", topics.get(i).getTimestamp().before(topics.get(i+1).getTimestamp()));
			}


		}
	}

}
