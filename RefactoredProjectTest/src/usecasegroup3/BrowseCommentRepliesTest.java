package usecasegroup3;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cmput301w14t13.project.R;
import cmput301w14t13.project.controllers.SelectUsernameController;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.Comment;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;
import cmput301w14t13.project.views.TopicView;

@SuppressLint("NewApi")
public class BrowseCommentRepliesTest extends ActivityInstrumentationTestCase2<HomeView> {

	HomeView homeView;
	TopicView topicView;

	public BrowseCommentRepliesTest() {
		super(HomeView.class);
	}



	public void testBrowseCommentReplies() throws Throwable{


		homeView = getActivity();
		assertNotNull(homeView);

		//Thread.sleep(10000);

		Instrumentation.ActivityMonitor submissionMonitor = getInstrumentation().addMonitor(TopicView.class.getName(), null , false);


		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);

				topicList.performItemClick(topicList.getChildAt(0), 0,
						topicList.getAdapter().getItemId(0));
			}

		});


		getInstrumentation().waitForIdleSync();
		topicView = (TopicView) getInstrumentation().waitForMonitorWithTimeout(submissionMonitor, 5000);
		assertNotNull(topicView);

		runTestOnUiThread(new Runnable()  {

			@Override
			public void run(){


				CommentTree ct = CommentTree.getInstance();

				ListView commentList = (ListView) topicView.findViewById(cmput301w14t13.project.R.id.comment_list);
				ArrayList<CommentTreeElement> comments = ct.getCommentList(topicView);

				assertEquals("Number of comments in the topic should equal the number of comments in the listview", comments.size(),
						commentList.getAdapter().getCount());

				TextView username;
				ImageView image;	
				TextView coordinates;
				TextView commentText;	

				for (int i = 0; i < comments.size(); i++){

					View v = commentList.getAdapter().getView(i, null, null);

					
					username = (TextView) v.findViewById(cmput301w14t13.project.R.id.comment_username);
					image = (ImageView) v.findViewById(cmput301w14t13.project.R.id.commentImage);	
					coordinates = (TextView) v.findViewById(cmput301w14t13.project.R.id.comment_coordinates);	
					commentText = (TextView) v.findViewById(cmput301w14t13.project.R.id.commentText);	
					View[] indentViews = new View[] {
							v.findViewById(cmput301w14t13.project.R.id.left_indent1),
							v.findViewById(cmput301w14t13.project.R.id.left_indent2),
							v.findViewById(cmput301w14t13.project.R.id.left_indent3),
							v.findViewById(cmput301w14t13.project.R.id.left_indent4),
							v.findViewById(cmput301w14t13.project.R.id.left_indent5),

					};
					
					if (comments.get(i).getIndentLevel() > 0){
						assertNotNull(indentViews[i-1]);
					}
					
					assertNotNull(username);
					assertNotNull(image);
					assertNotNull(coordinates);
					assertNotNull(commentText);
					
					
					assertNotNull(username.getText().toString());


					if (comments.get(i).getHasImage()){
						assertNotNull(image.getDrawable());
					}
					else{
						assertNotNull(commentText.getText().toString());
					}

					assertNotNull(coordinates);


				}
				 
			}

		});
	}
}
