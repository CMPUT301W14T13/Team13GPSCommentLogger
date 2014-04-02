package UseCaseGroup1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.models.content.Topic;
import cmput301w14t13.project.views.HomeView;



@SuppressLint("NewApi")
public class SortCommentsByTimeTest extends ActivityInstrumentationTestCase2<HomeView> {


	public SortCommentsByTimeTest() {
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
			for (int j = i; j < topics.size(); j++){

				if (j != topics.size() - 1 && j != i){
					assertTrue("Comments should be sorted from newest to oldest", topics.get(i).getTimestamp().after(topics.get(j).getTimestamp()));
				}

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
			for (int j = i; j < topics.size(); j++){

				if (j != topics.size() - 1 && j != i){
					assertTrue("Comments should be sorted from oldest to newest", topics.get(i).getTimestamp().before(topics.get(j).getTimestamp()));
				}

			}
		}
	}

}
