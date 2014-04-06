package usecasegroup3;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cmput301w14t13.project.models.CommentTree;
import cmput301w14t13.project.models.content.CommentTreeElement;
import cmput301w14t13.project.views.HomeView;

@SuppressLint("NewApi")
public class BrowseTopicsTest extends ActivityInstrumentationTestCase2<HomeView> {

	
	HomeView homeView;
	
	public BrowseTopicsTest() {
		super(HomeView.class);
	}

	
	
	public void testBrowseTopics() throws Throwable{
		
		
		homeView = getActivity();
		assertNotNull(homeView);
		
		Thread.sleep(10000);
		
		runTestOnUiThread(new Runnable() {

			@Override
			public void run() {
				
				CommentTree ct = CommentTree.getInstance();
				ArrayList<CommentTreeElement> topics = ct.getChildren(homeView);
				ListView topicList = (ListView) homeView.findViewById(cmput301w14t13.project.R.id.topic_listview);
				
				assertEquals("Number of topics in the root should equal the number of topics in the listview", topics.size(),
								topicList.getAdapter().getCount());
				
				assertTrue(topicList.getAdapter().areAllItemsEnabled());
				
				TextView title;
				TextView username;
				ImageView image;	
				TextView coordinates;
				TextView age;	
				TextView num_comments;	
				
				for (int i = 0; i < topics.size(); i++){
					
					View v = topicList.getAdapter().getView(i, null, null);
					assertNotNull(v);
					
					title = (TextView) v.findViewById(cmput301w14t13.project.R.id.title);
					username = (TextView) v.findViewById(cmput301w14t13.project.R.id.username);
					image = (ImageView) v.findViewById(cmput301w14t13.project.R.id.topicImage);	
					coordinates = (TextView) v.findViewById(cmput301w14t13.project.R.id.coordinates);	
					age = (TextView) v.findViewById(cmput301w14t13.project.R.id.age);	
					num_comments = (TextView) v.findViewById(cmput301w14t13.project.R.id.number_of_comments);
					
					assertNotNull(title);
					assertNotNull(username);
					assertNotNull(image);
					assertNotNull(coordinates);
					assertNotNull(age);
					assertNotNull(num_comments);
					
					assertNotNull(title.getText().toString());
					assertNotNull(username.getText().toString());
					
					
					if (topics.get(i).getHasImage()){
						assertNotNull(image.getDrawable());
					}
					
					assertNotNull(coordinates);
					assertNotNull(age.getText().toString());
					assertNotNull(num_comments.getText().toString());
					
					
				}
				
			}
			
		});
	}
	
}
