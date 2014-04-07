package usecasegroup2.integration;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import cmput301w14t13.project.auxilliary.tools.SortFunctions;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.services.LocationSelection;
import cmput301w14t13.project.views.CreateSubmissionView;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class EditLocationIntegrationTest extends ActivityInstrumentationTestCase2<HomeView> {


	HomeView homeView;
	CreateSubmissionView create;
	CommentTree ct;

	public EditLocationIntegrationTest() {
		super(HomeView.class);
	}
	/* Here is some of the old integration testing code, try and get it working! 
		// Testing setting map location in comment object
		public void testSetMapLocation () throws Throwable {

			intent.putExtra("construct code", 2);
			intent.putExtra("submit code", 3);
			intent.putExtra("row number", 0);

			cl.addComment(new Comment());
			cl.getTopicChildren().get(0).setImage(Bitmap.createBitmap(1,1, Config.ARGB_8888));
			activity = getActivity();
			assertNotNull(activity);

			runTestOnUiThread(new Runnable() {

				@Override
				public void run() {


					EditText username = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_username);
					EditText commentText = (EditText) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.set_comment_text);		
					Button submitButton = (Button) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submit);

					ImageButton attachLocationButton = (ImageButton) activity.findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.mapButton);
					assertNotNull(attachLocationButton);

					assertNotNull(username);
					assertNotNull(commentText);
					assertNotNull(submitButton);

					username.setText("User2");
					commentText.setText("text2");

					attachLocationButton.performClick(); // This click take us to another activity

					Button mapSubmitButton = (Button) getActivity().findViewById(com.CMPUT301W14T13.gpscommentlogger.R.id.submitLocation); // submit button in map view
					assertNotNull(mapSubmitButton);				

					mapSubmitButton.performClick(); // this click attaches default location of map (set to Edmonton)

					submitButton.performClick();

					Bitmap bitmap = Bitmap.createBitmap(5,2, Config.ARGB_8888);
					cl.getTopicChildren().get(0).setImage(bitmap);
					Comment comment = (Comment) cl.getTopicChildren().get(0);

					assertEquals("Usernames wasn't edited", comment.getUsername(), username.getText().toString());
					assertEquals("Text wasn't edited", comment.getCommentText(), commentText.getText().toString());
					assertEquals("Image wasn't edited", comment.getImage(), bitmap);


				}
			});
		}
	 */


	public void testEditTopicLocation() throws Throwable{
		ct = CommentTree.getInstance();

		homeView = getActivity();
		assertNotNull(homeView);

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(CreateSubmissionView.class.getName(), null , false);



		Menu menu = homeView.getMenu();
		menu.performIdentifierAction(cmput301w14t13.project.R.id.action_post_thread, 0);

		getInstrumentation().waitForIdleSync();
		create = (CreateSubmissionView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(create);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {


				EditText title = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTitle);
				EditText username = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicUsername);
				EditText commentText = (EditText) create.findViewById(cmput301w14t13.project.R.id.setTopicText);
				ImageButton mapButton = (ImageButton) getActivity().findViewById(cmput301w14t13.project.R.id.submitLocation); // submit button in map view
				Button submitButton = (Button) create.findViewById(cmput301w14t13.project.R.id.submit);

				assertNotNull(title);
				assertNotNull(username);
				assertNotNull(commentText);
				assertNotNull(mapButton);
				assertNotNull(submitButton);

				title.setText("title");
				username.setText("EditLocationTest");
				commentText.setText("text");

				mapButton.performClick();
				//submitButton.performClick();


				ArrayList<CommentTreeElement> topics = SortFunctions.sortByNewest(ct.getChildren(homeView));
				CommentTreeElement topic = topics.get(0);

				//assertTrue("Location should be default phone location", location.distanceTo(topic.getGPSLocation()) == 0);
			


			}
		});



	}


}
