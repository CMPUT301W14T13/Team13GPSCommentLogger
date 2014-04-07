package usecasegroup2.integration;

import java.util.ArrayList;

import org.osmdroid.views.MapView;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import cmput301w14t13.project.R;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.controllers.MapViewController;
import cmput301w14t13.project.controllers.submissions.CreateTopicSubmissionController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.submissions.CreateTopicSubmissionView;
import cmput301w14t13.project.views.submissions.SubmissionView;
import cmput301w14t13.project.views.submissions.TopicSubmissionView;

@SuppressLint("NewApi")
public class EditLocationIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {


	HomeView homeView;
	SubmissionView create;
	CommentTree ct;
	MapViewController map;
	MapView mapView;
	
	public EditLocationIntegrationTest() {
		super(HomeView.class);
	}
	

	public void testEditTopicLocation() throws Throwable{
		ct = CommentTree.getInstance();

		homeView = getActivity();
		assertNotNull(homeView);

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateTopicSubmissionView.class.getName(), null , false);



		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		getInstrumentation().waitForIdleSync();
		create = (CreateTopicSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);

		submissionMonitor = getInstrumentation().addMonitor(MapViewController.class.getName(), null , false);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				
				ImageButton mapButton = (ImageButton) create.findViewById(cmput301w14t13.project.R.id.mapButton); // submit button in map view
				assertNotNull(mapButton);
				mapButton.performClick();
			}
			
		});
		
		getInstrumentation().waitForIdleSync();
		map = (MapViewController) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(map);
		
		mapView = (MapView) map.findViewById(R.id.mapEditView);
		assertNotNull(mapView);
		
		TouchUtils.dragQuarterScreenUp(this, map);
		TouchUtils.tapView(this, mapView);
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				
				
				Button submit = (Button) map.findViewById(cmput301w14t13.project.R.id.submitLocation);
				assertNotNull(submit);
				submit.performClick();
			}
			
		});
		
		getInstrumentation().waitForIdleSync();
		
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
				username.setText("EditLocationTest");
				commentText.setText("text");

				
				submitButton.performClick();

				ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
				CommentTreeElement topic = topics.get(0);

				Location phoneLocation = LocationSelection.getInstance().getLocation();
				Location zeroLocation = new Location("default");
				
				assertTrue("Location should not be your current location", topic.getGPSLocation().distanceTo(phoneLocation) != 0);
				assertTrue("Location should not be 0,0", topic.getGPSLocation().distanceTo(zeroLocation) != 0);


			}
		});

		


	}


}
