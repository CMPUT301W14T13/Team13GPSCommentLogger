package usecasegroup2.integration;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.submissions.EditTopicSubmissionView;

@SuppressLint("NewApi")
public class GetLocationIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

	
	HomeView homeView;
	EditTopicSubmissionView create;
	CommentTree ct;
	
	public GetLocationIntegrationTest() {
		super(HomeView.class);
	}
	
	
	
	public void testGetLocation() throws Throwable{
		
		ct = CommentTree.getInstance();
		
		homeView = getActivity();
		assertNotNull(homeView);
		
		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(EditTopicSubmissionView.class.getName(), null , false);

		
		
		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		getInstrumentation().waitForIdleSync();
		create = (EditTopicSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);
		
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {

				
				EditText title = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTitle);
				EditText username = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicUsername);
				EditText commentText = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicText);	
				Button submitButton = (Button) create.findViewById(cmput301w14t13.project.R.id.submit);

				assertNotNull(title);
				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(submitButton);

				title.setText("title");
				username.setText("User1");
				commentText.setText("text");
				Location location = LocationSelection.getInstance().getLocation();
				
				submitButton.performClick();

				
				ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
				CommentTreeElement topic = topics.get(0);

				assertTrue("Location should be default phone location", location.distanceTo(topic.getGPSLocation()) == 0);
				System.out.println(topic.getLocation().getLatitude() + " " + location.getLatitude());
				

			}
		});
	}

}
