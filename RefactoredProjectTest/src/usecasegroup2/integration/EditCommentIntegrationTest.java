package usecasegroup2.integration;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.controllers.CreateSubmissionController;
import cmput301w14t13.project.controllers.HomeViewController;
import cmput301w14t13.project.controllers.TopicViewController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.CreateSubmissionView;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;
import cmput301w14t13.project.views.CreateSubmissionView;;

@SuppressLint("NewApi")
public class EditCommentIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;

	CreateSubmissionView create;
	HomeViewController homeControl;

	TopicViewController edit;
	Intent intent;
	CommentTree ct = CommentTree.getInstance();

	public EditCommentIntegrationTest() {
		super(HomeView.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		intent = new Intent();
		setActivityIntent(intent);

	}
	
	@UiThreadTest
	public void testHomeViewUpdate() {
		homeView = getActivity();
		homeView.update();
		ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
		assertNotNull(topicList);
		topicList.performItemClick(
				topicList.getAdapter().getView(0, null, null), 0, 0);

	}
	
}
