package usecasegroup2.integration;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class EditLocationIntegrationTest extends
ActivityInstrumentationTestCase2<HomeView> {

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
}
