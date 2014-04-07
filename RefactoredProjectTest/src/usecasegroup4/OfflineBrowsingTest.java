package usecasegroup4;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class OfflineBrowsingTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	CommentTree ct;

	public OfflineBrowsingTest() {
		super(HomeView.class);
	}


	public void testCacheTopics() throws Throwable{

		ct = CommentTree.getInstance();
		homeView = getActivity();

		ArrayList<CommentTreeElement> onlineTopics = ct.getChildren(homeView);
		
		
		
	}
	
}