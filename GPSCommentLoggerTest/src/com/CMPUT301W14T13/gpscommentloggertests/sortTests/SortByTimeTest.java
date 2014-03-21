package com.CMPUT301W14T13.gpscommentloggertests.sortTests;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;

import com.CMPUT301W14T13.gpscommentlogger.SortFunctions;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLogger;
import com.CMPUT301W14T13.gpscommentlogger.model.CommentLoggerApplication;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Topic;
import com.CMPUT301W14T13.gpscommentlogger.model.content.Viewable;
import com.CMPUT301W14T13.gpscommentlogger.view.HomeViewActivity;


@SuppressLint("NewApi")
public class SortByTimeTest extends ActivityInstrumentationTestCase2<HomeViewActivity> {



	public SortByTimeTest() {
		super(HomeViewActivity.class);
	}



	public void testSortByNewest() throws InterruptedException{

		CommentLogger cl = CommentLoggerApplication.getCommentLogger();
		ArrayList<Viewable> topics = new ArrayList<Viewable>();

		for (int i = 0; i <= 5; i++){
			topics.add(new Topic());
			Thread.sleep(1000);
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

		CommentLogger cl = CommentLoggerApplication.getCommentLogger();
		ArrayList<Viewable> topics = new ArrayList<Viewable>();

		for (int i = 0; i <= 5; i++){
			topics.add(new Topic());
			Thread.sleep(1000);
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
